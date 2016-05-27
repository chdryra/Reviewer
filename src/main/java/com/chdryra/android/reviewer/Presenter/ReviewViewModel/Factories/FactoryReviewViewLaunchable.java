/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewLaunchable {
    private FactoryChildListView mChildListScreenBuilder;
    private FactoryReviewViewParams mParamsFactory;
    private ConfigUi mConfig;

    public FactoryReviewViewLaunchable(ConfigUi config, FactoryReviewViewParams paramsFactory) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mChildListScreenBuilder = new FactoryChildListView();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    public LaunchableUi newReviewsListScreen(ReviewNode node, FactoryReviewViewAdapter adapterFactory) {
        return newReviewsListScreen(node, adapterFactory, getDefaultScreenActions(GvReview.TYPE));
    }
//
//    public ReviewView<GvReview> newReviewsListScreen(ReviewNodeAsync<?> node,
//                                                     FactoryReviewViewAdapter adapterFactory,
//                                                     ReviewViewActions<GvReview> actions) {
//        return mChildListScreenBuilder.newView(adapterFactory.newChildListAdapter(node), actions);
//    }

    public ReviewView<GvReview> newReviewsListScreen(ReviewNode node,
                                                             FactoryReviewViewAdapter adapterFactory,
                                             ReviewViewActions<GvReview> actions) {
        return mChildListScreenBuilder.newView(adapterFactory.newChildListAdapter(node), actions);
    }

    public <T extends GvData> LaunchableUi newViewScreen(ReviewViewAdapter<T> adapter) {
        //TODO this is probably expensive...
        GvDataType<T> dataType = (GvDataType<T>) adapter.getGvDataType();

        ReviewViewParams params = mParamsFactory.getParams(dataType);
        ReviewViewActions<T> actions = newViewScreenActions(dataType);
        ReviewViewPerspective<T> perspective = new ReviewViewPerspective<>(adapter, actions, params);

        return new ReviewViewDefault<>(perspective);
    }

    //private
    private <T extends GvData> ReviewViewActions<T> newViewScreenActions(GvDataType<T> dataType) {
        if(dataType.equals(GvList.TYPE)) return getDefaultScreenActions(dataType);
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
            return (GridItemAction<T>) new GridItemComments(viewerConfig, this, new ParcelablePacker<GvData>());
        } else {
            return new GridItemConfigLauncher<>(viewerConfig, this, new ParcelablePacker<GvData>());
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

    @NonNull
    private <T extends GvData> ReviewViewActions<T> getDefaultScreenActions(GvDataType<T> type) {
        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> rb = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> bb = new BannerButtonActionNone<>();
        GridItemAction<T> giAction = new GridItemLauncher<>(this);
        MenuAction<T> menuAction = new MenuActionNone<>();

        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction);
    }
}
