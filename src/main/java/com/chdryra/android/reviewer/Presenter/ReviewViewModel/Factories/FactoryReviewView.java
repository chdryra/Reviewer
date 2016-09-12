/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
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
    private final BuildScreenLauncher mLauncher;
    private FactoryReviewViewAdapter mAdapterFactory;

    public FactoryReviewView(ConfigUi config,
                             FactoryReviewViewParams paramsFactory,
                             BuildScreenLauncher launcher) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mLauncher = launcher;
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    public void setAdapterFactory(FactoryReviewViewAdapter adapterFactory) {
        mAdapterFactory = adapterFactory;
        mAdapterFactory.setReviewViewFactory(this);
    }

    public ReviewViewAdapter<GvNode> newReviewsListAdapter(ReviewNode node) {
        return newReviewsListView(node).getAdapter();
    }

    public ReviewsListView newFeedView(ReviewNode node) {
        return newReviewsListView(node,
                mAdapterFactory.newFeedAdapter(node),
                new FactoryReviewViewActions.Feed(mLauncher));
    }

    public ReviewsListView newReviewsListView(ReviewNode node) {
        return newReviewsListView(node,
                mAdapterFactory.newChildListAdapter(node),
                new FactoryReviewViewActions.ReviewsList(mLauncher));
    }

    public <T extends GvData> ReviewView<T> newDefaultView(ReviewViewAdapter<T> adapter,
                                                           ReviewLauncher launcher,
                                                           AuthorsRepository repo) {
        //TODO make type safe
        GvDataType<T> dataType = (GvDataType<T>) adapter.getGvDataType();

        ReviewViewParams params = mParamsFactory.getParams(dataType);
        ReviewViewActions<T> actions = newDefaultActions(adapter, launcher, repo);

        return new ReviewViewDefault<>(new ReviewViewPerspective<>(adapter, actions, params));
    }

    //private
    @NonNull
    private ReviewsListView newReviewsListView(ReviewNode node,
                                               ReviewViewAdapter<GvNode> adapter,
                                               FactoryReviewViewActions.ReviewsList actionsFactory) {
        GridItemReviewsList gi = actionsFactory.newGridItemLauncher(this, mConfig.getShareEdit().getLaunchable());
        SubjectAction<GvNode> sa = actionsFactory.newSubjectNoAction();
        RatingBarAction<GvNode> rb = actionsFactory.newRatingBarExpandGrid(this);
        BannerButtonAction<GvNode> bba = actionsFactory.newBannerButtonNoAction();
        MenuAction<GvNode> ma = actionsFactory.newMenu();

        ReviewViewActions<GvNode> actions = new ReviewsListView.Actions(sa, rb, bba, gi, ma);

        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);

        return new ReviewsListView(node, new ReviewViewPerspective<>(adapter, actions, params));
    }

    private <T extends GvData> ReviewViewActions<T> newDefaultActions(ReviewViewAdapter<T> adapter,
                                                                      ReviewLauncher launcher,
                                                                      AuthorsRepository repo) {
        //TODO make type safe
        GvDataType<T> dataType = (GvDataType<T>) adapter.getGvDataType();

        FactoryReviewViewActions<T> factory = getActionsFactory(dataType);

        boolean isSummaryScreen = dataType.equals(GvSize.Reference.TYPE);

        SubjectAction<T> subject = factory.newSubjectNoAction();
        RatingBarAction<T> rb = factory.newRatingBarExpandGrid(this);
        BannerButtonAction<T> bb = factory.newBannerButtonNoAction();

        GridItemAction<T> gridItem = isSummaryScreen ? factory.newGridItemLauncher(this) :
                factory.newGridItemLauncher(this, mConfig.getViewer(dataType.getDatumName()));

        ReviewStamp stamp = adapter.getStamp();
        MenuAction<T> menu = getMenuAction(factory, isSummaryScreen, stamp);

        ContextualButtonAction<T> context = stamp.isValid() ?
                factory.newContextButtonStamp(launcher, stamp, repo) : null;

        return new ReviewViewActions<>(subject, rb, bb, gridItem, menu, context);
    }

    private <T extends GvData> MenuAction<T> getMenuAction(FactoryReviewViewActions<T> factory,
                                                           boolean isSummaryScreen,
                                                           ReviewStamp stamp) {
        MenuAction<T> menu;
        if(!isSummaryScreen) {
            menu = factory.newMenuViewData();
        } else if (!stamp.isValid()) {
            menu = factory.newMenuNoAction();
        } else {
            menu = factory.newMenuCopyReview(stamp.getReviewId());
        }
        return menu;
    }

    private <T extends GvData> FactoryReviewViewActions<T> getActionsFactory(GvDataType<T> dataType) {
        return FactoryReviewViewActions.newTypedFactory(dataType, mLauncher);
    }
}
