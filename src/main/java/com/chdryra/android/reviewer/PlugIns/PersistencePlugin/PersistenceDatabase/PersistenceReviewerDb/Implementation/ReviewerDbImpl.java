/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb
        .Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;

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
    private final TagsManager mTagsManager;
    private final DataValidator mDataValidator;
    private final ReviewTransactor mReviewTransactor;
    private final FactoryDbTableRow mRowFactory;

    public ReviewerDbImpl(ContractorDb<ReviewerDbContract> contractor,
                          ReviewTransactor reviewTransactor,
                          FactoryDbTableRow rowFactory,
                          TagsManager tagsManager,
                          DataValidator dataValidator) {
        mContractor = contractor;
        mTables = contractor.getContract();
        mTagsManager = tagsManager;
        mReviewTransactor = reviewTransactor;
        mRowFactory = rowFactory;
        mDataValidator = dataValidator;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
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
    loadReviewsWhere(DbTable<DbRow> table, RowEntry<DbRow, Type> clause, TableTransactor transactor) {
        ArrayList<Review> reviews = new ArrayList<>();
        ArrayList<ReviewId> reviewsLoaded = new ArrayList<>();

        for (RowEntry<RowReview, ?> reviewClause : findReviewTableClauses(table, clause, transactor)) {
            for (RowReview reviewRow : getRowsWhere(getReviewsTable(), reviewClause, transactor)) {
                loadReviewIfNotLoaded(reviewRow, reviews, reviewsLoaded, transactor);
            }
        }

        return reviews;
    }

    @Override
    public <DbRow extends DbTableRow, Type> DbRow getUniqueRowWhere(DbTable<DbRow> table,
                                                                    RowEntry<DbRow, Type> clause,
                                                                    TableTransactor transactor) {
        TableRowList<DbRow> rows = transactor.getRowsWhere(table, clause, mRowFactory);
        if (rows.size() > 1) throw new IllegalStateException("More than one row found!");

        return rows.size() == 0 ? mRowFactory.emptyRow(table.getRowClass()) : rows.getItem(0);
    }

    @Override
    public <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                             RowEntry<DbRow, Type> clause,
                                                                             TableTransactor
                                                                                         transactor) {
        return transactor.getRowsWhere(table, clause, mRowFactory);
    }

    @Override
    public boolean addReviewToDb(Review review, TableTransactor transactor) {
        if (isReviewInDb(review, transactor)) return false;

        mReviewTransactor.addReviewToDb(review, this, transactor);

        return true;
    }

    @Override
    public boolean deleteReviewFromDb(ReviewId reviewId, TableTransactor transactor) {
        RowEntry<RowReview, String> clause = asClause(RowReview.class, RowReview.REVIEW_ID, reviewId.toString());
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
    public DbTable<RowAuthor> getAuthorsTable() {
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

    private void loadReviewIfNotLoaded(RowReview reviewRow, ArrayList<Review> reviews,
                                       ArrayList<ReviewId> reviewsLoaded,
                                       TableTransactor transactor) {
        if (reviewsLoaded.contains(reviewRow.getReviewId())) return;
        Review review = loadReview(reviewRow, transactor);
        if (review != null) {
            reviews.add(review);
            reviewsLoaded.add(review.getReviewId());
        }
    }

    @NonNull
    //TODO make type safe
    private <DbRow extends DbTableRow, Type> HashSet<RowEntry<RowReview, ?>>
    findReviewTableClauses(DbTable<DbRow> table, RowEntry<DbRow, Type> clause,
                           TableTransactor transactor) {
        HashSet<RowEntry<RowReview, ?>> reviewClauses;

        if (table.equals(getReviewsTable())) {
            reviewClauses = new HashSet<>();
            reviewClauses.add((RowEntry<RowReview, ?>) clause);
        } else if (table.equals(getAuthorsTable())) {
            reviewClauses = resolveAsAuthorsConstraint((RowEntry<RowAuthor, ?>) clause, transactor);
        } else if (table.equals(getTagsTable())) {
            reviewClauses = resolveAsTagsConstraint((RowEntry<RowTag, ?>) clause, transactor);
        } else {
            reviewClauses = resolveAsDataConstraint(table, clause, transactor);
        }

        return reviewClauses;
    }

    @NonNull
    private <Type> HashSet<RowEntry<RowReview, ?>> resolveAsAuthorsConstraint(RowEntry<RowAuthor, Type> clause,
                                                                              TableTransactor transactor) {
        RowAuthor row = getUniqueRowWhere(getAuthorsTable(), clause, transactor);

        HashSet<RowEntry<RowReview, ?>> entries = new HashSet<>();
        if (row.hasData(mDataValidator)) {
            entries.add(asClause(RowReview.class, RowReview.USER_ID, row.getUserId().toString()));
        }

        return entries;
    }

    @NonNull
    private <Type> HashSet<RowEntry<RowReview, ?>> resolveAsTagsConstraint(RowEntry<RowTag, Type> clause,
                                                                           TableTransactor transactor) {
        HashSet<String> reviewIds = new HashSet<>();
        for (RowTag row : getRowsWhere(getTagsTable(), clause, transactor)) {
            reviewIds.addAll(row.getReviewIds());
        }

        HashSet<RowEntry<RowReview, ?>> entries = new HashSet<>();
        for (String id : reviewIds) {
            entries.add(asClause(RowReview.class, RowReview.REVIEW_ID, id));
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
        } else {
            return entries;
        }

        for (DbRow row : getRowsWhere(table, clause, transactor)) {
            String reviewId = ((HasReviewId) row).getReviewId().toString();
            entries.add(asClause(RowReview.class, reviewIdCol, reviewId));
        }

        return entries;
    }

    @Nullable
    private Review loadReview(RowReview reviewRow, TableTransactor transactor) {
        if (!reviewRow.hasData(mDataValidator)) return null;

        Review review = mReviewTransactor.loadReview(reviewRow, this, transactor);
        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());
        if (tags.size() == 0) loadTags(transactor);

        return review;
    }

    private void loadTags(TableTransactor transactor) {
        for (RowTag tag : transactor.loadTable(getTagsTable(), mRowFactory)) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                mTagsManager.tagItem(reviewId, tag.getTag());
            }
        }
    }

    private boolean isReviewInDb(Review review, TableTransactor transactor) {
        DbColumnDefinition reviewIdCol = getReviewsTable().getColumn(RowReview.REVIEW_ID.getName());
        String reviewId = review.getReviewId().toString();

        return transactor.isIdInTable(reviewId, reviewIdCol, getReviewsTable());
    }

    @NonNull
    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
