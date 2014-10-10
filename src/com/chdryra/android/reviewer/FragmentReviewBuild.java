/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.ActionBar.LayoutParams;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.FragmentReviewBuild.GVCellManagerList.GVCellManager;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.OptionsReviewBuild.ReviewDataOption;
import com.google.android.gms.maps.model.LatLng;

/**
 * UI Fragment: editing and building reviews. The primary screen where the user builds the review.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: enabled</li>
 * <li>RatingBar: enabled</li>
 * <li>Banner button: disabled</li>
 * <li>Grid cells represent
 * <ul>
 * <li>Tags</li>
 * <li>Criteria (sub-reviews)</li>
 * <li>Images</li>
 * <li>Comments</li>
 * <li>Locations</li>
 * <li>Facts</li>
 * </ul></li>
 * <li>Grid cell click:
 * <ul>
 * <li>No data: launches appropriate DialogAddReviewDataFragment with QUICK_SET = true</li>
 * <li>Has data: same as long-click</li>
 * </ul>
 * <li>Grid cell long click: takes user to appropriate activity for that data</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * On top of standard FragmentReviewGrid functionality, there is also:
 * <ul>
 * <li>An ActionBar icon for setting the review score as an average of the sub-reviews</li>
 * <li>A "Share" button for moving to the next screen</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewBuild
 * @see com.chdryra.android.reviewer.OptionsReviewBuild
 * @see com.chdryra.android.reviewer.DialogAddReviewDataFragment
 */
public class FragmentReviewBuild extends FragmentReviewGrid<GVCellManager> {
    private final static int LOCATION_MAP = 22;

