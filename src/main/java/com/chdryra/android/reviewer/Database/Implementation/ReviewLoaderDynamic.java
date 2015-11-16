package com.chdryra.android.reviewer.Database.Implementation;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderDynamic implements ReviewLoader {
    private FactoryReviewNodeComponent mReviewFactory;

    public ReviewLoaderDynamic(FactoryReviewNodeComponent reviewFactory) {
        mReviewFactory = reviewFactory;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db) {
        return new ReviewUserDb(reviewRow, database, mReviewFactory);
    }
}
