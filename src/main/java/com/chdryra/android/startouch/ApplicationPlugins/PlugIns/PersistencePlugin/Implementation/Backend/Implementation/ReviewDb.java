/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDb {
    public static final String SUBJECT = "subject";
    public static final String RATING = "rating";
    public static final String AUTHOR = "author";
    public static final String PUBLISH_DATE = "publishDate";
    public static final String COVER = "images/0";
    public static final String CRITERIA = "criteria";
    public static final String COMMENTS = "comments";
    public static final String FACTS = "facts";
    public static final String IMAGES = "images";
    public static final String LOCATIONS = "locations";
    public static final String TAGS = "tags";

    private String reviewId;
    private String authorId;
    private long publishDate;
    private String subject;
    private Rating rating;
    private List<Criterion> criteria;
    private List<Comment> comments;
    private List<Fact> facts;
    private List<ImageData> images;
    private List<Location> locations;
    private List<String> tags;

    public ReviewDb() {
    }

    public ReviewDb(Review review) {
        reviewId = review.getReviewId().toString();
        subject = review.getSubject().getSubject();
        rating = new Rating(review.getRating());
        authorId = review.getAuthorId().toString();
        publishDate = review.getPublishDate().getTime();

        criteria = new ArrayList<>();
        for (DataCriterion criterion : review.getCriteria()) {
            criteria.add(new Criterion(criterion));
        }

        comments = new ArrayList<>();
        for (DataComment comment : review.getComments()) {
            comments.add(new Comment(comment));
        }

        facts = new ArrayList<>();
        for (DataFact fact : review.getFacts()) {
            facts.add(new Fact(fact));
        }

        images = new ArrayList<>();
        for (DataImage image : review.getImages()) {
            ImageData data = new ImageData(image);
            images.add(data);
        }

        locations = new ArrayList<>();
        for (DataLocation location : review.getLocations()) {
            locations.add(new Location(location));
        }

        tags = new ArrayList<>();
        for (DataTag tag : review.getTags()) {
            tags.add(tag.getTag());
        }
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getSubject() {
        return subject;
    }

    public Rating getRating() {
        return rating;
    }

    public String getAuthorId() {
        return authorId;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Criterion> getCriteria() {
        return criteria != null ? criteria : new ArrayList<Criterion>();
    }

    public List<Comment> getComments() {
        return comments != null ? comments : new ArrayList<Comment>();
    }

    public List<Fact> getFacts() {
        return facts != null ? facts : new ArrayList<Fact>();
    }

    public List<ImageData> getImages() {
        return images != null ? images : new ArrayList<ImageData>();
    }

    public List<Location> getLocations() {
        return locations != null ? locations : new ArrayList<Location>();
    }

    public int size() {
        return 4 + Rating.size() + 6;
    }
}
