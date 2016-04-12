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
    private static final String PROFILE = "Profile";

    public String getReviewsRoot() {
        return REVIEWS;
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
        updates.put(REVIEWS + "/" + reviewId, reviewMap);
        updates.put(REVIEWS_LIST + "/" + reviewId, rating);

        for (String tag : review.getTags()) {
            updates.put(TAGS + "/" + tag + "/" + reviewId, trueValue);
        }

        String user = USERS + "/" + review.getAuthor().getUserId();
        for (String tag : review.getTags()) {
            updates.put(user + "/" + TAGS + "/" + tag + "/" + reviewId, trueValue);
        }
        updates.put(user + "/" + REVIEWS + "/" + reviewId, trueValue);
        updates.put(user + "/" + FEED + "/" + reviewId, trueValue);

        return updates;
    }
}
