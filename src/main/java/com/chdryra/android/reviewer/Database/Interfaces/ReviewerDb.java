package com.chdryra.android.reviewer.Database.Interfaces;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Database.Interfaces.ReviewerReadableDb;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerReadableDb{
    TagsManager getTagsManager();

    SQLiteDatabase beginWriteTransaction();

    boolean addReviewToDb(Review review, SQLiteDatabase db);

    boolean deleteReviewFromDb(String reviewId, SQLiteDatabase db);
}
