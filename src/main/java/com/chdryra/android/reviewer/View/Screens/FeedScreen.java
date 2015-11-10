package com.chdryra.android.reviewer.View.Screens;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements DialogAlertFragment.DialogAlertListener{
    private FeedScreenGridItem mGridItem;

    public FeedScreen(FeedScreenGridItem gridItemAction) {
        mGridItem = gridItemAction;
    }

    public ReviewView createView(ReviewsRepository feed,
                                 PublishDate publishDate,
                                 FactoryReview reviewFactory,
                                 MdGvConverter converter,
                                 BuilderChildListScreen childListFactory,
                                 FactoryReviewViewAdapter adapterFactory,
                                 FeedScreenMenu menuAction) {
        Author author = feed.getAuthor();
        String title = author.getName() + "'s feed";
        ReviewsRepositoryScreen screen =
                new ReviewsRepositoryScreen(feed, reviewFactory, title, publishDate);
        return screen.createView(converter, childListFactory, adapterFactory, mGridItem, menuAction);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
    }
}
