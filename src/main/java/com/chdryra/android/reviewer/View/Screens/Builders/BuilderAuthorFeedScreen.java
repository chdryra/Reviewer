package com.chdryra.android.reviewer.View.Screens.Builders;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverters;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Screens.FeedScreen;
import com.chdryra.android.reviewer.View.Screens.FeedScreenGridItem;
import com.chdryra.android.reviewer.View.Screens.FeedScreenMenu;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderAuthorFeedScreen {
    private FeedScreen mFeedScreen;
    private ReviewView mView;

    public void newScreen(ReviewsProvider feed, FactoryReviews reviewFactory,
                          DataConverters converters, BuilderChildListScreen childListFactory,
                          FactoryReviewViewAdapter adapterFactory) {
        mFeedScreen = new FeedScreen(new FeedScreenGridItem(), new FeedScreenMenu());
        mView = mFeedScreen.createView(feed, reviewFactory, converters.getGvConverter(),
                childListFactory, adapterFactory);
    }

    public FeedScreen getFeedScreen() {
        return mFeedScreen;
    }

    public ReviewView getView() {
        return mView;
    }
}
