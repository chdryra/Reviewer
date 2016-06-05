/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditData;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.AddLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.AdderConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewBuild<GC extends GvDataList<?>> implements
        ImageChooser.ImageChooserListener,
        LocationClientConnector.Locatable,
        ReviewViewActions.ReviewViewAttachedObserver,
        GridItemClickObserved.ClickObserver<GC> {
    private final ConfigUi mUiConfig;
    private final UiLauncher mLauncher;
    private final Activity mActivity;
    private ReviewEditor<GC> mEditor;
    private LocationClientConnector mLocationClient;
    private ImageChooser mImageChooser;
    private LatLng mLatLng;

    private PresenterReviewBuild(ReviewEditor<GC> editor,
                                 ConfigUi uiConfig,
                                 UiLauncher launcher,
                                 Activity activity) {
        mEditor = editor;
        mUiConfig = uiConfig;
        mLauncher = launcher;
        mActivity = activity;
        setGridItemObservation();
    }

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

    public void executeIntent(GvDataList<? extends GvData> gridCell, boolean quickDialog) {
        GvDataType<? extends GvData> type = gridCell.getGvDataType();
        if (quickDialog && !gridCell.hasData()) {
            launchAdder(type);
        } else {
            //TODO can this be moved to LaunchablesList?
            ActivityEditData.start(mActivity, type);
        }
    }

    @Override
    public void onGridItemClick(GC item, int position, View v) {
        executeIntent(item, true);
    }

    @Override
    public void onGridItemLongClick(GC item, int position, View v) {
        executeIntent(item, false);
    }

    @Override
    public void onLocated(Location location) {
        setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onLocationClientConnected(Location location) {
        onLocated(location);
    }

    @Override
    public void onChosenImage(GvImage image) {
        setCover(image);
    }

    @Override
    public <T extends GvData> void onReviewViewAttached(ReviewView<T> reviewView) {
        mImageChooser = mEditor.getImageChooser();
        mLocationClient = new LocationClientConnector(mActivity, PresenterReviewBuild.this);
        mLocationClient.connect();
    }

    //private methods
    private int getImageRequestCode() {
        return getAdderConfig(GvImage.TYPE).getRequestCode();
    }

    private void setGridItemObservation() {
        ReviewViewActions<GC> actions = mEditor.getActions();
        actions.registerObserver(this);
        GridItemClickObserved<GC> gi = (GridItemClickObserved<GC>) actions.getGridItemAction();
        gi.registerObserver(this);
    }

    private <T extends GvData> LaunchableConfig getAdderConfig(GvDataType<T> dataType) {
        return mUiConfig.getAdder(dataType.getDatumName());
    }

    private void setCover(GvImage image) {
        mEditor.setCover(image);
    }

    private void updateScreen() {
        mEditor.notifyBuilder();
    }

    private void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    private void launchAdder(GvDataType<? extends GvData> type) {
        if (type.equals(GvImage.TYPE)) {
            launchImageChooser();
        } else {
            showQuickSetLaunchable(getAdderConfig(type));
        }
    }

    private void launchImageChooser() {
        mActivity.startActivityForResult(mImageChooser.getChooserIntents(), getImageRequestCode());
    }

    private void showQuickSetLaunchable(LaunchableConfig adderConfig) {
        Bundle args = new Bundle();
        args.putBoolean(AdderConfig.QUICK_SET, true);
        packLatLng(args);
        mLauncher.launch(adderConfig, RequestCodeGenerator.getCode(adderConfig.getTag()), args);
    }

    private void packLatLng(Bundle args) {
        LatLng latLng = mLatLng;
        boolean fromImage = false;

        GvImage cover = mEditor.getCover();
        LatLng coverLatLng = cover.getLatLng();
        if (coverLatLng != null) {
            latLng = coverLatLng;
            fromImage = true;
        }

        args.putParcelable(AddLocation.LATLNG, latLng);
        args.putBoolean(AddLocation.FROM_IMAGE, fromImage);
    }

    public static class Builder  {
        public static final int BUTTON_TITLE = R.string.button_add_review_data;
        private static final int SCREEN_TITLE = R.string.activity_title_build_review;
        private FactoryReviewEditor mEditorFactory;
        private ApplicationInstance mApp;
        private Review mReview;

        public Builder(ApplicationInstance app, FactoryReviewEditor editorFactory) {
            mApp = app;
            mEditorFactory = editorFactory;
        }

        public Builder setTemplateReview(Review review) {
            mReview = review;
            return this;
        }

        public PresenterReviewBuild<?> build(Activity activity) {
            ReviewBuilderAdapter<?> adapter = mApp.getReviewBuilderAdapter();
            if (adapter == null) adapter = mApp.newReviewBuilderAdapter(mReview);

            return buildPresenter(activity, adapter);
        }

        private <GC extends GvDataList<?>> PresenterReviewBuild<GC> buildPresenter(Activity activity,
                                                                                   ReviewBuilderAdapter<GC> adapter) {
            ConfigUi config = mApp.getConfigUi();
            UiLauncher uiLauncher = mApp.getUiLauncher();
            ReviewEditor<GC> editor = newEditor(mApp.getContext(), adapter,
                    config.getShareReview(), mEditorFactory);

            return new PresenterReviewBuild<>(editor, config, uiLauncher, activity);
        }

        private <GC extends GvDataList<?>> ReviewEditor<GC> newEditor(Context context,
                                                                      ReviewBuilderAdapter<GC> builder,
                                                                      LaunchableConfig shareScreenUi,
                                                                      FactoryReviewEditor factory) {
            ReviewViewParams params = new ReviewViewParams();
            params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

            String screenTitle = context.getResources().getString(SCREEN_TITLE);
            String buttonTitle = context.getResources().getString(BUTTON_TITLE);
            ReviewViewActions<GC> actions = new ReviewViewActions<>(new
                    SubjectEditBuildScreen<GC>(),
                    new RatingBarBuildScreen<GC>(), new BannerButtonActionNone<GC>(buttonTitle),
                    new GridItemClickObserved<GC>(), new MenuBuildScreen<GC>(screenTitle),
                    new BuildScreenShareButton<GC>(shareScreenUi));

            return factory.newEditor(builder, params, actions);
        }
    }
}
