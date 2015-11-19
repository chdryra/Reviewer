/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View.Launcher.Factories;

import android.util.Log;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Factories.FactoryReviewsListScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
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
    private FactoryLauncherUi mLauncherFactory;

    public FactoryLaunchableUi(ConfigDataUi config,
                               FactoryLauncherUi launcherFactory) {
        mConfig = config;
        mLauncherFactory = launcherFactory;
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

    public LaunchableUi newReviewsListScreen(ReviewNode node, FactoryReviewViewAdapter adapterFactory,
                                             FactoryReviewsListScreen listScreenFactory) {
        return listScreenFactory.newReviewsListScreen(node, adapterFactory);
    }

    public <T extends GvData> LaunchableUi newViewScreen(ReviewViewAdapter<T> adapter) {
        //TODO this is probably expensive...
        GvDataType<T> dataType = adapter.getGridData().getGvDataType();

        ReviewViewParams params = getParams(dataType);

        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> ratingBar = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> bannerButton = new BannerButtonActionNone<>();
        GridItemAction<T> gridItem = getGridItem(dataType);
        MenuAction<T> menu = getMenu(dataType);
        ReviewViewActions<T> actions = new ReviewViewActions<>(subject, ratingBar, bannerButton,
                gridItem, menu);

        ReviewViewPerspective<T> perspective = new ReviewViewPerspective<>(adapter, params, actions);

        return new ReviewViewDefault<>(perspective);
    }

    //TODO make type safe
    private <T extends GvData> GridItemAction<T> getGridItem(GvDataType<T> dataType) {
        LaunchableConfig<T> viewerConfig = mConfig.getLaunchableConfigs(dataType).getViewerConfig();
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

    private ReviewViewParams getParams(GvDataType dataType) {
        ReviewViewParams params = new ReviewViewParams();
        if (dataType.equals(GvImageList.GvImage.TYPE)) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            params.setCellHeight(half).setCellWidth(half);
        }

        return params;
    }
}
