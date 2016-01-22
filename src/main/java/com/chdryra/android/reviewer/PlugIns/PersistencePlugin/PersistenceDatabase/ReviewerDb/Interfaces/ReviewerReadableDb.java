package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerReadableDb extends ReviewerDbContract {
    TableTransactor beginReadTransaction();

    void endTransaction(TableTransactor db);

    ArrayList<Review> loadReviewsWhere(TableTransactor db, String col, @Nullable String val);

    <T extends DbTableRow> T getUniqueRowWhere(TableTransactor db, DbTable<T> table, String col,
                                               @Nullable String val);

    <T extends DbTableRow> ArrayList<T> loadFromDataTable(TableTransactor db, DbTable<T> table,
                                                            String reviewId);

    <T extends DbTableRow> ArrayList<T> getRowsWhere(TableTransactor db, DbTable<T> table, String col, @Nullable String val);
}
