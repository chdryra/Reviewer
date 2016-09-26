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
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.BuildScreenShareButton;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemClickObserved;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.GridItemShareScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.MenuBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishButton;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.RatingEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.SubjectEditBuildScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ContextButtonStamp;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemConfigLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemLaunchAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiDeleteReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiNewReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiSearchAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiSettings;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MaiSplitCommentRefs;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuCopyDeleteReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFollow;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectBannerFilter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryActions<T extends GvData> implements FactoryReviewViewActions<T> {
    private GvDataType<T> mDataType;

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
        private UserSession mSession;

        public Summary(FactoryReviewView factory,
                       ReviewLauncher launcher,
                       ReviewStamp stamp,
                       AuthorsRepository repo,
                        UserSession session) {
            super(GvSize.Reference.TYPE);
            mFactory = factory;
            mLauncher = launcher;
            mStamp = stamp;
            mRepo = repo;
            mSession = session;
        }

        @Override
        public MenuAction<GvSize.Reference> newMenu() {
            String title = Strings.Screens.SUMMARY;
            MenuAction<GvSize.Reference> menu = new MenuActionNone<>(title);
            if (mStamp.isValid()) {
                boolean isUser = mStamp.getAuthorId().toString().equals(mSession.getAuthorId()
                        .toString());

                ReviewId reviewId = mStamp.getReviewId();
                MaiNewReview<GvSize.Reference> maiCopy = new MaiNewReview<>(reviewId);
                MaiDeleteReview<GvSize.Reference> maiDelete = isUser ?
                        new MaiDeleteReview<GvSize.Reference>(reviewId) : null;
                menu = new MenuCopyDeleteReview<>(maiCopy, maiDelete, title);
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

    protected MenuAction.MenuActionItem<T> newNewReviewItem(@Nullable ReviewId template) {
        return new MaiNewReview<>(template);
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

    static class ReviewsList extends FactoryActions<GvNode> {
        private FactoryReviewView mFactoryReviewView;
        private LaunchableUi mShareEdit;
        private AuthorId mAuthorId;

        ReviewsList(FactoryReviewView factoryReviewView, LaunchableUi shareEdit,
                           @Nullable AuthorId authorId) {
            super(GvNode.TYPE);
            mFactoryReviewView = factoryReviewView;
            mShareEdit = shareEdit;
            mAuthorId = authorId;
        }

        protected FactoryReviewView getFactoryReviewView() {
            return mFactoryReviewView;
        }

        protected LaunchableUi getShareEdit() {
            return mShareEdit;
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            return mAuthorId != null ?
                    new MenuFollow<>(new MaiFollow<GvNode>(mAuthorId)) : super.newMenu();
        }

        @Override
        public RatingBarAction<GvNode> newRatingBar() {
            return new RatingBarExpandGrid<>(mFactoryReviewView);
        }

        @Override
        public GridItemReviewsList newGridItem() {
            return new GridItemReviewsList(mFactoryReviewView, mShareEdit);
        }
    }

    public static class Feed extends ReviewsList {
        public Feed(FactoryReviewView factoryReviewView, LaunchableUi shareEdit) {
            super(factoryReviewView, shareEdit, null);
        }

        @Override
        public GridItemReviewsList newGridItem() {
            return new GridItemFeed(getFactoryReviewView(), getShareEdit());
        }

        @Override
        public MenuAction<GvNode> newMenu() {
            return new MenuFeed<>(newNewReviewItem(null),
                    new MaiSearchAuthors<GvNode>(getFactoryReviewView()),
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

    public static class Publish extends FactoryActions<GvSocialPlatform> {
        private static final String TITLE = Strings.Screens.SHARE;;
        private PlatformAuthoriser mAuthoriser;
        private GvSocialPlatformList mPlatforms;
        private PublishAction mPublishAction;

        public Publish(PlatformAuthoriser authoriser,
                       GvSocialPlatformList platforms,
                       PublishAction publishAction) {
            super(GvSocialPlatform.TYPE);
            mAuthoriser = authoriser;
            mPlatforms = platforms;
            mPublishAction = publishAction;
        }

        @Override
        public BannerButtonAction<GvSocialPlatform> newBannerButton() {
            return new BannerButtonActionNone<>(TITLE);
        }

        @Override
        public GridItemAction<GvSocialPlatform> newGridItem() {
            return new GridItemShareScreen(mAuthoriser);
        }

        @Override
        public MenuAction<GvSocialPlatform> newMenu() {
            return new MenuActionNone<>(TITLE);
        }

        @Nullable
        @Override
        public ContextualButtonAction<GvSocialPlatform> newContextButton() {
            return new PublishButton(mPlatforms, mPublishAction);
        }
    }

    public static class Build<GC extends GvDataList<? extends GvDataParcelable>> extends FactoryActions<GC> {
        private LaunchableConfig mShareScreenUi;

        public Build(GvDataType<GC> dataType, LaunchableConfig shareScreenUi) {
            super(dataType);
            mShareScreenUi = shareScreenUi;
        }

        @Override
        public SubjectAction<GC> newSubject() {
            return new SubjectEditBuildScreen<>();
        }

        @Override
        public RatingBarAction<GC> newRatingBar() {
            return new RatingEditBuildScreen<>();
        }

        @Override
        public BannerButtonAction<GC> newBannerButton() {
            return new BannerButtonActionNone<>(Strings.Buttons.BUILD_SCREEN_BANNER);
        }

        @Override
        public GridItemAction<GC> newGridItem() {
            return new GridItemClickObserved<>();
        }

        @Override
        public MenuAction<GC> newMenu() {
            return new MenuBuildScreen<>(Strings.Screens.BUILD);
        }

        @Nullable
        @Override
        public ContextualButtonAction<GC> newContextButton() {
            return new BuildScreenShareButton<>(mShareScreenUi);
        }
    }
}
