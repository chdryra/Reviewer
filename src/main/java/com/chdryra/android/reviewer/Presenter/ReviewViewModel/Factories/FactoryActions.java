/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Strings;
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
        .GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemLaunchAuthor;
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
        .RatingBarActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectBannerFilter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher
        .ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActions<T extends GvData> implements FactoryReviewViewActions<T> {
    private GvDataType<T> mDataType;

    public static <T extends GvData> FactoryReviewViewActions<T> newTypedFactory(GvDataType<T> dataType) {
        return new FactoryActions<>(dataType);
    }

    public FactoryActions(GvDataType<T> dataType) {
        mDataType = dataType;
    }

    protected GvDataType<T> getDataType() {
        return mDataType;
    }

    @Override
    public MenuAction<T> newMenu() {
        return new MenuActionNone<>();
    }

    @Override
    public SubjectAction<T> newSubject() {
        return new SubjectActionNone<>();
    }

    @Override
    public RatingBarAction<T> newRatingBar() {
        return new RatingBarActionNone<>();
    }

    @Override
    public BannerButtonAction<T> newBannerButton() {
        return new BannerButtonActionNone<>();
    }

    @Override
    public GridItemAction<T> newGridItem() {
        return new GridItemActionNone<>();
    }

    @Nullable
    @Override
    public ContextualButtonAction<T> newContextButton() {
        return null;
    }

    public  static class ViewData<T extends GvData> extends FactoryActions<T> {
        private FactoryReviewView mFactory;
        private ReviewLauncher mLauncher;
        private ReviewStamp mStamp;
        private LaunchableConfig mConfig;
        private AuthorsRepository mRepo;

        public ViewData(GvDataType<T> dataType,
                        FactoryReviewView factory,
                        ReviewLauncher launcher,
                        ReviewStamp stamp,
                        AuthorsRepository repo,
                        LaunchableConfig config) {
            super(dataType);
            mFactory = factory;
            mLauncher = launcher;
            mStamp = stamp;
            mRepo = repo;
            mConfig = config;
        }

        protected FactoryReviewView getFactory() {
            return mFactory;
        }

        public LaunchableConfig getConfig() {
            return mConfig;
        }

        @Override
        public MenuAction<T> newMenu() {
            return new MenuActionNone<>(getDataType().getDataName());
        }

        @Override
        public RatingBarAction<T> newRatingBar() {
            return new RatingBarExpandGrid<>(mFactory);
        }

        @Override
        public GridItemAction<T> newGridItem() {
            return new GridItemConfigLauncher<>(mConfig, mFactory,
                    new ParcelablePacker<GvDataParcelable>());
        }

        @Nullable
        @Override
        public ContextualButtonAction<T> newContextButton() {
            return mStamp.isValid() ? new ContextButtonStamp<T>(mLauncher, mStamp, mRepo) : null;
        }
    }

    public  static class Summary extends FactoryActions<GvSize.Reference> {
        private FactoryReviewView mFactory;
        private ReviewLauncher mLauncher;
        private ReviewStamp mStamp;
        private AuthorsRepository mRepo;
        private BuildScreenLauncher mBuildScreenLauncher;

        public Summary(FactoryReviewView factory,
                       ReviewLauncher launcher,
                       ReviewStamp stamp,
                       AuthorsRepository repo,
                       BuildScreenLauncher buildScreenLauncher) {
            super(GvSize.Reference.TYPE);
            mFactory = factory;
            mLauncher = launcher;
            mStamp = stamp;
            mRepo = repo;
            mBuildScreenLauncher = buildScreenLauncher;
        }

        @Override
        public MenuAction<GvSize.Reference> newMenu() {
            String title = Strings.Screens.SUMMARY;
            MenuAction<GvSize.Reference> menu = new MenuActionNone<>(title);
            if (mStamp.isValid()) {
                MaiNewReview<GvSize.Reference> mai
                        = new MaiNewReview<>(mBuildScreenLauncher, mStamp.getReviewId());
                menu = new MenuCopyReview<>(mai, title);
            }

            return menu;
        }

        @Override
        public RatingBarAction<GvSize.Reference> newRatingBar() {
            return new RatingBarExpandGrid<>(mFactory);
        }

        @Override
        public GridItemAction<GvSize.Reference> newGridItem() {
            return new GridItemLauncher<>(mFactory);
        }

        @Nullable
        @Override
        public ContextualButtonAction<GvSize.Reference> newContextButton() {
            return mStamp.isValid() ?
                    new ContextButtonStamp<GvSize.Reference>(mLauncher, mStamp, mRepo) : null;
        }
    }

    protected MenuAction.MenuActionItem<T> newNewReviewItem(BuildScreenLauncher launcher, @Nullable ReviewId template) {
        return new MaiNewReview<>(launcher, template);
    }

    public static class Comments extends ViewData<GvComment.Reference> {
        public Comments(FactoryReviewView factory,
                        ReviewLauncher launcher,
                        ReviewStamp stamp,
                        AuthorsRepository repo,
                        LaunchableConfig config) {
            super(GvComment.Reference.TYPE, factory, launcher, stamp, repo, config);
        }

        @Override
        public GridItemLauncher<GvComment.Reference> newGridItem() {
            return new GridItemComments(getConfig(), getFactory(), new ParcelablePacker<GvDataParcelable>());
        }

        @Override
        public MenuAction<GvComment.Reference> newMenu() {
            return new MenuComments(new MaiSplitCommentRefs());
        }
    }

    public static class ReviewsList extends FactoryActions<GvNode> {
        private FactoryReviewView mFactoryReviewView;
        private LaunchableUiAlertable mShareEdit;
        private BuildScreenLauncher mLauncher;

        public ReviewsList(FactoryReviewView factoryReviewView, LaunchableUiAlertable shareEdit,
                           BuildScreenLauncher launcher) {
            super(GvNode.TYPE);
            mFactoryReviewView = factoryReviewView;
            mShareEdit = shareEdit;
            mLauncher = launcher;
        }

        protected FactoryReviewView getFactoryReviewView() {
            return mFactoryReviewView;
        }

        protected LaunchableUiAlertable getShareEdit() {
            return mShareEdit;
        }

        protected BuildScreenLauncher getLauncher() {
            return mLauncher;
        }

        @Override
        public RatingBarAction<GvNode> newRatingBar() {
            return new RatingBarExpandGrid<>(mFactoryReviewView);
        }


        @Override
        public GridItemReviewsList newGridItem() {
            return new GridItemReviewsList(mFactoryReviewView, mShareEdit, getLauncher());
        }
    }

    public static class Feed extends ReviewsList {
        public Feed(FactoryReviewView factoryReviewView, LaunchableUiAlertable shareEdit,
                    BuildScreenLauncher launcher) {
            super(factoryReviewView, shareEdit, launcher);
        }

        @Override
        public GridItemReviewsList newGridItem() {
            return new GridItemFeed(getFactoryReviewView(), getShareEdit(), getLauncher());
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            return new MenuFeed<>(newNewReviewItem(getLauncher(), null),
                    new MaiFollow<GvNode>(getFactoryReviewView()),
                    new MaiSettings<GvNode>());
        }
    }

    public static class Search<T extends GvData> extends FactoryActions<T> {
        private FactoryReviewView mFactory;

        public Search(GvDataType<T> dataType, FactoryReviewView factory) {
            super(dataType);
            mFactory = factory;
        }

        public FactoryReviewView getReviewViewFactory() {
            return mFactory;
        }

        @Override
        public SubjectAction<T> newSubject() {
            return newSubjectBannerFilter();
        }

        public SubjectBannerFilter<T> newSubjectBannerFilter() {
            return new SubjectBannerFilter<>(Strings.Buttons.SEARCH, Strings.SEARCHING);
        }

        @Override
        public BannerButtonAction<T> newBannerButton() {
            return newSubjectBannerFilter();
        }

        @Override
        public GridItemAction<T> newGridItem() {
            return new GridItemLauncher<>(mFactory);
        }
    }

    public static class SearchAuthor extends Search<GvAuthor> {
        public SearchAuthor(FactoryReviewView factory) {
            super(GvAuthor.TYPE, factory);
        }

        @Override
        public GridItemAction<GvAuthor> newGridItem() {
            return new GridItemLaunchAuthor(getReviewViewFactory());
        }
    }
}
