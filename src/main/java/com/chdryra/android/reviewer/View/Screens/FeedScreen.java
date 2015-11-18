package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Screens.Implementation.GridItemFeedScreen;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements DialogAlertFragment.DialogAlertListener{
    private GridItemFeedScreen mGridItem;
    private FeedScreenMenu mMenu;

    public FeedScreen(GridItemFeedScreen gridItemAction, FeedScreenMenu menuAction) {
        mGridItem = gridItemAction;
        mMenu = menuAction;
    }

    public ReviewView createView(ReviewsProvider feed,
                                 FactoryReviews reviewFactory,
                                 ConverterGv converter,
                                 BuilderChildListScreen childListFactory,
                                 FactoryReviewViewAdapter adapterFactory) {
        Author author = feed.getAuthor();
        String title = author.getName() + "'s feed";
        ReviewsRepositoryScreen screen =
                new ReviewsRepositoryScreen(feed, reviewFactory, title);
        return screen.createView(converter.getConverterReviews(), converter.getConverterImages(),
                childListFactory, adapterFactory, mGridItem, mMenu);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
    }
}
