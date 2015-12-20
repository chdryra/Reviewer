package com.chdryra.android.reviewer.Database.Interfaces;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Implementation.TableRowList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerReadableDb extends ReviewerDbContract {
    SQLiteDatabase beginReadTransaction();

    void endTransaction(SQLiteDatabase db);

    ArrayList<Review> loadReviewsFromDbWhere(SQLiteDatabase db, String col, @Nullable String val);

    <T extends DbTableRow> T getRowWhere(SQLiteDatabase db, DbTable<T> table, String col, @Nullable String val);

    <T1 extends DbTableRow> ArrayList<T1> loadFromDataTable(SQLiteDatabase db, DbTable<T1> table,
                                                            String reviewId);

    <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table, String col, @Nullable String val);
}