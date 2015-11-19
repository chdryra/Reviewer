package com.chdryra.android.reviewer.View.ReviewViewModel.Builders;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
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
    private FactoryReviewViewAdapter mAdapterFactory;
    private FactoryLaunchableUi mLaunchableFactory;
    private FactoryReviews mReviewFactory;

    private FeedScreen mFeedScreen;
    private ReviewView mView;

    public BuilderAuthorFeedScreen(FactoryReviewViewAdapter adapterFactory,
                                   FactoryLaunchableUi launchableFactory,
                                   FactoryReviews reviewFactory) {
        mAdapterFactory = adapterFactory;
        mLaunchableFactory = launchableFactory;
        mReviewFactory = reviewFactory;
    }

    public void buildScreen(ReviewsProvider feed,
                            BuilderChildListView childListBuilder,
                            FactoryReviewViewAdapter adapterFactory,
                            FactoryLauncherUi launcherFactory,
                            FactoryLaunchableUi launchableFactory,
                            FactoryReviews reviewFactory) {
        String title = feed.getAuthor().getName() + "'s feed";
        GridItemFeedScreen gridItem = new GridItemFeedScreen(launcherFactory, mLaunchableFactory);
        SubjectAction<GvReviewOverviewList.GvReviewOverview> sa = new SubjectActionNone<>();
        RatingBarAction<GvReviewOverviewList.GvReviewOverview> rb = new RatingBarExpandGrid<>(launchableFactory);
        BannerButtonAction<GvReviewOverviewList.GvReviewOverview> bba = new BannerButtonActionNone<>();
        FeedScreenMenu menuAction = new FeedScreenMenu();
        FactoryReviewNodeComponent nodeFactory = reviewFactory.getComponentFactory();
        Review root = reviewFactory.createUserReview(title, 0f);
        ReviewNodeComponent node = nodeFactory.createReviewNodeComponent(root, true);
        for (Review review : feed.getReviews()) {
            node.addChild(nodeFactory.createReviewNodeComponent(review, false));
        }
        mFeedScreen = new FeedScreen(node, nodeFactory, gridItem);
        feed.registerObserver(mFeedScreen);
        mView = mFeedScreen.createView(childListBuilder,adapterFactory, sa, rb, bba, menuAction);
    }

    public FeedScreen getFeedScreen() {
        return mFeedScreen;
    }

    public ReviewView getView() {
        return mView;
    }
}
