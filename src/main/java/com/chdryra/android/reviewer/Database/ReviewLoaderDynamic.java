package com.chdryra.android.reviewer.Database;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderDynamic implements ReviewerDb.ReviewLoader {
    private FactoryReviewNodeComponent mReviewFactory;

    public ReviewLoaderDynamic(FactoryReviewNodeComponent reviewFactory) {
        mReviewFactory = reviewFactory;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        return new ReviewUserDb(reviewRow, database, mReviewFactory);
    }
}
