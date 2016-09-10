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
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ContextButtonStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiSettings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiNewReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .MaiSplitCommentRefs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuNewReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private final FactoryReviewViewParams mParamsFactory;
    private final ConfigUi mConfig;
    private FactoryReviewViewAdapter mAdapterFactory;
    private BuildScreenLauncher mBuildScreenLauncher;

    public FactoryReviewView(ConfigUi config, FactoryReviewViewParams paramsFactory) {
        mConfig = config;
        mParamsFactory = paramsFactory;
        mBuildScreenLauncher = new BuildScreenLauncher();
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
        return newReviewsListView(node, adapter, newMenuNewReview(GvNode.TYPE, true, null));
    }

    public ReviewsListView newReviewsListScreen(ReviewNode node) {
        ReviewViewAdapter<GvNode> adapter = mAdapterFactory.newChildListAdapter(node);
        return newReviewsListView(node, adapter, new MenuActionNone<GvNode>());
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
                                               MenuAction<GvNode> ma) {
        ReviewViewPerspective<GvNode> perspective
                = new ReviewViewPerspective<>(adapter, newReviewsListActions(ma), getParamsForReviewsList());
        return new ReviewsListView(node, perspective);
    }

    @Nullable
    private <T extends GvData> ContextualButtonAction<T>
    getContextualButton(@Nullable ApplicationInstance app, @Nullable ReviewViewAdapter<T> adapter) {
        if (app == null || adapter == null) return null;
        ReviewStamp stamp = adapter.getStamp();
        if (stamp.isValid()) {
            return new ContextButtonStamp<>(app, stamp);
        } else {
            return null;
        }
    }

    //TODO make type safe
    private <T extends GvData> GridItemAction<T> getGridItem(GvDataType<T> dataType) {
        LaunchableConfig viewerConfig = mConfig.getViewer(dataType.getDatumName());
        if (dataType.equals(GvComment.Reference.TYPE)) {
            return (GridItemAction<T>) new GridItemComments(viewerConfig, this, new
                    ParcelablePacker<GvDataParcelable>());
        } else {
            return new GridItemConfigLauncher<>(viewerConfig, this, new
                    ParcelablePacker<GvDataParcelable>());
        }
    }

    //TODO make type safe
    private <T extends GvData> MenuAction<T> getMenu(GvDataType<T> dataType) {
        if (dataType.equals(GvComment.Reference.TYPE)) {
            return (MenuAction<T>) new MenuComments(new MaiSplitCommentRefs());
        } else {
            return new MenuActionNone<>(dataType.getDataName());
        }
    }

    private ReviewViewActions<GvNode> newReviewsListActions(MenuAction<GvNode> ma) {
        BuildScreenLauncher buildUiLauncher = new BuildScreenLauncher();
        GridItemReviewsList gi = new GridItemReviewsList(this,
                mConfig.getShareEdit().getLaunchable(), buildUiLauncher);
        SubjectAction<GvNode> sa = new SubjectActionNone<>();
        RatingBarAction<GvNode> rb = new RatingBarExpandGrid<>(this);
        BannerButtonAction<GvNode> bba = new BannerButtonActionNone<>();

        return new ReviewsListView.Actions(sa, rb, bba, gi, ma);
    }

    private <T extends GvData> ReviewViewActions<T> newViewScreenActions(ApplicationInstance app,
                                                                         GvDataType<T> dataType,
                                                                         ReviewViewAdapter<T>
                                                                                 adapter) {
        if (dataType.equals(GvSize.Reference.TYPE)) {
            return newSummaryScreenActions(dataType, app, adapter);
        }

        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> ratingBar = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> banner = new BannerButtonActionNone<>();
        GridItemAction<T> gridItem = getGridItem(dataType);
        MenuAction<T> menu = getMenu(dataType);
        ContextualButtonAction<T> context = getContextualButton(app, adapter);

        return new ReviewViewActions<>(subject, ratingBar, banner, gridItem, menu, context);
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newSummaryScreenActions(GvDataType<T> type,
                                                                            @Nullable
                                                                            ApplicationInstance app,
                                                                            @Nullable
                                                                            ReviewViewAdapter<T>
                                                                                    adapter) {
        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> rb = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> bb = new BannerButtonActionNone<>();
        GridItemAction<T> giAction = new GridItemLauncher<>(this);
        MenuAction<T> menuAction = getMenuAction(type, app, adapter);
        ContextualButtonAction<T> context = getContextualButton(app, adapter);

        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction, context);
    }

    @NonNull
    private <T extends GvData> MenuAction<T> getMenuAction(GvDataType<T> dataType,
                                                           @Nullable ApplicationInstance app,
                                                           @Nullable ReviewViewAdapter<T> adapter) {
        if (app == null || adapter == null) {
            return new MenuActionNone<>();
        }

        ReviewStamp stamp = adapter.getStamp();
        if (!stamp.isValid()) {
            return new MenuActionNone<>();
        } else {
            return newMenuNewReview(dataType, false, stamp.getReviewId());
        }
    }

    @NonNull
    private <T extends GvData> MenuAction<T> newMenuNewReview(GvDataType<T> type,
                                                              boolean isFeed,
                                                              @Nullable ReviewId template) {
        MaiNewReview<T> mai = new MaiNewReview<>(mBuildScreenLauncher, template);
        return isFeed ? new MenuFeed<>(mai, new MaiSettings<T>()) : new MenuNewReview<>(mai);
    }
}
