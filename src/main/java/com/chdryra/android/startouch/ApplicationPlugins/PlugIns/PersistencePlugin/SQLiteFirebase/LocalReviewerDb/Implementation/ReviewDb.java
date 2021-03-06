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
        .RelationalDb.Api.TableTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewerDbReadable;
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
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewStatic;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDb extends ReviewStatic {
    private static final RowEntry<RowImage, Boolean> COVER_CLAUSE
            = new RowEntryImpl<>(RowImage.class, RowImage.IS_COVER, true);

    private final RowReview mRow;
    private final ReviewerDbReadable mDb;

    public ReviewDb(RowReview row, ReviewerDbReadable db) {
        mRow = row;
        mDb = db;
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
    public DataAuthorId getAuthorId() {
        return new DatumAuthorId(mRow.getReviewId(), mRow.getAuthorId());
    }

    @Override
    public DataDate getPublishDate() {
        return mRow;
    }

    @Override
    public IdableList<? extends DataTag> getTags() {
        return getData(mDb.getTagsTable(), RowTag.REVIEW_ID);
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return getData(mDb.getCriteriaTable(), RowCriterion.REVIEW_ID);
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return getData(mDb.getCommentsTable(), RowComment.REVIEW_ID);
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return getData(mDb.getFactsTable(), RowFact.REVIEW_ID);
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return getData(mDb.getImagesTable(), RowImage.REVIEW_ID);
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return getData(mDb.getLocationsTable(), RowLocation.REVIEW_ID);
    }

    @Override
    public DataImage getCover() {
        IdableRowList<RowImage> images = new IdableRowList<>(getReviewId(), loadDataWhere(mDb
                .getImagesTable(), COVER_CLAUSE));

        return images.size() == 0 ? new DatumImage(getReviewId()) : images.get(0);
    }

    @NonNull
    private <T extends DbTableRow & ReviewDataRow> IdableList<T> getData(DbTable<T> table,
                                                                         ColumnInfo<String>
                                                                                 reviewIdCol) {
        return new IdableRowList<>(getReviewId(), loadReviewIdRows(table, reviewIdCol));
    }

    private <DbRow extends DbTableRow> ArrayList<DbRow> loadReviewIdRows(DbTable<DbRow> table,
                                                                         ColumnInfo<String> idCol) {
        return loadDataWhere(table, asClause(table.getRowClass(), idCol, getReviewId().toString()));
    }

    @NonNull
    private <DbRow extends DbTableRow, Type> ArrayList<DbRow> loadDataWhere(DbTable<DbRow> table,
                                                                            RowEntry<DbRow, Type>
                                                                                    clause) {
        ArrayList<DbRow> data = new ArrayList<>();

        TableTransactor transactor = mDb.beginReadTransaction();
        data.addAll(mDb.getRowsWhere(table, clause, transactor));
        mDb.endTransaction(transactor);

        return data;
    }

    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                                      ColumnInfo<T> column,
                                                                      T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}
