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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ContextButtonStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuNewReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

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

    public ReviewViewAdapter<GvReviewRef> newReviewsListAdapter(ReviewNode node,
                                                                FactoryReviewViewAdapter adapterFactory) {
        return newReviewsListScreen(node, adapterFactory, false).getAdapter();
    }

    public ReviewsListView newReviewsListScreen(ReviewNode node,
                                                FactoryReviewViewAdapter factory,
                                                boolean withMenu) {
        return new ReviewsListView(node,
                new ReviewViewPerspective<>(factory.newChildListAdapter(node),
                        newReviewsListActions(withMenu), getReviewViewParams()));
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
    @NonNull
    private ReviewViewParams getReviewViewParams() {
        ReviewViewParams params = new ReviewViewParams();
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setCoverManager(false).setCellHeight(full).setCellWidth(full).setGridAlpha(trans);
        return params;
    }

    @Nullable
    private <T extends GvData> ContextualButtonAction<T>
    getContextualButton(@Nullable ApplicationInstance app, @Nullable ReviewViewAdapter<T> adapter) {
        if(app == null || adapter == null) return null;
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
        if (dataType.equals(GvComment.TYPE)) {
            return (GridItemAction<T>) new GridItemComments(viewerConfig, this, new
                    ParcelablePacker<GvDataParcelable>());
        } else {
            return new GridItemConfigLauncher<>(viewerConfig, this, new ParcelablePacker<GvDataParcelable>());
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

    private ReviewViewActions<GvReviewRef> newReviewsListActions(boolean withMenu) {
        BuildScreenLauncher buildUiLauncher = new BuildScreenLauncher();
        GridItemReviewsList gi = new GridItemReviewsList(this,
                mConfig.getShareEdit().getLaunchable(), buildUiLauncher);
        SubjectAction<GvReviewRef> sa = new SubjectActionNone<>();
        RatingBarAction<GvReviewRef> rb = new RatingBarExpandGrid<>(this);
        BannerButtonAction<GvReviewRef> bba = new BannerButtonActionNone<>();
        MenuAction<GvReviewRef> ma = withMenu ?
                new MenuNewReview<GvReviewRef>(buildUiLauncher) : new MenuActionNone<GvReviewRef>();

        return new ReviewsListView.Actions(sa, rb, bba, gi, ma);
    }

    private <T extends GvData> ReviewViewActions<T> newViewScreenActions(GvDataType<T> dataType,
                                                                         ApplicationInstance app,
                                                                         ReviewViewAdapter<T>
                                                                                 adapter) {
        if (dataType.equals(GvSize.TYPE)) return newDefaultScreenActions(dataType, app, adapter);

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
        MenuAction<T> menuAction = getMenuAction(app, adapter);
        ContextualButtonAction<T> context = getContextualButton(app, adapter);

        return new ReviewViewActions<>(subject, rb, bb, giAction, menuAction, context);
    }

    @NonNull
    private <T extends GvData> MenuAction<T> getMenuAction(@Nullable ApplicationInstance app,
                                                                   @Nullable ReviewViewAdapter<T> adapter) {
        if(app == null || adapter == null) {
            return new MenuActionNone<>();
        }

        ReviewStamp stamp = adapter.getStamp();
        if(!stamp.isValid()) {
            return new MenuActionNone<>();
        } else {
            return new MenuNewReview<>(new BuildScreenLauncher(), stamp.getReviewId());
        }
    }
}
