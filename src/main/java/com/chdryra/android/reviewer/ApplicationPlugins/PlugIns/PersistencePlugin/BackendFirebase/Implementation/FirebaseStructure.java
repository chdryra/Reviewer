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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseStructure {
    private static final String REVIEWS = "Reviews";
    private static final String REVIEWS_LIST = "ReviewsList";
    private static final String TAGS = "Tags";
    private static final String USERS = "Users";
    private static final String FEED = "Feed";
    private static final String SUBJECT = FbReview.SUBJECT;
    private static final String RATING = FbReview.RATING;
    private static final String DATE = FbReview.PUBLISH_DATE;

    public String getReviewsRoot() {
        return REVIEWS;
    }

    public String getReviewsListRoot() {
        return REVIEWS_LIST;
    }

    @NonNull
    public Map<String, Object> getUpdatesMap(FbReview review) {
        return getUpdatesMap(review, false);
    }

    @NonNull
    public Map<String, Object> getDeleteMap(FbReview review) {
        return getUpdatesMap(review, true);
    }

    @NonNull
    private Map<String, Object> getUpdatesMap(FbReview review, boolean delete) {
        Map<String, Object> reviewMap = null;
        Map<String, Object> rating = null;
        Boolean trueValue = null;

        if (!delete) {
            reviewMap = new ObjectMapper().convertValue(review, Map.class);
            rating = new ObjectMapper().convertValue(review.getRating(), Map.class);
            trueValue = true;
        }

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();
        updates.put(path(REVIEWS, reviewId), reviewMap);
        updates.put(path(REVIEWS_LIST, reviewId, SUBJECT), review.getSubject());
        updates.put(path(REVIEWS_LIST, reviewId, RATING), rating);
        updates.put(path(REVIEWS_LIST, reviewId, DATE), review.getPublishDate());

        for (String tag : review.getTags()) {
            updates.put(path(TAGS, tag, reviewId), trueValue);
        }

        String user = path(USERS, review.getAuthor().getUserId());
        for (String tag : review.getTags()) {
            updates.put(path(user, TAGS, tag , reviewId), trueValue);
        }
        updates.put(path(user, REVIEWS, reviewId), trueValue);
        updates.put(path(user, FEED, reviewId), trueValue);

        return updates;
    }

    private String path(String root, String...elements) {
        String path = root;
        for(String element : elements) {
            path += "/" + element;
        }

        return path;
    }
}
