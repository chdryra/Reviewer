/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 1 April, 2015
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

import java.util.ArrayList;

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
    public ArrayList<Review> loadReviewsWhere(TableTransactor transactor,
                                              String col,
                                              @Nullable String val) {
        ArrayList<Review> reviews = new ArrayList<>();
        for (RowReview reviewRow : loadFromTableWhere(getReviewsTable(), col, val, transactor)) {
            reviews.add(loadReview(reviewRow, transactor));
        }

        return reviews;
    }

    @Override
    public <T extends DbTableRow> T getUniqueRowWhere(TableTransactor transactor,
                                                      DbTable<T> table,
                                                      String col,
                                                      String val) {
        TableRowList<T> rows = transactor.getRowsWhere(table, col, val);
        if(rows.size() > 1) throw new IllegalStateException("More than one row found!");
        return rows.getItem(0);
    }

    @Override
    public <T extends DbTableRow> ArrayList<T> getRowsWhere(TableTransactor transactor,
                                                            DbTable<T> table,
                                                            String col,
                                                            String val) {
        ArrayList<T> rowArray = new ArrayList<>();
        rowArray.addAll(transactor.getRowsWhere(table, col, val));

        return rowArray;
    }

    @Override
    public <T extends DbTableRow> ArrayList<T> loadFromDataTable(TableTransactor transactor,
                                                                 DbTable<T> table,
                                                                 String reviewId) {
        return loadFromTableWhere(table, getColumnNameReviewId(), reviewId, transactor);
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
    public boolean deleteReviewFromDb(String reviewId, TableTransactor transactor) {
        RowReview row = getUniqueRowWhere(transactor, getReviewsTable(), getColumnNameReviewId(),
                reviewId);
        if (!row.hasData(mDataValidator)) return false;

        deleteFromTable(getImagesTable(), reviewId, transactor);
        deleteFromTable(getLocationsTable(), reviewId, transactor);
        deleteFromTable(getFactsTable(), reviewId, transactor);
        deleteFromTable(getCommentsTable(), reviewId, transactor);
        deleteCriteriaFromReviewsTable(reviewId, transactor);
        deleteFromTable(getReviewsTable(), reviewId, transactor);
        deleteFromAuthorsTableIfNecessary(transactor, row);
        deleteFromTagsTableIfNecessary(reviewId, transactor);

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
                = transactor.getRowsWhere(getReviewsTable(), RowReview.USER_ID.getName(), userId);
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

    @NonNull
    private <T extends DbTableRow> ArrayList<T> loadFromTableWhere(DbTable<T> table, String col,
                                                                   @Nullable String val,
                                                                   TableTransactor transactor) {
        ArrayList<T> results = new ArrayList<>();
        for (T row : transactor.getRowsWhere(table, col, val)) {
            results.add(row);
        }

        return results;
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
        transactor.deleteRows(getAuthorsTable(), RowAuthor.USER_ID.getName(), userId);
    }

    private void addToTagsTable(Review review, TableTransactor transactor) {
        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());
        for (ItemTag tag : tags) {
            transactor.insertOrReplaceRow(mRowFactory.newRow(tag), getTagsTable());
        }
    }

    private void deleteFromTagsTable(String tag, TableTransactor transactor) {
        transactor.deleteRows(getTagsTable(), RowTag.TAG.getName(), tag);
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
                RowReview.PARENT_ID.getName(), reviewId);
        for (RowReview row : rows) {
            deleteReviewFromDb(row.getRowId(), transactor);
        }
    }

    private void deleteFromTable(DbTable table, String reviewId, TableTransactor transactor) {
        transactor.deleteRows(table, getColumnNameReviewId(), reviewId);
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
