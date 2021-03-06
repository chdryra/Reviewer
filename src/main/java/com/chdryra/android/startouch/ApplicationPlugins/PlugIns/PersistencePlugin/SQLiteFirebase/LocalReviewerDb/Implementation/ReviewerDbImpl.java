/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.ContractorDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbImpl implements ReviewerDb {
    private final ContractorDb<ReviewerDbContract> mContractor;
    private final ReviewerDbContract mTables;
    private final DataValidator mDataValidator;
    private final ReviewTransactor mReviewTransactor;
    private final FactoryDbTableRow mRowFactory;

    public ReviewerDbImpl(ContractorDb<ReviewerDbContract> contractor,
                          ReviewTransactor reviewTransactor,
                          FactoryDbTableRow rowFactory,
                          DataValidator dataValidator) {
        mContractor = contractor;
        mTables = contractor.getContract();
        mReviewTransactor = reviewTransactor;
        mRowFactory = rowFactory;
        mDataValidator = dataValidator;
    }

    @Override
    public TableTransactor beginWriteTransaction() {
        TableTransactor transactor = mContractor.getWriteableTransactor();
        transactor.beginTransaction();
        return transactor;
    }

    @Override
    public TableTransactor beginReadTransaction() {
        TableTransactor transactor = mContractor.getReadableTransactor();
        transactor.beginTransaction();
        return transactor;
    }

    @Override
    public void endTransaction(TableTransactor transactor) {
        transactor.endTransaction();
    }

    @Override
    public <DbRow extends DbTableRow, Type> Collection<Review>
    loadReviewsWhere(DbTable<DbRow> table, RowEntry<DbRow, Type> clause, TableTransactor
            transactor) {
        ArrayList<Review> reviews = new ArrayList<>();
        ArrayList<ReviewId> reviewsLoaded = new ArrayList<>();

        for (RowEntry<RowReview, ?> reviewClause : findReviewTableClauses(table, clause,
                transactor)) {
            for (RowReview reviewRow : getRowsWhere(getReviewsTable(), reviewClause, transactor)) {
                loadReviewIfNotLoaded(reviewRow, reviews, reviewsLoaded, transactor);
            }
        }

        return reviews;
    }

    @Override
    public Collection<Review> loadReviews(TableTransactor transactor) {
        ArrayList<Review> reviews = new ArrayList<>();
        ArrayList<ReviewId> reviewsLoaded = new ArrayList<>();

        for (RowReview reviewRow : loadTable(getReviewsTable(), transactor)) {
            loadReviewIfNotLoaded(reviewRow, reviews, reviewsLoaded, transactor);
        }

        return reviews;
    }

    @Override
    public <DbRow extends DbTableRow, Type> DbRow getUniqueRowWhere(DbTable<DbRow> table,
                                                                    RowEntry<DbRow, Type> clause,
                                                                    TableTransactor transactor) {
        TableRowList<DbRow> rows = transactor.getRowsWhere(table, clause, mRowFactory);
        if (rows.size() > 1) throw new IllegalStateException("More than one row found!");

        return rows.size() == 0 ? mRowFactory.emptyRow(table.getRowClass()) : rows.get(0);
    }

    @Override
    public <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                             RowEntry<DbRow,
                                                                                     Type> clause,
                                                                             TableTransactor
                                                                                     transactor) {
        return transactor.getRowsWhere(table, clause, mRowFactory);
    }

    @Override
    public <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table,
                                                                    TableTransactor transactor) {
        return transactor.loadTable(table, mRowFactory);
    }

    @Override
    public boolean addReviewToDb(Review review, TableTransactor transactor) {
        mReviewTransactor.addReviewToDb(review, this, transactor);
        return true;
    }

    @Override
    public boolean deleteReviewFromDb(ReviewId reviewId, TableTransactor
            transactor) {
        RowEntry<RowReview, String> clause = asClause(RowReview.class, RowReview.REVIEW_ID,
                reviewId.toString());
        RowReview row = getUniqueRowWhere(getReviewsTable(), clause, transactor);
        if (!row.hasData(mDataValidator)) return false;

        mReviewTransactor.deleteReviewFromDb(row, this, transactor);

        return true;
    }

    @Override
    public DbTable<RowReview> getReviewsTable() {
        return mTables.getReviewsTable();
    }

    @Override
    public DbTable<RowCriterion> getCriteriaTable() {
        return mTables.getCriteriaTable();
    }

    @Override
    public DbTable<RowComment> getCommentsTable() {
        return mTables.getCommentsTable();
    }

    @Override
    public DbTable<RowFact> getFactsTable() {
        return mTables.getFactsTable();
    }

    @Override
    public DbTable<RowLocation> getLocationsTable() {
        return mTables.getLocationsTable();
    }

    @Override
    public DbTable<RowImage> getImagesTable() {
        return mTables.getImagesTable();
    }

    @Override
    public DbTable<RowAuthorName> getAuthorsTable() {
        return mTables.getAuthorsTable();
    }

    @Override
    public DbTable<RowTag> getTagsTable() {
        return mTables.getTagsTable();
    }

    @Override
    public ArrayList<DbTable<? extends DbTableRow>> getTables() {
        return mTables.getTables();
    }

    @Override
    public ArrayList<String> getTableNames() {
        return mTables.getTableNames();
    }

    private void loadReviewIfNotLoaded(RowReview row, ArrayList<Review> reviews,
                                       ArrayList<ReviewId> reviewsLoaded,
                                       TableTransactor transactor) {
        if (reviewsLoaded.contains(row.getReviewId()) || !row.hasData(mDataValidator)) return;

        Review review = mReviewTransactor.loadReview(row, this, transactor);
        reviews.add(review);
        reviewsLoaded.add(review.getReviewId());
    }

    @NonNull
    private <DbRow extends DbTableRow, Type> HashSet<RowEntry<RowReview, ?>>
    findReviewTableClauses(DbTable<DbRow> table, RowEntry<DbRow, Type> clause,
                           TableTransactor transactor) {
        HashSet<RowEntry<RowReview, ?>> reviewClauses;
        //TODO make type safe
        if (table.equals(getReviewsTable())) {
            reviewClauses = new HashSet<>();
            reviewClauses.add((RowEntry<RowReview, ?>) clause);
        } else if (table.equals(getAuthorsTable())) {
            reviewClauses = resolveAsAuthorsConstraint((RowEntry<RowAuthorName, ?>) clause,
                    transactor);
        } else {
            reviewClauses = resolveAsDataConstraint(table, clause, transactor);
        }

        return reviewClauses;
    }

    @NonNull
    private <Type> HashSet<RowEntry<RowReview, ?>> resolveAsAuthorsConstraint
            (RowEntry<RowAuthorName,
            Type> clause,
                                                                              TableTransactor
                                                                                      transactor) {
        RowAuthorName row = getUniqueRowWhere(getAuthorsTable(), clause, transactor);

        HashSet<RowEntry<RowReview, ?>> entries = new HashSet<>();
        if (row.hasData(mDataValidator)) {
            entries.add(asClause(RowReview.class, RowReview.AUTHOR_ID, row.getAuthorId().toString
                    ()));
        }

        return entries;
    }

    @NonNull
    private <DbRow extends DbTableRow, Type> HashSet<RowEntry<RowReview, ?>> resolveAsDataConstraint
            (DbTable<DbRow> table, RowEntry<DbRow, Type> clause, TableTransactor transactor) {
        HashSet<RowEntry<RowReview, ?>> entries = new HashSet<>();

        ColumnInfo<String> reviewIdCol;
        if (table.equals(getCommentsTable())) {
            reviewIdCol = RowComment.REVIEW_ID;
        } else if (table.equals(getFactsTable())) {
            reviewIdCol = RowFact.REVIEW_ID;
        } else if (table.equals(getImagesTable())) {
            reviewIdCol = RowImage.REVIEW_ID;
        } else if (table.equals(getLocationsTable())) {
            reviewIdCol = RowLocation.REVIEW_ID;
        } else if (table.equals(getTagsTable())) {
            reviewIdCol = RowTag.REVIEW_ID;
        } else {
            return entries;
        }

        for (DbRow row : getRowsWhere(table, clause, transactor)) {
            String reviewId = ((HasReviewId) row).getReviewId().toString();
            entries.add(asClause(RowReview.class, reviewIdCol, reviewId));
        }

        return entries;
    }

    @NonNull
    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column, T
                                                                              value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
