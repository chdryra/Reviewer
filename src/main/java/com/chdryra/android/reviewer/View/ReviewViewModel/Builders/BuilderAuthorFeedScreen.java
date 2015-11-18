package com.chdryra.android.reviewer.View.ReviewViewModel.Builders;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.FeedScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.FeedScreenMenu;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.GridItemFeedScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderAuthorFeedScreen {
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryLaunchable mLaunchableFactory;
    private FactoryReviews mReviewFactory;

    private FeedScreen mFeedScreen;
    private ReviewView mView;

    public BuilderAuthorFeedScreen(FactoryReviewViewAdapter adapterFactory,
                                   FactoryLaunchable launchableFactory,
                                   FactoryReviews reviewFactory) {
        mAdapterFactory = adapterFactory;
        mLaunchableFactory = launchableFactory;
        mReviewFactory = reviewFactory;
    }

    public void buildScreen(ReviewsProvider feed, BuilderChildListView childListBuilder) {
        String title = feed.getAuthor().getName() + "'s feed";
        GridItemFeedScreen gridItem = new GridItemFeedScreen(mLaunchableFactory);
        FeedScreenMenu menuAction = new FeedScreenMenu();
        mFeedScreen = new FeedScreen(feed, title, mAdapterFactory, mReviewFactory,
                gridItem, menuAction);
        mView = mFeedScreen.createView(childListBuilder);
    }

    public FeedScreen getFeedScreen() {
        return mFeedScreen;
    }

    public ReviewView getView() {
        return mView;
    }
}
