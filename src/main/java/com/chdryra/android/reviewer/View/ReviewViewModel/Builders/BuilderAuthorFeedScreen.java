package com.chdryra.android.reviewer.View.ReviewViewModel.Builders;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.FeedScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.FeedScreenMenu;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemFeedScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.RatingBarExpandGrid;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.SubjectAction;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderAuthorFeedScreen {
    private FeedScreen mFeedScreen;
    private ReviewView mView;
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryLauncherUi mLauncherFactory;
    private FactoryLaunchableUi mLaunchableFactory;
    private FactoryReviews mReviewFactory;

    public BuilderAuthorFeedScreen(FactoryReviewViewAdapter adapterFactory,
                                   FactoryLauncherUi launcherFactory,
                                   FactoryLaunchableUi launchableFactory,
                                   FactoryReviews reviewFactory) {
        mAdapterFactory = adapterFactory;
        mLauncherFactory = launcherFactory;
        mLaunchableFactory = launchableFactory;
        mReviewFactory = reviewFactory;
    }

    public void buildScreen(ReviewsProvider feed,
                            BuilderChildListView childListBuilder) {
        String title = feed.getAuthor().getName() + "'s feed";
        GridItemFeedScreen gi = new GridItemFeedScreen(mLauncherFactory, mLaunchableFactory);
        SubjectAction<GvReviewOverviewList.GvReviewOverview> sa = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverviewList.GvReviewOverview> rb = new RatingBarExpandGrid<>(mLaunchableFactory);
        BannerButtonAction<GvReviewOverviewList.GvReviewOverview> bba = new BannerButtonActionNone<>();
        FeedScreenMenu ma = new FeedScreenMenu();

        mFeedScreen = new FeedScreen(feed, title, mReviewFactory);
        feed.registerObserver(mFeedScreen);
        mView = mFeedScreen.createView(childListBuilder, mAdapterFactory, sa, rb, bba, gi, ma);
    }

    public FeedScreen getFeedScreen() {
        return mFeedScreen;
    }

    public ReviewView getView() {
        return mView;
    }
}
