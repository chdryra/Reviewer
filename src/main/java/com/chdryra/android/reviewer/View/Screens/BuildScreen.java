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
import android.app.Fragment;
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
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityReviewView;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.Dialogs.AddLocation;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.GvDataModel.GvBuildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreen {
    private final ReviewView          mScreen;
    private final BuildScreenGridItem mGridItem;

    private BuildScreen(Context context) {
        ReviewBuilderAdapter builder = Administrator.get(context).getReviewBuilder();

        mScreen = new ReviewView.Editor(builder, new BuildScreenModifier(builder));
        mGridItem = new BuildScreenGridItem();

        String screenTitle = context.getResources().getString(R.string.screen_title_build_review);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        mScreen.setAction(new SubjectEdit());
        mScreen.setAction(new BuildScreenRatingBar());
        mScreen.setAction(ReviewViewAction.BannerButtonAction.newDisplayButton(buttonTitle));
        mScreen.setAction(mGridItem);
        mScreen.setAction(new BuildScreenMenu(screenTitle));

        mScreen.getParams().setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);
    }

    public static ReviewView newScreen(Context context) {
        return new BuildScreen(context).getScreen();
    }

    private ReviewView getScreen() {
        return mScreen;
    }

    private void showTagDialog() {
        mGridItem.showQuickDialog(ConfigGvDataUi.getConfig(GvTagList.GvTag.TYPE));
    }

    private class BuildScreenGridItem extends ReviewViewAction.GridItemAction {
        private static final String TAG = "GridItemBuildUiListener";
        private final BuildListener           mListener;
        private       LatLng                  mLatLng;
        private       ImageChooser            mImageChooser;
        private       LocationClientConnector mLocationClient;

        private BuildScreenGridItem() {
            mListener = new BuildListener() {
            };
            registerActionListener(mListener, TAG);
        }

        @Override
        public void onUnattachReviewView() {
            super.onUnattachReviewView();
            mLocationClient.disconnect();
        }

        @Override
        public void onAttachReviewView() {
            mImageChooser = Administrator.getImageChooser(getActivity());
            mLocationClient = new LocationClientConnector(getActivity(), mListener);
            mLocationClient.connect();
        }

        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            executeIntent((GvBuildReviewList.GvBuildReview) item, true);
        }

        @Override
        public void onGridItemLongClick(GvData item, int position, View v) {
            executeIntent((GvBuildReviewList.GvBuildReview) item, false);
        }

        private void executeIntent(GvBuildReviewList.GvBuildReview gridCell,
                boolean quickDialog) {
            if (quickDialog && gridCell.getDataSize() == 0) {
                showQuickDialog(gridCell.getConfig());
            } else {
                startActivity(gridCell.getConfig());
            }
        }

        private ReviewBuilderAdapter getBuilder() {
            return (ReviewBuilderAdapter) getAdapter();
        }

        private void startActivity(ConfigGvDataUi.Config config) {
            Intent i = new Intent(getActivity(), ActivityReviewView.class);
            Administrator admin = Administrator.get(getActivity());
            admin.packView(EditScreen.newScreen(getActivity(), config.getGvDataType()), i);

            mListener.startActivity(i);
        }

        private void showQuickDialog(ConfigGvDataUi.Config config) {
            if (config.getGvDataType() == GvImageList.GvImage.TYPE) {
                mListener.startActivityForResult(mImageChooser.getChooserIntents(),
                        getImageRequestCode());
                return;
            }

            Bundle args = new Bundle();
            args.putBoolean(DialogGvDataAdd.QUICK_SET, true);
            packLatLng(args);

            ConfigGvDataUi.LaunchableConfig adderConfig = config.getAdderConfig();
            LauncherUi.launch(adderConfig.getLaunchable(), mListener, adderConfig.getRequestCode(),
                    adderConfig.getTag(), args);
        }

        private void packLatLng(Bundle args) {
            LatLng latLng = mLatLng;
            boolean fromImage = false;

            GvImageList images = (GvImageList) getBuilder().getDataBuilder(GvImageList.GvImage.TYPE)
                    .getGridData();
            if (images.size() > 0) {
                LatLng coverLatLng = images.getCovers().getItem(0).getLatLng();
                if (coverLatLng != null) {
                    latLng = coverLatLng;
                    fromImage = true;
                }
            }

            args.putParcelable(AddLocation.LATLNG, latLng);
            args.putBoolean(AddLocation.FROM_IMAGE, fromImage);
        }

        private int getImageRequestCode() {
            return ConfigGvDataUi.getConfig(GvImageList.GvImage.TYPE).getAdderConfig()
                    .getRequestCode();
        }

        private abstract class BuildListener extends Fragment implements
                ImageChooser.ImageChooserListener,
                LocationClientConnector.Locatable {

            @Override
            public void onLocated(Location location) {
                mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onLocationClientConnected(Location location) {
                onLocated(location);
            }

            @Override
            public void onImageChosen(GvImageList.GvImage image) {
                image.setIsCover(true);
                ReviewBuilderAdapter.DataBuilderAdapter builder = getBuilder().getDataBuilder
                        (GvImageList
                        .GvImage.TYPE);
                builder.add(image);
                builder.setData();
                getReviewView().updateUi();
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                ActivityResultCode result = ActivityResultCode.get(resultCode);

                boolean imageRequested = requestCode == getImageRequestCode();

                if (imageRequested && mImageChooser.chosenImageExists(result, data)) {
                    mImageChooser.getChosenImage(this);
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }

                getReviewView().updateUi();
            }
        }
    }

    private class BuildScreenMenu extends ReviewViewAction.MenuAction {
        public static final  int MENU_AVERAGE_ID = R.id.menu_item_average_rating;
        private static final int MENU            = R.menu.fragment_review_options;

        private final MenuActionItem    mActionItem;
        private       ReviewView.Editor mEditor;

        private BuildScreenMenu(String title) {
            super(MENU, title, true);
            mActionItem = new MenuActionItem() {
                @Override
                public void doAction(MenuItem item) {
                    ReviewBuilderAdapter builder = (ReviewBuilderAdapter) getAdapter();
                    builder.setRatingIsAverage(true);
                    mEditor.setRating(builder.getRating());
                }
            };
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            mEditor = ReviewView.Editor.cast(getReviewView());
        }

        @Override
        protected void addMenuItems() {
            addMenuActionItem(mActionItem, MENU_AVERAGE_ID, false);
        }
    }

    private class BuildScreenRatingBar extends EditScreen.RatingBar {
        @Override
        public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                boolean fromUser) {
            super.onRatingChanged(ratingBar, rating, fromUser);
            if (fromUser) ((ReviewBuilderAdapter) getAdapter()).setRatingIsAverage(false);
            ((ReviewBuilderAdapter) getAdapter()).setRating(rating);
        }
    }

    private class BuildScreenModifier implements ReviewView.ViewModifier {
        private final ReviewBuilderAdapter mBuilder;

        private BuildScreenModifier(ReviewBuilderAdapter builder) {
            mBuilder = builder;
        }

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
                @Override
                public void onClick(View v) {
                    requestShareIntent(parent);
                }
            });

            parent.addView(shareButton);
            parent.addView(divider);

            return v;
        }

        private void requestShareIntent(FragmentReviewView parent) {
            Activity activity = parent.getActivity();

            if (parent.getSubject().length() == 0) {
                Toast.makeText(activity, R.string.toast_enter_subject,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (mBuilder.getDataSize(GvTagList.GvTag.TYPE) == 0) {
                Toast.makeText(activity, R.string.toast_enter_tag,
                        Toast.LENGTH_SHORT).show();
                showTagDialog();
                return;
            }

            Intent i = new Intent(activity, ActivityReviewView.class);
            Administrator admin = Administrator.get(activity);
            admin.packView(ShareScreen.newScreen(activity), i);

            activity.startActivity(i);
        }
    }
}
