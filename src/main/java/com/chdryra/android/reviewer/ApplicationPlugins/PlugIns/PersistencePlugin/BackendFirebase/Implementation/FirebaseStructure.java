/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

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

    public String getReviewsRoot(){
        return REVIEWS;
    }

    @NonNull
    public Map<String, Object> getUpdatesMap(FbReview review) {
        Map<String, Object> reviewMap = new ObjectMapper().convertValue(review, Map.class);
        Map<String, Object> rating = new ObjectMapper().convertValue(review.getRating(), Map.class);

        Map<String, Object> updates = new HashMap<>();
        String reviewId = review.getReviewId();
        updates.put(REVIEWS + "/" + reviewId, reviewMap);
        updates.put(REVIEWS_LIST + "/" + reviewId, rating);

        String user = USERS + "/" + review.getAuthor().getUserId();
        updates.put(user + "/" + PROFILE, review.getAuthor().getName());
        for(String tag : review.getTags()) {
            updates.put(TAGS + "/" + tag + "/" + reviewId, true);
            updates.put(user + "/" + TAGS + "/" + tag+ "/" + reviewId, true);
        }
        updates.put(user + "/" + REVIEWS + "/" + reviewId, true);
        updates.put(user + "/" + FEED + "/" + reviewId, true);

        return updates;
    }

}
