/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View.Launcher.Factories;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Factories.FactoryReviewsListScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemComments;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemConfigLauncher;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.MenuComments;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;

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
        mListScreenFactory = new FactoryReviewsListScreen(mLauncherFactory, this, childListBuilder);
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
        LaunchableConfig<T> viewerConfig = mConfig.getViewerConfig(dataType);
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            LaunchableConfig<GvCommentList.GvComment> config
                    = (LaunchableConfig<GvCommentList.GvComment>) viewerConfig;
            return (GridItemAction<T>) new GridItemComments(config, this);
        } else {
            return new GridItemConfigLauncher<>(viewerConfig, mLauncherFactory, this);
        }
    }

    //TODO make type safe
    private <T extends GvData> MenuAction<T> getMenu(GvDataType<T> dataType) {
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            return (MenuAction<T>) new MenuComments();
        } else {
            return new MenuActionNone<>(dataType.getDataName());
        }
    }
}
