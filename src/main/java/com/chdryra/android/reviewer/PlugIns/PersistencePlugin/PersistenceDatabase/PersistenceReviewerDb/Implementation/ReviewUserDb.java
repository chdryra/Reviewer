/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb
        .Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbReadable;
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

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserDb implements Review {
    public static final RowEntry<Boolean> COVER_CLAUSE
            = new RowEntryImpl<>(RowImage.IS_COVER, true);

    private RowReview mRow;
    private ReviewerDbReadable mDb;
    private ReviewNode mNode;
    private DataAuthorReview mAuthor;

    public ReviewUserDb(RowReview row,
                        ReviewerDbReadable db,
                        FactoryReviewNode nodeFactory) {
        mRow = row;
        mDb = db;
        mNode = nodeFactory.createReviewNode(this, false);
    }

    @Override
    public ReviewId getReviewId() {
        return mRow.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mRow;
    }

    @Override
    public DataRating getRating() {
        return mRow;
    }

    @Override
    public DataAuthorReview getAuthor() {
        if (mAuthor == null) loadAuthor();
        return mAuthor;
    }

    @Override
    public DataDateReview getPublishDate() {
        return mRow;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return mNode;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mRow.isRatingIsAverage();
    }

    @Override
    public IdableList<? extends DataCriterionReview> getCriteria() {
        IdableList<DataCriterionReview> criteria = new IdableDataList<>(getReviewId());
        for (Review criterion : loadCriteria()) {
            criteria.add(new DatumCriterionReview(getReviewId(), criterion));
        }

        return criteria;
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDb.getCommentsTable(),
                RowComment.REVIEW_ID));
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDb.getFactsTable(), RowFact
                .REVIEW_ID));
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDb.getImagesTable(),
                RowImage.REVIEW_ID));
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return new IdableRowList<>(getReviewId(), loadFromDataTable(mDb.getLocationsTable(),
                RowLocation.REVIEW_ID));
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return new IdableRowList<>(getReviewId(), loadDataWhere(mDb.getImagesTable(),
                COVER_CLAUSE));
    }

    private void loadAuthor() {
        TableTransactor transactor = mDb.beginReadTransaction();
        RowEntry<String> clause = asClause(RowAuthor.USER_ID, mRow.getAuthorId());
        RowAuthor row = mDb.getUniqueRowWhere(mDb.getAuthorsTable(), clause, transactor);
        mDb.endTransaction(transactor);
        mAuthor = new DatumAuthorReview(getReviewId(), row.getName(), row.getUserId());
    }

    private <T extends ReviewDataRow> ArrayList<T> loadFromDataTable(DbTable<T> table,
                                                                     ColumnInfo<String> idCol) {
        return loadDataWhere(table, asClause(idCol, getReviewId().toString()));
    }

    @NonNull
    private <T extends ReviewDataRow, Type> ArrayList<T> loadDataWhere(DbTable<T> table,
                                                                       RowEntry<Type> clause) {
        ArrayList<T> data = new ArrayList<>();

        TableTransactor db = mDb.beginReadTransaction();
        data.addAll(mDb.getRowsWhere(table, clause, db));
        mDb.endTransaction(db);

        return data;
    }

    private Iterable<Review> loadCriteria() {
        TableTransactor transactor = mDb.beginReadTransaction();
        Iterable<Review> criteria = mDb.loadReviewsWhere(mDb.getReviewsTable(),
                asClause(RowReview.PARENT_ID, getReviewId().toString()), transactor);
        mDb.endTransaction(transactor);
        return criteria;
    }

    private <T> RowEntry<T> asClause(ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(column, value);
    }
}
