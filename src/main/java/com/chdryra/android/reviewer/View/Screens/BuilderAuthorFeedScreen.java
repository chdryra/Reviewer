package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverters;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviews;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderAuthorFeedScreen {
    private FeedScreen mFeedScreen;
    private ReviewView mView;

    public void newScreen(ApplicationInstance app) {
        ReviewsProvider feed = app.getReviewsProvider();
        FactoryReviews reviewFactory = app.getReviewsFactory();
        DataConverters converters = app.getDataConverters();
        BuilderChildListScreen childListFactory = app.getBuilderChildListScreen();
        FactoryReviewViewAdapter adapterFactory = app.getReviewViewAdapterFactory();

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
