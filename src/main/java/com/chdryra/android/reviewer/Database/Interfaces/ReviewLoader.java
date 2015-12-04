package com.chdryra.android.reviewer.Database.Interfaces;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewLoader {
    Review loadReview(RowReview reviewRow, ReviewerDb database, SQLiteDatabase db);
}
