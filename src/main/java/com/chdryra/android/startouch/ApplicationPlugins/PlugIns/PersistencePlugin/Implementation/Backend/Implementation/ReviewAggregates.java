/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewAggregates {
    public static final String CRITERIA = "criteria";
    public static final String COMMENTS = "comments";
    public static final String FACTS = "facts";
    public static final String IMAGES = "images";
    public static final String LOCATIONS = "locations";
    public static final String TAGS = "tags";

    private long criteria;
    private long comments;
    private long facts;
    private long images;
    private long locations;
    private long tags;

    public ReviewAggregates() {
    }

    public ReviewAggregates(ReviewDb review) {
        criteria = review.getCriteria().size();
        comments = review.getComments().size();
        facts = review.getFacts().size();
        images = review.getImages().size();
        locations = review.getLocations().size();
        tags = review.getTags().size();
    }

    public long getTags() {
        return tags;
    }

    public long getCriteria() {
        return criteria;
    }

    public long getComments() {
        return comments;
    }

    public long getFacts() {
        return facts;
    }

    public long getImages() {
        return images;
    }

    public long getLocations() {
        return locations;
    }

    public int size() {
        return 6;
    }
}
