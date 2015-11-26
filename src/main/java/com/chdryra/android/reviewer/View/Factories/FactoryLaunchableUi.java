/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View.Factories;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewsListScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.GridItemComments;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.GridItemConfigLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.MenuComments;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLaunchableUi {
    private static final String TAG = "FactoryLaunchable";

    private ConfigDataUi mConfig;
    private FactoryReviewViewParams mParamsFactory;
    private FactoryLauncherUi mLauncherFactory;
    private FactoryReviewsListScreen mListScreenFactory;

    public FactoryLaunchableUi(ConfigDataUi config,
                               FactoryReviewViewParams paramsFactory,
                               BuilderChildListView childListBuilder) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mLauncherFactory = new FactoryLauncherUi();
        mListScreenFactory = new FactoryReviewsListScreen(this, childListBuilder);
    }

    public FactoryReviewsListScreen getListScreenFactory() {
        return mListScreenFactory;
    }

    public LaunchableUi newLaunchable(Class<? extends LaunchableUi> uiClass) throws
            RuntimeException {
        if (uiClass == null) return null;

        try {
            return uiClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create UI for " + uiClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessException: trying to create " + uiClass.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public LaunchableUi newReviewsListScreen(ReviewNode node, FactoryReviewViewAdapter adapterFactory) {
        return mListScreenFactory.newReviewsListScreen(node, adapterFactory);
    }

    public <T extends GvData> LaunchableUi newViewScreen(ReviewViewAdapter<T> adapter) {
        //TODO this is probably expensive...
        GvDataType<T> dataType = adapter.getGridData().getGvDataType();

        ReviewViewParams params = mParamsFactory.getParams(dataType);
        ReviewViewActions<T> actions = newActions(dataType);
        ReviewViewPerspective<T> perspective = new ReviewViewPerspective<>(adapter, params, actions);

        return new ReviewViewDefault<>(perspective);
    }

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode, String tag, Bundle args) {
        ui.launch(mLauncherFactory.newLauncher(commissioner, requestCode, tag, args));
    }

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode, String tag){
        ui.launch(mLauncherFactory.newLauncher(commissioner, requestCode, tag));
    }

    public void launch(LaunchableConfig config, Activity commissioner, Bundle args) {
        launch(config.getLaunchable(this), commissioner, config.getRequestCode(), config.getTag(), args);
    }

    private <T extends GvData> ReviewViewActions<T> newActions(GvDataType<T> dataType) {
        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> ratingBar = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> bannerButton = new BannerButtonActionNone<>();
        GridItemAction<T> gridItem = getGridItem(dataType);
        MenuAction<T> menu = getMenu(dataType);
        return new ReviewViewActions<>(subject, ratingBar, bannerButton, gridItem, menu);
    }

    //TODO make type safe
    private <T extends GvData> GridItemAction<T> getGridItem(GvDataType<T> dataType) {
        LaunchableConfig viewerConfig = mConfig.getViewerConfig(dataType.getDatumName());
        if (dataType.equals(GvComment.TYPE)) {
            return (GridItemAction<T>) new GridItemComments(viewerConfig, this, new GvDataPacker<>());
        } else {
            return new GridItemConfigLauncher<>(viewerConfig, this, new GvDataPacker<>());
        }
    }

    //TODO make type safe
    private <T extends GvData> MenuAction<T> getMenu(GvDataType<T> dataType) {
        if (dataType.equals(GvComment.TYPE)) {
            return (MenuAction<T>) new MenuComments();
        } else {
            return new MenuActionNone<>(dataType.getDataName());
        }
    }
}
