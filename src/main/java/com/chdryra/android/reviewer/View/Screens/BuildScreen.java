/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditData;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityShareReview;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.AddLocation;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreen implements ImageChooser.ImageChooserListener,
        LocationClientConnector.Locatable {
    private final ReviewEditor mEditor;
    private final BuildScreenGridItem mGridItem;
    private ImageChooser mImageChooser;
    private LatLng mLatLng;

//Constructors
    public BuildScreen(Context context, ReviewBuilderAdapter builder) {
        //Actions
        ReviewViewActions actions = new ReviewViewActions();
        mGridItem = new BuildScreenGridItem();
        actions.setAction(mGridItem);
        String screenTitle = context.getResources().getString(R.string.screen_title_build_review);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        actions.setAction(new SubjectEdit());
        actions.setAction(new RatingBar());
        actions.setAction(ReviewViewAction.BannerButtonAction.newDisplayButton(buttonTitle));
        actions.setAction(new BuildScreenMenu(screenTitle));

        //Parameters
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        mEditor = new ReviewEditor(builder, params, actions, new BuildScreenModifier());
    }

    //public methods
    public ReviewEditor getEditor() {
        return mEditor;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        boolean imageRequested = requestCode == getImageRequestCode();
        if (imageRequested && mImageChooser.chosenImageExists(result, data)) {
            mImageChooser.getChosenImage(this);
        }

        updateScreen();
    }

    //private methods
    private int getImageRequestCode() {
        return ConfigGvDataUi.getConfig(GvImageList.GvImage.TYPE).getAdderConfig()
                .getRequestCode();
    }

    private void setCover(GvImageList.GvImage image) {
        mEditor.setCover(image);
    }

    private void updateScreen() {
        mEditor.notifyBuilder();
    }

    private void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    private void showTagDialog() {
        mGridItem.showQuickDialog(ConfigGvDataUi.getConfig(GvTagList.GvTag.TYPE));
    }

    //Overridden
    @Override
    public void onLocated(Location location) {
        setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onLocationClientConnected(Location location) {
        onLocated(location);
    }

    @Override
    public void onChosenImage(GvImageList.GvImage image) {
        setCover(image);
    }

    //Classes

    /**
     * Created by: Rizwan Choudrey
     * On: 24/01/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private class SubjectEdit extends ReviewViewAction.SubjectAction {
        //Overridden
        @Override
        public void onEditorDone(CharSequence s) {
            mEditor.setSubject();
        }
    }

    private class RatingBar extends ReviewViewAction.RatingBarAction {
        //Overridden
        @Override
        public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            mEditor.setRating(rating, fromUser);
        }
    }

    private class BuildScreenGridItem extends ReviewViewAction.GridItemAction {
        private LocationClientConnector mLocationClient;

        //private methods
        private void executeIntent(GvDataList gridCell, boolean quickDialog) {
            GvDataType type = gridCell.getGvDataType();
            ConfigGvDataUi.Config config = ConfigGvDataUi.getConfig(type);
            if (quickDialog && !gridCell.hasData()) {
                showQuickDialog(config);
            } else {
                ActivityEditData.start(getActivity(), config.getGvDataType());
            }
        }

        private void showQuickDialog(ConfigGvDataUi.Config config) {
            if (config.getGvDataType().equals(GvImageList.GvImage.TYPE)) {
                getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                        getImageRequestCode());
                return;
            }

            Bundle args = new Bundle();
            args.putBoolean(DialogGvDataAdd.QUICK_SET, true);
            packLatLng(args);

            ConfigGvDataUi.LaunchableConfig adderConfig = config.getAdderConfig();
            LauncherUi.launch(adderConfig.getLaunchable(), getActivity(),
                    adderConfig.getRequestCode(), adderConfig.getTag(), args);
        }

        private void packLatLng(Bundle args) {
            LatLng latLng = mLatLng;
            boolean fromImage = false;

            GvImageList covers = mEditor.getCovers();
            if (covers.size() > 0) {
                LatLng coverLatLng = covers.getItem(0).getLatLng();
                if (coverLatLng != null) {
                    latLng = coverLatLng;
                    fromImage = true;
                }
            }

            args.putParcelable(AddLocation.LATLNG, latLng);
            args.putBoolean(AddLocation.FROM_IMAGE, fromImage);
        }

        //Overridden
        @Override
        public void onUnattachReviewView() {
            super.onUnattachReviewView();
            mLocationClient.disconnect();
        }

        @Override
        public void onAttachReviewView() {
            mImageChooser = mEditor.getImageChooser(getActivity());
            mLocationClient = new LocationClientConnector(getActivity(), BuildScreen.this);
            mLocationClient.connect();
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            executeIntent((GvDataList) item, true);
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            executeIntent((GvDataList) item, false);
        }
    }

    private class BuildScreenMenu extends ReviewViewAction.MenuAction {
        public static final int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
        private static final int MENU = R.menu.menu_build_review;

        private final MenuActionItem mActionItem;

        private BuildScreenMenu(String title) {
            super(MENU, title, true);
            mActionItem = new MenuActionItem() {
                //Overridden
                @Override
                public void doAction(Context context, MenuItem item) {
                    mEditor.setRatingIsAverage(true);
                }
            };
        }

        //Overridden
        @Override
        protected void addMenuItems() {
            bindMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
        }
    }

    private class BuildScreenModifier implements ReviewViewPerspective.ReviewViewModifier {
        private void requestShareIntent(FragmentReviewView parent) {
            Activity activity = parent.getActivity();

            if (parent.getSubject().length() == 0) {
                Toast.makeText(activity, R.string.toast_enter_subject, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!mEditor.hasTags()) {
                Toast.makeText(activity, R.string.toast_enter_tag, Toast.LENGTH_SHORT).show();
                showTagDialog();
                return;
            }

            Intent i = new Intent(activity, ActivityShareReview.class);
            activity.startActivity(i);
        }

        //Overridden
        @Override
        public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {


            parent.setBannerNotClickable();

            View divider = inflater.inflate(R.layout.horizontal_divider, container, false);

            Button shareButton = (Button) inflater.inflate(R.layout.review_banner_button, container,
                    false);
            String title = parent.getActivity().getResources().getString(R.string.button_share);
            shareButton.setText(title);
            shareButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
            shareButton.setOnClickListener(new View.OnClickListener() {
                //Overridden
                @Override
                public void onClick(View v) {
                    requestShareIntent(parent);
                }
            });

            parent.addView(shareButton);
            parent.addView(divider);

            return v;
        }
    }
}
