/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.Database.Interfaces;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Implementation.TableRowList;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerDbContract {
    String getDatabaseName();

    SQLiteDatabase getReadableDatabase();

    TagsManager getTagsManager();

    void registerObserver(ReviewerDbObserver observer);

    Review loadReviewFromDb(String reviewId);

    void addReviewToDb(Review review);

    ArrayList<Review> loadReviewsFromDb();

    void deleteReviewFromDb(String reviewId);

    //For ReviewLoader to use
    <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table, String col, String val);

    ArrayList<Review> loadReviewsFromDbWhere(SQLiteDatabase db, String col, String val);

    <T extends DbTableRow> T getRowWhere(SQLiteDatabase db, DbTable<T> table, String col, String val);

    <T1 extends DbTableRow> ArrayList<T1> loadFromDataTable(SQLiteDatabase db, DbTable<T1> table, String reviewId);

}
