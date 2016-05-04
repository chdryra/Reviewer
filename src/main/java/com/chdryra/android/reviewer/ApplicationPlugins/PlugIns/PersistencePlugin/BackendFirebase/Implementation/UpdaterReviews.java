/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.FirebaseStructuring.DbUpdaterBasic;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UpdaterReviews extends DbUpdaterBasic<FbReview> {
    public final String mReviewsPath;
    public final String mReviewsDataPath;
    public final String mReviewsListPath;

    public UpdaterReviews(String reviewsPath, String reviewsDataPath, String reviewsListPath) {
        mReviewsPath = reviewsPath;
        mReviewsDataPath = reviewsDataPath;
        mReviewsListPath = reviewsListPath;
    }

    @NonNull
    @Override
    public Map<String, Object> getUpdatesMap(FbReview review, UpdateType updateType) {
        boolean update = updateType == UpdateType.INSERT_OR_UPDATE;

        Map<String, Object> listMap = null;
        Map<String, Object> reviewMap = null;

        if (update) {
            ListEntry entry = new ListEntry(review.getSubject(), review.getRating(), review
                    .getPublishDate());
            listMap = new ObjectMapper().convertValue(entry, Map.class);
            reviewMap = new ObjectMapper().convertValue(review, Map.class);
        }

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();
        updates.put(path(mReviewsPath, mReviewsDataPath, reviewId), reviewMap);
        updates.put(path(mReviewsPath, mReviewsListPath, reviewId), listMap);

        return updates;
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
