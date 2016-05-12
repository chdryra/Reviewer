/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.HierarchyStructuring.DbStructureBasic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Interfaces.StructureReviews;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StructureReviewsImpl extends DbStructureBasic<ReviewDb> implements StructureReviews {
    public final String mReviewsDataPath;
    public final String mReviewsListPath;

    public StructureReviewsImpl(String reviewsDataPath, String reviewsListPath) {
        mReviewsDataPath = reviewsDataPath;
        mReviewsListPath = reviewsListPath;
    }

    @Override
    public String relativePathToReviewData() {
        return mReviewsDataPath;
    }

    @Override
    public String relativePathToReviewsList() {
        return mReviewsListPath;
    }

    @Override
    public String relativePathToReview(String reviewId) {
        return path(mReviewsDataPath, reviewId);
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(ReviewDb review, UpdateType updateType) {
        String reviewId = review.getReviewId();
        ListEntry entry
                = new ListEntry(review.getSubject(), review.getRating(), review.getPublishDate());

        Updates updates = new Updates(updateType);
        updates.atPath(review, relativePathToReviewData(), reviewId).putObject(review);
        updates.atPath(review, relativePathToReviewsList(), reviewId).putObject(entry);

        return updates.toMap();
    }

    private class ListEntry {
        private String subject;
        private Rating rating;
        private long date;

        public ListEntry(String subject, Rating rating, long date) {
            this.subject = subject;
            this.rating = rating;
            this.date = date;
        }

        public String getSubject() {
            return subject;
        }

        public Rating getRating() {
            return rating;
        }

        public long getDate() {
            return date;
        }
    }
}
