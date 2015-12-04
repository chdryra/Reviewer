package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.FeedScreenMenu;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemDeleteRequester;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFeedScreen {
    private FeedScreen mMutableFeedScreen;
    private ReviewView mView;
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryReviewViewLaunchable mLaunchableFactory;
    private LaunchableUiLauncher mLauncher;
    private FactoryReviews mReviewFactory;
    private LaunchableConfig mReviewBuildScreenConfig;

    public FactoryFeedScreen(FactoryReviewViewAdapter adapterFactory,
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
                            FeedScreen.DeleteRequestListener listener) {
        String title = feed.getAuthor().getName() + "'s feed";
        mMutableFeedScreen = new FeedScreen(feed, title, mReviewFactory, listener);

        GridItemDeleteRequester gi = new GridItemDeleteRequester(mLaunchableFactory, mLauncher, mMutableFeedScreen);
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
