package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerReadableDb extends ReviewerDbContract {
    TableTransactor beginReadTransaction();

    void endTransaction(TableTransactor db);

    ArrayList<Review> loadReviewsFromDbWhere(TableTransactor db, String col, @Nullable String val);

    <T extends DbTableRow> T getRowWhere(TableTransactor db, DbTable<T> table, String col, @Nullable String val);

    <T1 extends DbTableRow> ArrayList<T1> loadFromDataTable(TableTransactor db, DbTable<T1> table,
                                                            String reviewId);

    <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table, String col, @Nullable String val);
}
