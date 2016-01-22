package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;



import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb
        .Implementation.TableRowList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerReadableDb{
    TagsManager getTagsManager();

    boolean addReviewToDb(Review review, TableTransactor db);

    boolean deleteReviewFromDb(ReviewId id, TableTransactor db);

    TableTransactor beginWriteTransaction();

    @Override
    TableTransactor beginReadTransaction();

    @Override
    void endTransaction(TableTransactor db);

    @Override
    <DbRow extends DbTableRow, Type> ArrayList<Review> loadReviewsWhere(TableTransactor db,
                                                                        DbTable<DbRow> table,
                                                                        RowEntry<Type> entry);

    @Override
    <DbRow extends DbTableRow, Type> DbRow getUniqueRowWhere(TableTransactor db,
                                                             DbTable<DbRow> table,
                                                             RowEntry<Type> entry);

    @Override
    <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(TableTransactor db,
                                                                      DbTable<DbRow> table,
                                                                      RowEntry<Type> entry);
}
