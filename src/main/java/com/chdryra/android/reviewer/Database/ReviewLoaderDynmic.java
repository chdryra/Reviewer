package com.chdryra.android.reviewer.Database;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewUserDb;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderDynmic implements ReviewerDb.ReviewLoader {
    private FactoryReview mReviewFactory;

    public ReviewLoaderDynmic(FactoryReview reviewFactory) {
        mReviewFactory = reviewFactory;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        return new ReviewUserDb(reviewRow, database, mReviewFactory);
    }
}
