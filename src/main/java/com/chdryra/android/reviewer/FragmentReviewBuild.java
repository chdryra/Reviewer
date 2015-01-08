/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
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
import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.mygenerallibrary.VHDString;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

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
 * @see ConfigGvDataUi
 * @see DialogFragmentGvDataAdd
 */
public class FragmentReviewBuild extends FragmentReviewGrid implements ImageChooser
        .ImageChooserListener {
    private final static int LOCATION_MAP = 22;

    private GvCellManagerList mCellManagerList;
    private ImageChooser      mImageChooser;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode resCode = ActivityResultCode.get(resultCode);
        if (requestCode == getUIConfig(GvDataList.GvType.IMAGES).getDisplayConfig()
                .getRequestCode() &&
                mImageChooser.chosenImageExists(resCode, data)) {
            mImageChooser.getChosenImage(this);
        } else if (requestCode == getUIConfig(GvDataList.GvType.LOCATIONS).getAdderConfig()
                .getRequestCode()
                && resCode.equals(DialogFragmentLocation.RESULT_MAP.getResultCode())) {
            requestMapIntent(data);
        } else if (requestCode == LOCATION_MAP && resCode.equals(ActivityResultCode.DONE)) {
            addLocation(data);
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
        setDismissOnDone(false);
        setBannerButtonText(getResources().getString(R.string.button_add_review_data));
        setIsEditable(true);
        setTransparentGridCellBackground();

        mImageChooser = new ImageChooser(getController(), getActivity());
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

                if (getController().getData(GvDataList.GvType.TAGS).size() == 0) {
                    Toast.makeText(getActivity(), R.string.toast_enter_tag,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                requestIntent(getUIConfig(GvDataList.GvType.SOCIAL));
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
    protected ViewHolderAdapter getGridViewCellAdapter() {
        return new ReviewOptionsGridCellAdapter();
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        GvCellManagerList.GvCellManager cellManager = (GvCellManagerList.GvCellManager) parent
                .getItemAtPosition(position);
        cellManager.executeIntent(false);
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        GvCellManagerList.GvCellManager cellManager = (GvCellManagerList.GvCellManager) parent
                .getItemAtPosition(position);
        cellManager.executeIntent(true);
    }

    @Override
    public void onImageChosen(GvImageList.GvImage image) {
        image.setIsCover(true);
        GvImageList images = new GvImageList();
        images.add(image);
        getEditableController().setData(images);
        updateUI();
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
            getRatingBar().setRating(getController().getRating());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private ConfigGvDataUi.Config getUIConfig(GvDataList.GvType dataType) {
        return ConfigGvDataUi.getConfig(dataType);
    }

    private void requestMapIntent(Intent data) {
        GvLocationList.GvLocation location = (GvLocationList.GvLocation) GvDataPacker
                .unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
        Bundle args = new Bundle();
        GvDataPacker.packItem(GvDataPacker.CurrentNewDatum.CURRENT, location, args);

        LaunchableIU mapUi = ConfigGvDataUi.getLaunchable(ActivityReviewLocationMap.class);
        LauncherIU.launch(mapUi, this, LOCATION_MAP, null, args);
    }

    private void addLocation(Intent data) {
        GvLocationList.GvLocation location = (GvLocationList.GvLocation) GvDataPacker
                .unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
        if (location.isValidForDisplay()) {
            GvLocationList list = new GvLocationList();
            list.add(location);
            getEditableController().setData(list);
        }
    }

    private void initCellManagerList() {
        mCellManagerList = new GvCellManagerList();
        mCellManagerList.add(GvDataList.GvType.TAGS);
        mCellManagerList.add(GvDataList.GvType.CHILDREN);
        mCellManagerList.add(GvDataList.GvType.IMAGES);
        mCellManagerList.add(GvDataList.GvType.COMMENTS);
        mCellManagerList.add(GvDataList.GvType.LOCATIONS);
        mCellManagerList.add(GvDataList.GvType.FACTS);
    }

    private void requestIntent(ConfigGvDataUi.Config config) {
        Intent i = config.getDisplayConfig().requestIntent(getActivity());
        Administrator.get(getActivity()).pack(getController(), i);
        startActivityForResult(i, config.getDisplayConfig().getRequestCode());
    }

    private void showQuickDialog(ConfigGvDataUi.Config config) {
        Bundle args = Administrator.get(getActivity()).pack(getController());
        args.putBoolean(DialogFragmentGvDataAdd.QUICK_SET, true);

        ConfigGvDataUi.GvDataUiConfig adderConfig = config.getAdderConfig();

        LaunchableIU ui = adderConfig.getReviewDataUI();
        if (adderConfig.getGVType() == GvDataList.GvType.LOCATIONS) {
            ui = ConfigGvDataUi.getLaunchable(DialogFragmentLocation.class);
        }

        LauncherIU.launch(ui, this, adderConfig.getRequestCode(), adderConfig.getTag(),
                args);
    }

    private void showQuickImageDialog() {
        startActivityForResult(mImageChooser.getChooserIntents(),
                getUIConfig(GvDataList.GvType.IMAGES).getDisplayConfig().getRequestCode());
    }

    /**
     * Provides the adapter for the GridView of data tiles. Can't use the ViewHolder pattern here
     * because each cell can have its own unique look so reuse is not an option. The view update
     * requests are forwarded to underlying the GVCellManagers to handle.
     */
    class ReviewOptionsGridCellAdapter extends ViewHolderAdapter {
        public ReviewOptionsGridCellAdapter() {
            super(getActivity(), getGridData(), getGridCellWidth(), getGridCellHeight());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = ((GvCellManagerList) getGridData()).getItem(position).updateView(parent);
            convertView.getLayoutParams().height = getGridCellHeight();
            convertView.getLayoutParams().width = getGridCellWidth();

            return convertView;
        }
    }

    /**
     * Holds the list of cells that manage data display and respond to user interaction for the data
     * tiles. This is what {@link #getGridData()} returns for this fragment.
     */
    class GvCellManagerList extends GvDataList<GvCellManagerList.GvCellManager> {

        private void add(GvType dataType) {
            add(new GvCellManager(dataType));
        }

        /**
         * Encapsulates the range of responses and displays available to each data tile depending
         * on the underlying data and user interaction.
         */
        class GvCellManager implements GvDataList.GvData {
            private final GvType mDataType;

            private GvCellManager(GvType dataType) {
                mDataType = dataType;
            }

            @Override
            public ViewHolder newViewHolder() {
                return null;
            }

            @Override
            public boolean isValidForDisplay() {
                return true;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeSerializable(mDataType);
            }

            private void executeIntent(boolean forceRequestIntent) {
                if (getController().getData(mDataType).size() == 0 && !forceRequestIntent) {
                    if (mDataType == GvType.IMAGES) {
                        showQuickImageDialog();
                    } else {
                        showQuickDialog(getUIConfig(mDataType));
                    }
                } else {
                    requestIntent(getUIConfig(mDataType));
                }
            }

            private View updateView(ViewGroup parent) {
                int size = getController().getData(mDataType).size();

                if (size == 0) return getNoDatumView(parent);

                return size > 1 || mDataType == GvType.IMAGES ? getMultiDataView(parent) :
                        getSingleDatumView(parent);
            }

            private View getNoDatumView(ViewGroup parent) {
                ViewHolder vh = new VHText();
                vh.inflate(getActivity(), parent);
                vh.updateView(new VHDString(mDataType.getDataString()));
                return vh.getView();
            }

            private View getMultiDataView(ViewGroup parent) {
                int number = getController().getData(mDataType).size();
                String type = number == 1 ? mDataType.getDatumString() : mDataType.getDataString();

                ViewHolder vh = new VHDualText();
                vh.inflate(getActivity(), parent);
                vh.updateView(new VHDDualString(String.valueOf(number), type));
                return vh.getView();
            }

            private View getSingleDatumView(ViewGroup parent) {
                ViewHolderData datum = (ViewHolderData) getController().getData(mDataType)
                        .getItem(0);
                ViewHolder vh = mDataType == GvType.LOCATIONS ? new VHLocation(true) : datum
                        .newViewHolder();
                if (vh.getView() == null) vh.inflate(getActivity(), parent);
                vh.updateView(datum);
                return vh.getView();
            }
        }
    }
}
