/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation;



import com.chdryra.android.mygenerallibrary.CacheUtils.QueueCache;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.RelationalDbPlugin.Api.TableTransactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbCache implements QueueCache.Cache<Review> {
    private final ReviewerDb mDatabase;
    private final TagsManager mTagsManager;
    private final DbTable<RowReview> mTable;
    private boolean mTagsLoaded = false;

    public ReviewerDbCache(ReviewerDb database, TagsManager tagsManager) {
        mDatabase = database;
        mTagsManager = tagsManager;
        mTable = mDatabase.getReviewsTable();
    }

    @Override
    public void put(String id, Review review) {
        if (!id.equals(review.getReviewId().toString())) {
            throw new IllegalArgumentException("Id must be ReviewId");
        }

        TableTransactor db = mDatabase.beginWriteTransaction();
        mDatabase.addReviewToDb(review, mTagsManager, db);
        mDatabase.endTransaction(db);
    }

    @Override
    public Review get(String id) {
        TableTransactor transactor = mDatabase.beginReadTransaction();
        Review review = getReview(id, transactor);
        mDatabase.endTransaction(transactor);

        return review;
    }

    @Override
    public Review remove(String id) {
        TableTransactor transactor = mDatabase.beginWriteTransaction();
        Review review = getReview(id, transactor);
        mDatabase.deleteReviewFromDb(new DatumReviewId(id), mTagsManager, transactor);
        mDatabase.endTransaction(transactor);

        return review;
    }

    private Review getReview(String id, TableTransactor transactor) {
        RowEntry<RowReview, String> clause
                = new RowEntryImpl<>(RowReview.class, RowReview.REVIEW_ID, id);

        Collection<Review> reviews = mDatabase.loadReviewsWhere(mTable, clause, transactor);
        loadTagsIfNecessary(transactor);

        Iterator<Review> iterator = reviews.iterator();

        Review review;
        if (iterator.hasNext()) {
            review = iterator.next();
        } else {
            throw new IllegalArgumentException("Id not found: " + id);
        }

        return review;
    }

    private void loadTagsIfNecessary(TableTransactor transactor) {
        if (mTagsLoaded) return;
        TableRowList<RowTag> rows = mDatabase.loadTable(mDatabase.getTagsTable(), transactor);
        for (RowTag row : rows) {
            ArrayList<String> reviewIds = row.getReviewIds();
            for (String reviewId : reviewIds) {
                if (!mTagsManager.tagsItem(reviewId, row.getTag())) {
                    mTagsManager.tagItem(reviewId, row.getTag());
                }
            }
        }

        mTagsLoaded = true;
    }
}
