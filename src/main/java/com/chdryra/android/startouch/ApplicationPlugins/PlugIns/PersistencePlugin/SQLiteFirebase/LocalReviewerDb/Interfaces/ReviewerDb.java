/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.TableRowList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDb extends ReviewerDbReadable {
    boolean addReviewToDb(Review review, TableTransactor transactor);

    boolean deleteReviewFromDb(ReviewId id, TableTransactor transactor);

    TableTransactor beginWriteTransaction();

    @Override
    TableTransactor beginReadTransaction();

    @Override
    void endTransaction(TableTransactor db);

    @Override
    Collection<Review> loadReviews(TableTransactor transactor);

    @Override
    <DbRow extends DbTableRow, Type> Collection<Review> loadReviewsWhere(DbTable<DbRow> table,
                                                                         RowEntry<DbRow, Type>
                                                                                 clause,
                                                                         TableTransactor
                                                                                 transactor);

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