    private GVCellManagerList mCellManagerList;
    private HelperReviewImage mHelperReviewImage;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode resCode = ActivityResultCode.get(resultCode);
        if (requestCode == getOption(GVType.IMAGES).getActivityRequestCode()) {
            if (mHelperReviewImage.processOnActivityResult(getActivity(), resultCode, data)) {
                addImage();
            }
        } else if (requestCode == getOption(GVType.LOCATIONS).getDialogRequestCode()) {
            if (resCode.equals(DialogLocationFragment.RESULT_MAP.getResultCode())) {
                requestMapIntent(data);
            }
        } else if (requestCode == LOCATION_MAP) {
            if (resCode.equals(ActivityResultCode.DONE)) {
                addLocation(data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCellManagerList();

        setGridViewData(mCellManagerList);
        setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
        setDismissOnDone(false);
        setBannerButtonText(getResources().getString(R.string.button_add_review_data));
        setIsEditable(true);
        setTransparentGridCellBackground();

        mHelperReviewImage = HelperReviewImage.getInstance(getController());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        getBannerButton().setClickable(false);
        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
        Button shareButton = (Button) inflater.inflate(R.layout.review_banner_button, container,
                false);
        shareButton.setText(getResources().getString(R.string.button_share));
        shareButton.getLayoutParams().height = LayoutParams.MATCH_PARENT;
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSubjectText().length() == 0) {
                    Toast.makeText(getActivity(), R.string.toast_enter_subject,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (getController().getData(GVType.TAGS).size() == 0) {
                    Toast.makeText(getActivity(), R.string.toast_enter_tag,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(getActivity(), ActivityReviewShare.class);
                Administrator.get(getActivity()).pack(getController(), i);
                startActivity(i);
            }
        });

        getGridView().getLayoutParams().height = LayoutParams.WRAP_CONTENT;
        getLayout().addView(shareButton);
        getLayout().addView(divider);

        return v;
    }

    @Override
    protected void onDoneSelected() {
    }

    @Override
    protected GridViewCellAdapter getGridViewCellAdapter() {
        return new ReviewOptionsGridCellAdapter();
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        GVCellManager cellManager = (GVCellManager) parent.getItemAtPosition(position);
        cellManager.executeIntent(false);
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        GVCellManager cellManager = (GVCellManager) parent.getItemAtPosition(position);
        cellManager.executeIntent(true);
    }

    private ReviewDataOption getOption(GVType dataType) {
        return OptionsReviewBuild.get(dataType);
    }

    private void addImage() {
        final GVImageList images = new GVImageList();
        mHelperReviewImage.addReviewImage(getActivity(), images, new FunctionPointer<Void>() {
            @Override
            public void execute(Void data) {
                images.getItem(0).setIsCover(true);
                getEditableController().setData(images);
                updateUI();
            }
        });
    }

    private void requestMapIntent(Intent data) {
        Intent i = new Intent(getActivity(), ActivityReviewLocationMap.class);
        i.putExtras(data);
        startActivityForResult(i, LOCATION_MAP);
    }

    private void addLocation(Intent data) {
        LatLng latLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
        String name = (String) data.getSerializableExtra(FragmentReviewLocationMap.NAME);
        if (latLng != null && name != null) {
            GVLocationList list = new GVLocationList();
            list.add(latLng, name);
            getEditableController().setData(list);
        }
    }

    private void initCellManagerList() {
        mCellManagerList = new GVCellManagerList();
        mCellManagerList.add(GVType.TAGS);
        mCellManagerList.add(GVType.CHILDREN);
        mCellManagerList.add(GVType.IMAGES);
        mCellManagerList.add(GVType.COMMENTS);
        mCellManagerList.add(GVType.LOCATIONS);
        mCellManagerList.add(GVType.FACTS);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_review_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_average_rating) {
            getNodeController().setReviewRatingAverage(true);
            getTotalRatingBar().setRating(getController().getRating());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void requestIntent(ReviewDataOption option) {
        Intent i = new Intent(getActivity(), option.getActivityRequestClass());
        Administrator.get(getActivity()).pack(getController(), i);
        startActivityForResult(i, option.getActivityRequestCode());
    }

    private void showQuickDialog(ReviewDataOption option) {
        DialogFragment dialog = option.getDialogFragment();
        dialog.setTargetFragment(FragmentReviewBuild.this, option.getDialogRequestCode());
        Bundle args = Administrator.get(getActivity()).pack(getController());
        args.putBoolean(DialogAddReviewDataFragment.QUICK_SET, true);
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), option.getDialogTag());
    }

    private void showQuickImageDialog() {
        startActivityForResult(mHelperReviewImage.getImageChooserIntents(getActivity()),
                getOption(GVType.IMAGES).getActivityRequestCode());
    }

    /**
     * Provides the adapter for the GridView of data tiles. Can't use the ViewHolder pattern here
     * because each cell can have its own unique look so reuse is not an option. The view update
     * requests are forwarded to underlying the GVCellManagers to handle.
     */
    class ReviewOptionsGridCellAdapter extends GridViewCellAdapter {
        public ReviewOptionsGridCellAdapter() {
            super(getActivity(), getGridData(), getGridCellWidth(), getGridCellHeight());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getGridData().getItem(position).updateView(parent);
            convertView.getLayoutParams().height = getGridCellHeight();
            convertView.getLayoutParams().width = getGridCellWidth();

            return convertView;
        }
    }

    /**
     * Holds the list of cells that manage data display and respond to user interaction for the data
     * tiles. This is what <code>getGridViewData()</code> returns for this fragment.
     */
    class GVCellManagerList extends GVReviewDataList<GVCellManagerList.GVCellManager> {
        private GVCellManagerList() {
            super(null);
        }

        private void add(GVType dataType) {
            add(new GVCellManager(dataType));
        }

        /**
         * Encapsulates the range of responses and displays available to each data tile depending
         * on the underlying data and user interaction.
         */
        class GVCellManager implements GVData {
            private final GVType mDataType;

            private GVCellManager(GVType dataType) {
                mDataType = dataType;
            }

            private void executeIntent(boolean forceRequestIntent) {
                if (getController().getData(mDataType).size() == 0 && !forceRequestIntent) {
                    if (mDataType == GVType.IMAGES) {
                        showQuickImageDialog();
                    } else {
                        showQuickDialog(getOption(mDataType));
                    }
                } else {
                    requestIntent(getOption(mDataType));
                }
            }

            @Override
            public ViewHolder getViewHolder() {
                return null;
            }

            private View updateView(ViewGroup parent) {
                int size = getController().getData(mDataType).size();

                if (size == 0) return getNoDatumView(parent);

                return size > 1 || mDataType == GVType.IMAGES ? getMultiDataView(parent) :
                        getSingleDatumView(parent);
            }

            private View getNoDatumView(ViewGroup parent) {
                ViewHolder vh = new VHTextView();
                vh.inflate(getActivity(), parent);
                vh.updateView(new GVString(mDataType.getDataString()));
                return vh.getView();
            }

            private View getMultiDataView(ViewGroup parent) {
                int number = getController().getData(mDataType).size();
                String type = number == 1 ? mDataType.getDatumString() : mDataType.getDataString();

                ViewHolder vh = new VHTextDualView();
                vh.inflate(getActivity(), parent);
                vh.updateView(new GVDualString(String.valueOf(number), type));
                return vh.getView();
            }

            private View getSingleDatumView(ViewGroup parent) {
                ViewHolder vh = getOption(mDataType).getViewHolder();
                if (vh.getView() == null) vh.inflate(getActivity(), parent);
                vh.updateView((GVData) getController().getData(mDataType).getItem(0));
                return vh.getView();
            }
        }
    }
}
