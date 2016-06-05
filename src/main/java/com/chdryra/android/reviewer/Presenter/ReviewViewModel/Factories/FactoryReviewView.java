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
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ContextButtonAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 26/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private FactoryReviewViewParams mParamsFactory;
    private ConfigUi mConfig;

    public FactoryReviewView(ConfigUi config, FactoryReviewViewParams paramsFactory) {
        mConfig = config;
        mParamsFactory = paramsFactory;
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    public ReviewViewAdapter<GvReview> newReviewsListAdapter(ReviewNode node,
                                                             FactoryReviewViewAdapter
                                                                     adapterFactory) {
        return newReviewsListScreen(node, adapterFactory).getAdapter();
    }

    public ReviewsListView newReviewsListScreen(ReviewNode node,
                                                FactoryReviewViewAdapter factory) {
        return new ReviewsListView(node,
                new ReviewViewPerspective<>(factory.newChildListAdapter(node),
                        newReviewsListActions(), getReviewViewParams()));
    }

    public <T extends GvData> ReviewView<T> newViewScreen(ApplicationInstance app,
                                                          ReviewViewAdapter<T> adapter) {
        //TODO make type safe
        GvDataType<T> dataType = (GvDataType<T>) adapter.getGvDataType();

        ReviewViewParams params = mParamsFactory.getParams(dataType);
        ReviewViewActions<T> actions = newViewScreenActions(dataType, app, adapter);
        ReviewViewPerspective<T> perspective = new ReviewViewPerspective<>(adapter, actions,
                params);

        return new ReviewViewDefault<>(perspective);
    }


    //private
    private ReviewViewActions<GvReview> newReviewsListActions() {
        LaunchableUi reviewBuildUi = mConfig.getBuildReview().getLaunchable();

        GridItemReviewsList gi = new GridItemReviewsList(this,
                mConfig.getShareEdit().getLaunchable(), reviewBuildUi);
        SubjectAction<GvReview> sa = new SubjectActionNone<>();
        RatingBarAction<GvReview> rb = new RatingBarExpandGrid<>(this);
        BannerButtonAction<GvReview> bba = new BannerButtonActionNone<>();
        MenuAction<GvReview> ma = new MenuActionNone<>();

        return new ReviewsListView.Actions(sa, rb, bba, gi, ma);
    }

    @NonNull
    private ReviewViewParams getReviewViewParams() {
        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
        return params;
    }

    @Nullable
    private <T extends GvData> ContextualButtonAction<T> getContextualButton(ApplicationInstance
                                                                                     app,
                                                                             ReviewViewAdapter<T>
                                                                                     adapter) {
        GvAuthor author = adapter.getUniqueAuthor();
        if (author != null && author.isValidForDisplay()) {
            return new ContextButtonAuthor<>(app, author);
        } else {
            return null;
        }
    }

    //TODO make type safe
    private <T extends GvData> GridItemAction<T> getGridItem(GvDataType<T> dataType) {
        LaunchableConfig viewerConfig = mConfig.getViewer(dataType.getDatumName());
        if (dataType.equals(GvComment.TYPE)) {
            return (GridItemAction<T>) new GridItemComments(viewerConfig, this, new
                    ParcelablePacker<GvData>());
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

    private <T extends GvData> ReviewViewActions<T> newViewScreenActions(GvDataType<T> dataType,
                                                                         ApplicationInstance app,
                                                                         ReviewViewAdapter<T>
                                                                                 adapter) {
        if (dataType.equals(GvList.TYPE)) return newDefaultScreenActions(dataType, app, adapter);

        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> ratingBar = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> banner = new BannerButtonActionNone<>();
        GridItemAction<T> gridItem = getGridItem(dataType);
        MenuAction<T> menu = getMenu(dataType);
        ContextualButtonAction<T> context = getContextualButton(app, adapter);

        return new ReviewViewActions<>(subject, ratingBar, banner, gridItem, menu, context);
    }

    @NonNull
    private <T extends GvData> ReviewViewActions<T> newDefaultScreenActions(GvDataType<T> type,
                                                                            @Nullable
                                                                            ApplicationInstance app,
                                                                            @Nullable
                                                                            ReviewViewAdapter<T>
                                                                                        adapter) {
        SubjectAction<T> subject = new SubjectActionNone<>();
        RatingBarAction<T> rb = new RatingBarExpandGrid<>(this);
        BannerButtonAction<T> bb = new BannerButtonActionNone<>();
        GridItemAction<T> giAction = new GridItemLauncher<>(this);
        MenuAction<T> menuAction = new MenuActionNone<>();
        ContextualButtonAction<T> context = (adapter == null || app == null) ?
                null : getContextualButton(app, adapter);

        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction, context);
    }
}
