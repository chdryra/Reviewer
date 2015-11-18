/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

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
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityEditData;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityShareReview;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Dialogs.AddLocation;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.RatingBarActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;
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
    private final ConfigDataUi mUiConfig;
    private final FactoryLaunchableUi mLaunchableFactory;

    private ImageChooser mImageChooser;
    private LatLng mLatLng;

//Constructors
    public BuildScreen(Context context,
                       ReviewBuilderAdapter builder,
                       FactoryReviewEditor editorFactory,
                       ConfigDataUi uiConfig,
                       FactoryLaunchableUi launchableFactory) {
        mUiConfig = uiConfig;
        mLaunchableFactory = launchableFactory;

        //Actions
        ReviewViewActions actions = new ReviewViewActions();
        mGridItem = new BuildScreenGridItem();
        actions.setAction(mGridItem);
        String screenTitle = context.getResources().getString(R.string.screen_title_build_review);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        actions.setAction(new SubjectEdit());
        actions.setAction(new RatingBar());
        actions.setAction(BannerButtonActionNone.newDisplayButton(buttonTitle));
        actions.setAction(new BuildScreenMenu(screenTitle));

        //Parameters
        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        mEditor = editorFactory.newEditor(builder, params, actions, new BuildScreenModifier());
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
    private <T extends GvData> LaunchableConfig<T> getAdderConfig(GvDataType<T> dataType) {
        return mUiConfig.getLaunchableConfigs(dataType).getAdderConfig();
    }

    private int getImageRequestCode() {
        return getAdderConfig(GvImageList.GvImage.TYPE).getRequestCode();
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
        mGridItem.showQuickDialog(getAdderConfig(GvTagList.GvTag.TYPE));
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
    private class SubjectEdit extends SubjectActionNone {
        //Overridden
        @Override
        public void onKeyboardDone(CharSequence s) {
            mEditor.setSubject();
        }
    }

    private class RatingBar extends RatingBarActionNone {
        //Overridden
        @Override
        public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            mEditor.setRating(rating, fromUser);
        }
    }

    private class BuildScreenGridItem extends GridItemActionNone {
        private LocationClientConnector mLocationClient;

        //private methods
        private void executeIntent(GvDataList<? extends GvData> gridCell, boolean quickDialog) {
            GvDataType<? extends GvData> type = gridCell.getGvDataType();
            if (quickDialog && !gridCell.hasData()) {
                showQuickDialog(getAdderConfig(type));
            } else {
                ActivityEditData.start(getActivity(), type);
            }
        }

        private <T extends GvData> void showQuickDialog(LaunchableConfig<T> adderConfig) {
            if (adderConfig.getGvDataType().equals(GvImageList.GvImage.TYPE)) {
                getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                        getImageRequestCode());
                return;
            }

            Bundle args = new Bundle();
            args.putBoolean(DialogGvDataAdd.QUICK_SET, true);
            packLatLng(args);

            LauncherUiImpl.launch(adderConfig.getLaunchable(mLaunchableFactory), getActivity(),
                    adderConfig.getRequestCode(), adderConfig.getTag(), args);
        }

        private void packLatLng(Bundle args) {
            LatLng latLng = mLatLng;
            boolean fromImage = false;

            GvImageList.GvImage cover = mEditor.getCover();
            LatLng coverLatLng = cover.getLatLng();
            if (coverLatLng != null ) {
                    latLng = coverLatLng;
                    fromImage = true;
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
            mImageChooser = mEditor.getImageChooser();
            mLocationClient = new LocationClientConnector(getActivity(), BuildScreen.this);
            mLocationClient.connect();
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            executeIntent((GvDataList<? extends GvData>) item, true);
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            executeIntent((GvDataList<? extends GvData>) item, false);
        }
    }

    private class BuildScreenMenu extends MenuActionNone {
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
