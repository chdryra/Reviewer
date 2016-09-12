/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiNewReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiSettings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .MaiSplitCommentRefs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuCopyReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewActions<T extends GvData> {
    private GvDataType<T> mDataType;
    private BuildScreenLauncher mLauncher;

    public static <T extends GvData> FactoryReviewViewActions<T> newTypedFactory(GvDataType<T> dataType, BuildScreenLauncher launcher) {
        //TODO make type safe
        if(dataType.equals(GvComment.Reference.TYPE)) {
            return (FactoryReviewViewActions<T>) new FactoryReviewViewActions.Comments(launcher);
        } else {
            return new FactoryReviewViewActions<>(dataType, launcher);
        }
    }

    protected BuildScreenLauncher getLauncher() {
        return mLauncher;
    }

    public FactoryReviewViewActions(GvDataType<T> dataType, BuildScreenLauncher launcher) {
        mDataType = dataType;
        mLauncher = launcher;
    }

    public MenuAction<T> newMenuCopyReview(ReviewId template) {
        return new MenuCopyReview<>(newNewReviewItem(template));
    }

    public MenuAction<T> newMenuNoAction() {
        return new MenuActionNone<>();
    }

    public MenuAction<T> newMenuViewData() {
        return new MenuActionNone<>(mDataType.getDataName());
    }

    public SubjectAction<T> newSubjectNoAction() {
        return new SubjectActionNone<>();
    }

    public RatingBarAction<T> newRatingBarExpandGrid(FactoryReviewView factory) {
        return new RatingBarExpandGrid<>(factory);
    }

    public BannerButtonAction<T> newBannerButtonNoAction() {
        return new BannerButtonActionNone<>();
    }

    public GridItemAction<T> newGridItemLauncher(FactoryReviewView factory) {
        return new GridItemLauncher<>(factory);
    }

    public GridItemAction<T> newGridItemLauncher(FactoryReviewView factory, LaunchableConfig viewerConfig) {
        return new GridItemConfigLauncher<>(viewerConfig, factory, new ParcelablePacker<GvDataParcelable>());
    }

    public ContextualButtonAction<T> newContextButtonStamp(ReviewLauncher launcher,
                                                           ReviewStamp stamp,
                                                           AuthorsRepository repo) {
        return new ContextButtonStamp<>(launcher, stamp, repo);
    }

    protected MenuAction.MenuActionItem<T> newNewReviewItem(@Nullable ReviewId template) {
        return new MaiNewReview<>(mLauncher, template);
    }

    public static class Comments extends FactoryReviewViewActions<GvComment.Reference> {
        public Comments(BuildScreenLauncher launcher) {
            super(GvComment.Reference.TYPE, launcher);
        }

        @Override
        public GridItemLauncher<GvComment.Reference> newGridItemLauncher(FactoryReviewView factory, LaunchableConfig viewerConfig) {
            return new GridItemComments(viewerConfig, factory, new ParcelablePacker<GvDataParcelable>());
        }

        @Override
        public MenuAction<GvComment.Reference> newMenuViewData() {
            return new MenuComments(new MaiSplitCommentRefs());
        }
    }

    public static class ReviewsList extends FactoryReviewViewActions<GvNode> {
        public ReviewsList(BuildScreenLauncher launcher) {
            super(GvNode.TYPE, launcher);
        }

        public GridItemReviewsList newGridItemLauncher(FactoryReviewView factory, LaunchableUiAlertable launchable) {
            return new GridItemReviewsList(factory, launchable, getLauncher());
        }

        public MenuAction<GvNode> newMenu() {
            return newMenuNoAction();
        }
    }

    public static class Feed extends ReviewsList {
        public Feed(BuildScreenLauncher launcher) {
            super(launcher);
        }

        @Override
        public GridItemReviewsList newGridItemLauncher(FactoryReviewView factory, LaunchableUiAlertable launchable) {
            return new GridItemFeed(factory, launchable, getLauncher());
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            return new MenuFeed<>(newNewReviewItem(null), new MaiFollow<GvNode>(), new MaiSettings<GvNode>());
        }
    }
}
