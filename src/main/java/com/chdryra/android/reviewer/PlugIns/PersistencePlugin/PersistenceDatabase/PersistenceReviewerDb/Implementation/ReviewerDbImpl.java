/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.RowTag;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbImpl implements ReviewerDb {
    private final ContractorDb<ReviewerDbContract> mContractor;
    private final ReviewerDbContract mTables;
    private final ReviewLoader mReviewLoader;
    private final FactoryReviewerDbTableRow mRowFactory;
    private final TagsManager mTagsManager;
    private final DataValidator mDataValidator;

    public ReviewerDbImpl(ContractorDb<ReviewerDbContract> contractor,
                          ReviewLoader reviewLoader,
                          FactoryReviewerDbTableRow rowFactory,
                          TagsManager tagsManager,
                          DataValidator dataValidator) {
        mContractor = contractor;
        mTables = contractor.getContract();
        mTagsManager = tagsManager;
        mReviewLoader = reviewLoader;
        mRowFactory = rowFactory;
        mDataValidator = dataValidator;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public TableTransactor beginWriteTransaction() {
        TableTransactor transactor = mContractor.getWriteableTransactor(mRowFactory);
        transactor.beginTransaction();
        return transactor;
    }

    @Override
    public TableTransactor beginReadTransaction() {
        TableTransactor transactor = mContractor.getReadableTransactor(mRowFactory);
        transactor.beginTransaction();
        return transactor;
    }

    @Override
    public void endTransaction(TableTransactor transactor) {
        transactor.endTransaction();
    }

    @Override
    public <DbRow extends DbTableRow, Type> ArrayList<Review>
    loadReviewsWhere(DbTable<DbRow> table, RowEntry<Type> clause, TableTransactor transactor) {

        ArrayList<Review> reviews = new ArrayList<>();
        ArrayList<ReviewId> reviewsLoaded = new ArrayList<>();

        for (RowEntry<?> reviewEntry : resolveReviewTableEntries(transactor, table, clause)) {

            for (RowReview reviewRow : getRowsWhere(getReviewsTable(), reviewEntry, transactor)) {
                if (reviewsLoaded.contains(reviewRow.getReviewId())) continue;
                Review review = loadReview(reviewRow, transactor);
                if (review != null) {
                    reviews.add(review);
                    reviewsLoaded.add(review.getReviewId());
                }
            }

        }

        return reviews;
    }

    @Override
    public <DbRow extends DbTableRow, Type> DbRow getUniqueRowWhere(DbTable<DbRow> table,
                                                                    RowEntry<Type> clause,
                                                                    TableTransactor transactor) {
        TableRowList<DbRow> rows = transactor.getRowsWhere(table, clause);
        if (rows.size() > 1) throw new IllegalStateException("More than one row found!");
        return rows.getItem(0);
    }

    @Override
    public <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                             RowEntry<Type> clause,
                                                                             TableTransactor
                                                                                         transactor) {
        return transactor.getRowsWhere(table, clause);
    }

    @Override
    public boolean addReviewToDb(Review review, TableTransactor transactor) {
        if (isReviewInDb(review, transactor)) return false;

        addToAuthorsTableIfNecessary(review, transactor);
        addToReviewsTable(review, transactor);
        addCriteriaToReviewsTable(review, transactor);
        addToCommentsTable(review, transactor);
        addToFactsTable(review, transactor);
        addToLocationsTable(review, transactor);
        addToImagesTable(review, transactor);
        addToTagsTable(review, transactor);

        return true;
    }

    @Override
    public boolean deleteReviewFromDb(ReviewId reviewId, TableTransactor transactor) {
        String idString = reviewId.toString();
        RowReview row = getUniqueRowWhere(getReviewsTable(), asEntry(RowReview.REVIEW_ID, idString), transactor
        );
        if (!row.hasData(mDataValidator)) return false;

        deleteFromTable(getImagesTable(), idString, transactor);
        deleteFromTable(getLocationsTable(), idString, transactor);
        deleteFromTable(getFactsTable(), idString, transactor);
        deleteFromTable(getCommentsTable(), idString, transactor);
        deleteCriteriaFromReviewsTable(idString, transactor);
        deleteFromTable(getReviewsTable(), idString, transactor);
        deleteFromAuthorsTableIfNecessary(transactor, row);
        deleteFromTagsTableIfNecessary(idString, transactor);

        return true;
    }

    @Override
    public String getColumnNameReviewId() {
        return mTables.getColumnNameReviewId();
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

    //Private methods
    @NonNull
    private <DbRow extends DbTableRow, Type> HashSet<RowEntry<?>>
    resolveReviewTableEntries(TableTransactor transactor, DbTable<DbRow> table, RowEntry<Type>
            entry) {
        HashSet<RowEntry<?>> reviewEntries;

        if (table.getName().equals(getReviewsTable().getName())) {
            reviewEntries = new HashSet<>();
            reviewEntries.add(entry);
        } else if (table.getName().equals(getAuthorsTable().getName())) {
            reviewEntries = resolveAsAuthorsConstraint(transactor, entry);
        } else if (table.getName().equals(getTagsTable().getName())) {
            reviewEntries = resolveAsTagsConstraint(transactor, entry);
        } else {
            reviewEntries = resolveAsDataConstraint(transactor, table, entry);
        }

        return reviewEntries;
    }

    @NonNull
    private <DbRow extends DbTableRow, Type> HashSet<RowEntry<?>> resolveAsDataConstraint
            (TableTransactor transactor, DbTable<DbRow> table, RowEntry<Type> entry) {
        HashSet<RowEntry<?>> entries = new HashSet<>();

        if (table.getName().equals(getCommentsTable().getName())) {
            for (RowComment row : getRowsWhere(getCommentsTable(), entry, transactor)) {
                entries.add(asEntry(RowComment.REVIEW_ID, row.getReviewId().toString()));
            }
        } else if (table.getName().equals(getFactsTable().getName())) {
            for (RowFact row : getRowsWhere(getFactsTable(), entry, transactor)) {
                entries.add(asEntry(RowFact.REVIEW_ID, row.getReviewId().toString()));
            }
        } else if (table.getName().equals(getImagesTable().getName())) {
            for (RowImage row : getRowsWhere(getImagesTable(), entry, transactor)) {
                entries.add(asEntry(RowImage.REVIEW_ID, row.getReviewId().toString()));
            }
        } else if (table.getName().equals(getLocationsTable().getName())) {
            for (RowLocation row : getRowsWhere(getLocationsTable(), entry, transactor)) {
                entries.add(asEntry(RowLocation.REVIEW_ID, row.getReviewId().toString()));
            }
        }

        return entries;
    }

    @NonNull
    private <Type> HashSet<RowEntry<?>> resolveAsTagsConstraint(TableTransactor transactor,
                                                                RowEntry<Type> entry) {
        HashSet<String> reviewIds = new HashSet<>();
        for (RowTag row : getRowsWhere(getTagsTable(), entry, transactor)) {
            reviewIds.addAll(row.getReviewIds());
        }

        HashSet<RowEntry<?>> entries = new HashSet<>();
        for (String id : reviewIds) {
            entries.add(asEntry(RowReview.REVIEW_ID, id));
        }

        return entries;
    }

    private <Type> HashSet<RowEntry<?>> resolveAsAuthorsConstraint(TableTransactor transactor,
                                                                   RowEntry<Type> entry) {
        RowAuthor row = getUniqueRowWhere(getAuthorsTable(), entry, transactor);

        HashSet<RowEntry<?>> entries = new HashSet<>();
        entries.add(asEntry(RowReview.USER_ID, row.getUserId().toString()));

        return entries;
    }

    private void deleteFromTagsTableIfNecessary(String reviewId, TableTransactor transactor) {
        ItemTagCollection tags = mTagsManager.getTags(reviewId);
        for (ItemTag tag : tags) {
            if (mTagsManager.untagItem(reviewId, tag)) {
                deleteFromTagsTable(tag.getTag(), transactor);
            }
        }
    }

    private void deleteFromAuthorsTableIfNecessary(TableTransactor transactor, RowReview row) {
        String userId = row.getAuthorId();
        TableRowList<RowReview> authored
                = transactor.getRowsWhere(getReviewsTable(), asEntry(RowReview.USER_ID, userId));
        if (authored.size() == 0) deleteFromAuthorsTable(userId, transactor);
    }

    private void addToAuthorsTableIfNecessary(Review review, TableTransactor transactor) {
        DataAuthor author = review.getAuthor();
        String userId = author.getUserId().toString();
        DbTable<RowAuthor> authorsTable = getAuthorsTable();
        if (!transactor.isIdInTable(userId, authorsTable.getColumn(RowAuthor.USER_ID.getName()),
                authorsTable)) {
            addToAuthorsTable(author, transactor);
        }
    }

    private void loadTags(TableTransactor transactor) {
        for (RowTag tag : transactor.loadTable(getTagsTable())) {
            ArrayList<String> reviews = tag.getReviewIds();
            for (String reviewId : reviews) {
                mTagsManager.tagItem(reviewId, tag.getTag());
            }
        }
    }

    @Nullable
    private Review loadReview(RowReview reviewRow, TableTransactor transactor) {
        if (!reviewRow.hasData(mDataValidator)) return null;
        Review review = mReviewLoader.loadReview(reviewRow, this, transactor);
        setTags(reviewRow.getReviewId().toString(), transactor);

        return review;
    }

    private void setTags(String id, TableTransactor transactor) {
        ItemTagCollection tags = mTagsManager.getTags(id);
        if (tags.size() == 0) loadTags(transactor);
    }

    private void addToAuthorsTable(DataAuthor author, TableTransactor transactor) {
        transactor.insertRow(mRowFactory.newRow(author), getAuthorsTable());
    }

    private void deleteFromAuthorsTable(String userId, TableTransactor transactor) {
        transactor.deleteRows(getAuthorsTable(), asEntry(RowAuthor.USER_ID, userId));
    }

    private void addToTagsTable(Review review, TableTransactor transactor) {
        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());
        for (ItemTag tag : tags) {
            transactor.insertOrReplaceRow(mRowFactory.newRow(tag), getTagsTable());
        }
    }

    private void deleteFromTagsTable(String tag, TableTransactor transactor) {
        transactor.deleteRows(getTagsTable(), asEntry(RowTag.TAG, tag));
    }

    private void addToReviewsTable(Review review, TableTransactor transactor) {
        transactor.insertRow(mRowFactory.newRow(review), getReviewsTable());
    }

    private void addCriteriaToReviewsTable(Review review, TableTransactor transactor) {
        for (DataCriterionReview criterion : review.getCriteria()) {
            transactor.insertRow(mRowFactory.newRow(criterion), getReviewsTable());
        }
    }

    private void deleteCriteriaFromReviewsTable(String reviewId, TableTransactor transactor) {
        TableRowList<RowReview> rows = transactor.getRowsWhere(getReviewsTable(),
                asEntry(RowReview.PARENT_ID, reviewId));
        for (RowReview row : rows) {
            deleteReviewFromDb(row.getReviewId(), transactor);
        }
    }

    @NonNull
    private <T> RowEntry<T> asEntry(ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(column, value);
    }

    private void deleteFromTable(DbTable table, String reviewId, TableTransactor transactor) {
        transactor.deleteRows(table, asEntry(RowReview.REVIEW_ID, reviewId));
    }

    private void addToCommentsTable(Review review, TableTransactor transactor) {
        int i = 1;
        for (DataComment datum : review.getComments()) {
            transactor.insertRow(mRowFactory.newRow(datum, i++), getCommentsTable());
        }
    }

    private void addToFactsTable(Review review, TableTransactor transactor) {
        int i = 1;
        for (DataFact datum : review.getFacts()) {
            transactor.insertRow(mRowFactory.newRow(datum, i++), getFactsTable());
        }
    }

    private void addToLocationsTable(Review review, TableTransactor transactor) {
        int i = 1;
        for (DataLocation datum : review.getLocations()) {
            transactor.insertRow(mRowFactory.newRow(datum, i++), getLocationsTable());
        }
    }

    private void addToImagesTable(Review review, TableTransactor transactor) {
        int i = 1;
        for (DataImage datum : review.getImages()) {
            transactor.insertRow(mRowFactory.newRow(datum, i++), getImagesTable());
        }
    }

    private boolean isReviewInDb(Review review, TableTransactor transactor) {
        DbColumnDefinition reviewIdCol = getReviewsTable().getColumn(getColumnNameReviewId());
        return transactor.isIdInTable(review.getReviewId().toString(), reviewIdCol,
                getReviewsTable());
    }
}
