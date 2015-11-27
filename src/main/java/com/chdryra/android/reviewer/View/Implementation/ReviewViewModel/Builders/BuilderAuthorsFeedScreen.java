package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.FeedScreen;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.FeedScreenMenu;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.GridItemDeleteRequester;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderAuthorsFeedScreen {
    private FeedScreen mMutableFeedScreen;
    private ReviewView mView;
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryReviewViewLaunchable mLaunchableFactory;
    private LaunchableUiLauncher mLauncher;
    private FactoryReviews mReviewFactory;
    private LaunchableConfig mReviewBuildScreenConfig;

    public BuilderAuthorsFeedScreen(FactoryReviewViewAdapter adapterFactory,
                                    FactoryReviewViewLaunchable launchableFactory,
                                    LaunchableUiLauncher launcher,
                                    FactoryReviews reviewFactory,
                                    LaunchableConfig reviewBuildScreenConfig) {
        mAdapterFactory = adapterFactory;
        mLaunchableFactory = launchableFactory;
        mLauncher = launcher;
        mReviewFactory = reviewFactory;
        mReviewBuildScreenConfig = reviewBuildScreenConfig;
    }

    public void buildScreen(ReviewsFeedMutable feed,
                            GridItemDeleteRequester.DeleteRequestListener listener) {
        String title = feed.getAuthor().getName() + "'s feed";
        mMutableFeedScreen = new FeedScreen(feed, title, mReviewFactory);

        GridItemDeleteRequester gi = new GridItemDeleteRequester(mLaunchableFactory, mLauncher, listener);
        SubjectAction<GvReviewOverview> sa = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverview> rb = new RatingBarExpandGrid<>(mLaunchableFactory, mLauncher);
        BannerButtonAction<GvReviewOverview> bba = new BannerButtonActionNone<>();
        FeedScreenMenu ma = new FeedScreenMenu(mLauncher, mReviewBuildScreenConfig);

        feed.registerObserver(mMutableFeedScreen);
        mView = mMutableFeedScreen.createView(mLaunchableFactory,
                mAdapterFactory, sa, rb, bba, gi, ma);
    }

    public FeedScreen getFeedScreen() {
        return mMutableFeedScreen;
    }

    public ReviewView getView() {
        return mView;
    }
}
