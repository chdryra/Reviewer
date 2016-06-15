/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbReadable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowCriterion;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbReference implements ReviewReference {
    private ReviewInfo mInfo;
    private ReviewerDbRepository mRepo;
    private ArrayList<ValueObserver<?>> mObservers;

    public ReviewerDbReference(ReviewInfo info, ReviewerDbRepository repo) {
        mInfo = info;
        mRepo = repo;
        mObservers = new ArrayList<>();
    }

    @Override
    public ReviewInfo getInfo() {
        return mInfo;
    }

    @Override
    public void registerObserver(SubjectObserver observer) {
        registerIfNecessary(observer);
        observer.onValue(mInfo.getSubject());
    }

    @Override
    public void registerObserver(RatingObserver observer) {
        registerIfNecessary(observer);
        observer.onValue(mInfo.getRating());
    }

    @Override
    public void registerObserver(AuthorObserver observer) {
        registerIfNecessary(observer);
        observer.onValue(mInfo.getAuthor());
    }

    @Override
    public void registerObserver(DateObserver observer) {
        registerIfNecessary(observer);
        observer.onValue(mInfo.getPublishDate());
    }

    @Override
    public void registerObserver(CoverObserver observer) {
        registerIfNecessary(observer);
        IdableList<RowImage> images = getData(getDb().getImagesTable(), RowImage.REVIEW_ID);
        for(RowImage image : images) {
            if(image.isCover()) {
                observer.onValue(image);
                break;
            }
        }
    }

    @Override
    public void registerObserver(CriteriaObserver
                                                 observer) {
        registerIfNecessary(observer);
        observer.onValue(getData(getDb().getCriteriaTable(), RowCriterion.REVIEW_ID));
    }

    @Override
    public void registerObserver(CommentsObserver
                                                     observer) {
        registerIfNecessary(observer);
        observer.onValue(getData(getDb().getCommentsTable(), RowComment.REVIEW_ID));
    }

    @Override
    public void registerObserver(FactsObserver observer) {
        registerIfNecessary(observer);
        observer.onValue(getData(getDb().getFactsTable(), RowFact.REVIEW_ID));
    }

    @Override
    public void registerObserver(ImagesObserver observer) {
        registerIfNecessary(observer);
        observer.onValue(getData(getDb().getImagesTable(), RowImage.REVIEW_ID));
    }

    @Override
    public void registerObserver(LocationsObserver
                                                  observer) {
        registerIfNecessary(observer);
        observer.onValue(getData(getDb().getLocationsTable(), RowLocation.REVIEW_ID));
    }

    @Override
    public void registerObserver(TagsObserver observer) {
        registerIfNecessary(observer);
        ItemTagCollection tags = mRepo.getTagsManager().getTags(mInfo.getReviewId().toString());
        IdableList<DataTag> dataTags = new IdableDataList<>(mInfo.getReviewId());
        for(ItemTag tag : tags) {
            dataTags.add(new DatumTag(mInfo.getReviewId(), tag.getTag()));
        }

        observer.onValue(dataTags);
    }

    @Override
    public void unregisterObserver(SubjectObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(RatingObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(AuthorObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(DateObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(CoverObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(CriteriaObserver
                                                       observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(CommentsObserver
                                                   observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(FactsObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(ImagesObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(LocationsObserver
                                                        observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void unregisterObserver(TagsObserver observer) {
        unregisterIfNecessary(observer);
    }

    @Override
    public void dereference(final DereferenceCallback callback) {
        mRepo.getReview(mInfo.getReviewId(), new ReviewsRepository.RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                callback.onDereferenced(result.getReview(), result.getMessage());
            }
        });
    }

    @Override
    public boolean isValid() {
        return mInfo.isValid();
    }

    private ReviewerDbReadable getDb() {
        return mRepo.getReadableDatabase();
    }

    private <T> void registerIfNecessary(ValueObserver<T> observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    private <T> void unregisterIfNecessary(ValueObserver<T> observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @NonNull
    private <T extends DbTableRow & ReviewDataRow> IdableList<T> getData(DbTable<T> table,
                                                                         ColumnInfo<String> idCol) {
        RowEntry<T, String> clause = new RowEntryImpl<>(table.getRowClass(), idCol, mInfo
                .getReviewId().toString());

        ArrayList<T> data = new ArrayList<>();
        ReviewerDbReadable db = getDb();
        TableTransactor transactor = db.beginReadTransaction();
        data.addAll(db.getRowsWhere(table, clause, transactor));
        db.endTransaction(transactor);

        return new IdableRowList<>(mInfo.getReviewId(), data);
    }
}
