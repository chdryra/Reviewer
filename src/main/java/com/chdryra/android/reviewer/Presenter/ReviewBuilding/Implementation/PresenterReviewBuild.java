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
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityEditData;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation.AddLocation;
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
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
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
    private final LaunchableUiLauncher mLauncher;
    private ReviewEditor<GC> mEditor;
    private LocationClientConnector mLocationClient;
    private ImageChooser mImageChooser;
    private LatLng mLatLng;

    private PresenterReviewBuild(ReviewEditor<GC> editor,
                                 ConfigUi uiConfig,
                                 LaunchableUiLauncher launcher) {
        mEditor = editor;
        mUiConfig = uiConfig;
        mLauncher = launcher;

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
            ActivityEditData.start(getActivity(), type);
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
        mLocationClient = new LocationClientConnector(getActivity(), PresenterReviewBuild.this);
        mLocationClient.connect();
    }

    //private methods
    private Activity getActivity() {
        return mEditor.getActivity();
    }

    private int getImageRequestCode() {
        return getAdderConfig(GvImage.TYPE).getRequestCode();
    }

    private void setGridItemObservation() {
        ReviewViewActions<GC> actions = mEditor.getActions();
        actions.registerObserver(this);
        GridItemClickObserved<GC> gridItem = (GridItemClickObserved<GC>) actions
                .getGridItemAction();
        gridItem.registerObserver(this);
    }

    private <T extends GvData> LaunchableConfig getAdderConfig(GvDataType<T> dataType) {
        return mUiConfig.getAdderConfig(dataType.getDatumName());
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
            showQuickDialog(getAdderConfig(type));
        }
    }

    private void launchImageChooser() {
        getActivity().startActivityForResult(mImageChooser.getChooserIntents(),
                getImageRequestCode());
    }

    private void showQuickDialog(LaunchableConfig adderConfig) {
        Bundle args = new Bundle();
        args.putBoolean(DialogGvDataAdd.QUICK_SET, true);
        packLatLng(args);
        mLauncher.launch(adderConfig, getActivity(),
                RequestCodeGenerator.getCode(adderConfig.getTag()), args);
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

    public static class Builder {
        public static final int BUTTON_TITLE = R.string.button_add_review_data;
        private static final int SCREEN_TITLE = R.string.activity_title_build_review;
        private FactoryReviewEditor mEditorFactory;
        private ApplicationInstance mApp;
        private String mReviewId;

        public interface BuildCallback {
            void onBuildFinished(PresenterReviewBuild<?> presenter);
        }

        public Builder(ApplicationInstance app, FactoryReviewEditor editorFactory) {
            mApp = app;
            mEditorFactory = editorFactory;
        }

        public Builder setTemplateReview(@Nullable String reviewId) {
            mReviewId = reviewId;
            return this;
        }

        public void build(final BuildCallback callback) {
            ReviewBuilderAdapter<?> adapter = mApp.getReviewBuilderAdapter();
            if (adapter != null) {
                buildPresenter(callback, adapter);
            } else {
                ApplicationInstance.ReviewBuilderAdapterCallback adapterCallback
                        = new ApplicationInstance.ReviewBuilderAdapterCallback() {
                    @Override
                    public void onAdapterBuilt(ReviewBuilderAdapter<?> adapter,
                                               RepositoryError error) {
                        buildPresenter(callback, adapter);
                    }
                };

                mApp.newReviewBuilderAdapter(adapterCallback, mReviewId);
            }
        }

        private void buildPresenter(BuildCallback callback, ReviewBuilderAdapter<?> adapter) {
            ConfigUi config = mApp.getConfigDataUi();
            LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
            ReviewEditor<? extends GvDataList<?>> editor
                    = newEditor(mApp.getContext(), adapter, uiLauncher,
                    config.getShareReviewConfig().getLaunchable(), mEditorFactory);

            PresenterReviewBuild<? extends GvDataList<?>> presenter = new
                    PresenterReviewBuild<>(editor, config, uiLauncher);

            callback.onBuildFinished(presenter);
        }

        private <GC extends GvDataList<?>> ReviewEditor<GC> newEditor(Context context,
                                                                      ReviewBuilderAdapter<GC>
                                                                              builder,
                                                                      LaunchableUiLauncher launcher,
                                                                      LaunchableUi shareScreenUi,
                                                                      FactoryReviewEditor factory) {
            ReviewViewParams params = new ReviewViewParams();
            params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

            String screenTitle = context.getResources().getString(SCREEN_TITLE);
            String buttonTitle = context.getResources().getString(BUTTON_TITLE);
            ReviewViewActions<GC> actions = new ReviewViewActions<>(new
                    SubjectEditBuildScreen<GC>(),
                    new RatingBarBuildScreen<GC>(), new BannerButtonActionNone<GC>(buttonTitle),
                    new GridItemClickObserved<GC>(), new MenuBuildScreen<GC>(screenTitle));

            return factory.newEditor(builder, params, actions,
                    new BuildScreenModifier(launcher, shareScreenUi));
        }
    }
}
