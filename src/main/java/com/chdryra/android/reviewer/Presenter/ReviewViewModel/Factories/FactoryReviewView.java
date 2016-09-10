/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private final ConfigUi mConfig;
    private final FactoryReviewViewParams mParamsFactory;
    private FactoryReviewViewAdapter mAdapterFactory;
    private BuildScreenLauncher mLauncher;

    public FactoryReviewView(ConfigUi config, FactoryReviewViewParams paramsFactory) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mLauncher = new BuildScreenLauncher();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    public void setAdapterFactory(FactoryReviewViewAdapter adapterFactory) {
        mAdapterFactory = adapterFactory;
        mAdapterFactory.setReviewViewFactory(this);
    }

    public ReviewViewAdapter<GvNode> newReviewsListAdapter(ReviewNode node) {
        return newReviewsListScreen(node).getAdapter();
    }

    public ReviewsListView newFeedScreen(ReviewNode node) {
        ReviewViewAdapter<GvNode> adapter = mAdapterFactory.newFeedAdapter(node);
        FactoryReviewViewActions.ReviewsList actionsFactory = new FactoryReviewViewActions.ReviewsList(mLauncher);
        return newReviewsListView(node, adapter, actionsFactory, actionsFactory.newMenuFeed());
    }

    public ReviewsListView newReviewsListScreen(ReviewNode node) {
        ReviewViewAdapter<GvNode> adapter = mAdapterFactory.newChildListAdapter(node);
        FactoryReviewViewActions.ReviewsList actionsFactory = new FactoryReviewViewActions.ReviewsList(mLauncher);
        return newReviewsListView(node, adapter, actionsFactory, actionsFactory.newMenuNoAction());
    }

    public <T extends GvData> ReviewView<T> newViewScreen(ApplicationInstance app,
                                                          ReviewViewAdapter<T> adapter) {
        GvDataType<?> dataType = adapter.getGvDataType();

        //TODO make type safe
        ReviewViewParams params = mParamsFactory.getParams(dataType);
        ReviewViewActions<T> actions = newViewScreenActions(app, (GvDataType<T>) dataType, adapter);
        ReviewViewPerspective<T> perspective = new ReviewViewPerspective<>(adapter, actions,
                params);

        return new ReviewViewDefault<>(perspective);
    }

    //private
    @NonNull
    private ReviewViewParams getParamsForReviewsList() {
        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
        return params;
    }

    @NonNull
    private ReviewsListView newReviewsListView(ReviewNode node,
                                               ReviewViewAdapter<GvNode> adapter,
                                               FactoryReviewViewActions.ReviewsList factory,
                                               MenuAction<GvNode> ma) {
        ReviewViewPerspective<GvNode> perspective
                = new ReviewViewPerspective<>(adapter, newReviewsListActions(factory, ma),
                getParamsForReviewsList());
        return new ReviewsListView(node, perspective);
    }

    @Nullable
    private <T extends GvData> ContextualButtonAction<T>
    getContextualButton(FactoryReviewViewActions<T> factory, @Nullable ApplicationInstance app, @Nullable ReviewViewAdapter<T> adapter) {
        if (app == null || adapter == null || !adapter.getStamp().isValid()) return null;
        return factory.newContextButtonStamp(app, adapter.getStamp());
    }

    private ReviewViewActions<GvNode> newReviewsListActions(FactoryReviewViewActions.ReviewsList factory, MenuAction<GvNode> ma) {
        GridItemReviewsList gi = factory.newGridItemLauncher(this, mConfig.getShareEdit().getLaunchable());
        SubjectAction<GvNode> sa = factory.newSubjectNoAction();
        RatingBarAction<GvNode> rb = factory.newRatingBarExpandGrid(this);
        BannerButtonAction<GvNode> bba = factory.newBannerButtonNoAction();

        return new ReviewsListView.Actions(sa, rb, bba, gi, ma);
    }

    private <T extends GvData> ReviewViewActions<T> newViewScreenActions(ApplicationInstance app,
                                                                         GvDataType<T> dataType,
                                                                         ReviewViewAdapter<T>
                                                                                 adapter) {
        FactoryReviewViewActions<T> factory = getActionsFactory(dataType);

        boolean isSummaryScreen = dataType.equals(GvSize.Reference.TYPE);

        SubjectAction<T> subject = factory.newSubjectNoAction();
        RatingBarAction<T> rb = factory.newRatingBarExpandGrid(this);
        BannerButtonAction<T> bb = factory.newBannerButtonNoAction();
        GridItemAction<T> gridItem = isSummaryScreen ? factory.newGridItemLauncher(this) :
                factory.newGridItemLauncher(this, mConfig.getViewer(dataType.getDatumName()));
        MenuAction<T> menu = getMenuAction(factory, isSummaryScreen, adapter);
        ContextualButtonAction<T> context = getContextualButton(factory, app, adapter);

        return new ReviewViewActions<>(subject, rb, bb, gridItem, menu, context);
    }

    private <T extends GvData> MenuAction<T> getMenuAction(FactoryReviewViewActions<T> factory,
                                                           boolean isSummaryScreen,
                                                           @Nullable ReviewViewAdapter<T> adapter) {
        MenuAction<T> menu;
        if(!isSummaryScreen) {
            menu = factory.newMenuViewData();
        } else if (adapter == null || !adapter.getStamp().isValid()) {
            menu = factory.newMenuNoAction();
        } else {
            menu = factory.newMenuCopyReview(adapter.getStamp().getReviewId());
        }
        return menu;
    }

    private <T extends GvData> FactoryReviewViewActions<T> getActionsFactory(GvDataType<T> dataType) {
        return FactoryReviewViewActions.newTypedFactory(dataType, mLauncher);
    }
}
