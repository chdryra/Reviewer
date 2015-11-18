/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View.Launcher;

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
import com.chdryra.android.reviewer.View.Screens.Implementation.GridItemComments;
import com.chdryra.android.reviewer.View.Screens.Implementation.GridItemViewerLauncher;
import com.chdryra.android.reviewer.View.Screens.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Screens.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Screens.MenuComments;
import com.chdryra.android.reviewer.View.Screens.RbExpandGrid;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;
import com.chdryra.android.reviewer.View.Screens.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLaunchable {
    private static final String TAG = "FactoryLaunchable";
    private ConfigDataUi mConfig;

    public FactoryLaunchable(ConfigDataUi config) {
        mConfig = config;
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

    public LaunchableUi newReviewsListScreen(ReviewNode node,
                                             FactoryReviewViewAdapter adapterFactory) {
        return adapterFactory.newReviewsListAdapter(node).getReviewView();
    }

    public <T extends GvData> LaunchableUi newScreen(ReviewViewAdapter<T> adapter) {
        //TODO this is probably expensive...
        GvDataType<T> dataType = adapter.getGridData().getGvDataType();

        ReviewViewParams params = getParams(dataType);

        ReviewViewActions actions = new ReviewViewActions();
        actions.setAction(new RbExpandGrid(this));
        actions.setAction(getGridItem(dataType));
        actions.setAction(getMenu(dataType));

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
            return new GridItemViewerLauncher<>(viewerConfig, this);
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
