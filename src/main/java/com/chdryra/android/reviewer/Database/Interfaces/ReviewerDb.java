package com.chdryra.android.reviewer.Database.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerReadableDb{
    TagsManager getTagsManager();

    DatabaseInstance beginWriteTransaction();

    boolean addReviewToDb(Review review, DatabaseInstance db);

    boolean deleteReviewFromDb(String reviewId, DatabaseInstance db);

    @Override
    DatabaseInstance beginReadTransaction();

    @Override
    void endTransaction(DatabaseInstance db);

    @Override
    ArrayList<Review> loadReviewsFromDbWhere(DatabaseInstance db, String col, @Nullable String val);
}
