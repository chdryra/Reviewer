package com.chdryra.android.reviewer.Database.Interfaces;

import android.database.sqlite.SQLiteDatabase;

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
public interface ReviewLoaderDb extends ReviewerDbContract{
    SQLiteDatabase getReadableDatabase();

    <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table, String col, String val);

    ArrayList<Review> loadReviewsFromDbWhere(SQLiteDatabase db, String col, String val);

    <T extends DbTableRow> T getRowWhere(SQLiteDatabase db, DbTable<T> table, String col, String val);

    <T1 extends DbTableRow> ArrayList<T1> loadFromDataTable(SQLiteDatabase db, DbTable<T1> table,
                                                            String reviewId);
}
