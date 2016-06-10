/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
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
    private final ApplicationInstance mApp;
    private ReviewEditor<GC> mEditor;
    private LocationClient mLocationClient;
    private ImageChooser mImageChooser;
    private LatLng mLatLng;

    private PresenterReviewBuild(ApplicationInstance app, ReviewEditor<GC> editor) {
        mEditor = editor;
        mApp = app;
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
            launchQuickSetAdder(type);
        } else {
            mApp.launchEditScreen(type);
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
    public void onConnected(Location location) {
        onLocated(location);
    }

    @Override
    public void onChosenImage(GvImage image) {
        setCover(image);
    }

    @Override
    public <T extends GvData> void onReviewViewAttached(ReviewView<T> reviewView) {
        mImageChooser = mEditor.getImageChooser();
        mLocationClient = mApp.getLocationClient(this);
        mLocationClient.connect();
    }

    @Override
    public void onReviewViewDetached() {
        mLocationClient.disconnect();
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
        return mApp.getConfigUi().getAdder(dataType.getDatumName());
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

    private void launchQuickSetAdder(GvDataType<? extends GvData> type) {
        if (type.equals(GvImage.TYPE)) {
            mApp.launchImageChooser(mImageChooser, getImageRequestCode());
        } else {
            showQuickSetLaunchable(getAdderConfig(type));
        }
    }

    private void showQuickSetLaunchable(LaunchableConfig adderConfig) {
        Bundle args = new Bundle();
        args.putBoolean(AdderConfig.QUICK_SET, true);
        packLatLng(args);
        mApp.getUiLauncher().launch(adderConfig, RequestCodeGenerator.getCode(adderConfig.getTag()), args);
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

        public PresenterReviewBuild<?> build() {
            ReviewBuilderAdapter<?> adapter = mApp.getReviewBuilderAdapter();
            if (adapter == null) adapter = mApp.newReviewBuilderAdapter(mReview);

            return buildPresenter(adapter);
        }

        private <GC extends GvDataList<?>> PresenterReviewBuild<GC> buildPresenter(ReviewBuilderAdapter<GC> adapter) {
            ReviewEditor<GC> editor = newEditor(adapter,
                    mApp.getConfigUi().getShareReview(), mEditorFactory);

            return new PresenterReviewBuild<>(mApp, editor);
        }

        private <GC extends GvDataList<?>> ReviewEditor<GC> newEditor(ReviewBuilderAdapter<GC> builder,
                                                                      LaunchableConfig shareScreenUi,
                                                                      FactoryReviewEditor factory) {
            ReviewViewParams params = new ReviewViewParams();
            params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

            ReviewViewActions<GC> actions = new ReviewViewActions<>(new
                    SubjectEditBuildScreen<GC>(),
                    new RatingBarBuildScreen<GC>(),
                    new BannerButtonActionNone<GC>(Strings.Buttons.BUILD_SCREEN_BANNER),
                    new GridItemClickObserved<GC>(),
                    new MenuBuildScreen<GC>(Strings.Screens.BUILD),
                    new BuildScreenShareButton<GC>(shareScreenUi));

            return factory.newEditor(builder, params, actions);
        }
    }
}
