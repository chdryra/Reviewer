/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces;



import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.RowEntry;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerDbReadable {
    boolean addReviewToDb(Review review, TagsManager tagsManager, TableTransactor transactor);

    boolean deleteReviewFromDb(ReviewId id, TagsManager tagsManager, TableTransactor transactor);

    TableTransactor beginWriteTransaction();

    @Override
    TableTransactor beginReadTransaction();

    @Override
    void endTransaction(TableTransactor db);

    @Override
    <DbRow extends DbTableRow, Type> Collection<Review> loadReviewsWhere(DbTable<DbRow> table,
                                                                        RowEntry<DbRow, Type> clause,
                                                                        TableTransactor transactor);

    @Override
    <DbRow extends DbTableRow, Type> DbRow getUniqueRowWhere(DbTable<DbRow> table,
                                                             RowEntry<DbRow, Type> clause,
                                                             TableTransactor transactor);

    @Override
    <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                      RowEntry<DbRow, Type> clause,
                                                                      TableTransactor transactor);

    @Override
    <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table,
                                                             TableTransactor transactor);
}
