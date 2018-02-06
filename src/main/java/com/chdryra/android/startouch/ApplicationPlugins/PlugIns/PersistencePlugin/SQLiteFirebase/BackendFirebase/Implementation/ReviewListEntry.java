/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Rating;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;


import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReviewInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewListEntry {
    public static String SUBJECT = "subject";
    public static String RATING = "rating";
    public static String DATE = "publishDate";

    private String reviewId;
    private String subject;
    private Rating rating;
    private String authorId;
    private long publishDate;

    public ReviewListEntry() {

    }

    public ReviewListEntry(ReviewDb review) {
        this.reviewId = review.getReviewId();
        this.subject = review.getSubject();
        this.rating = review.getRating();
        this.authorId = review.getAuthorId();
        this.publishDate = review.getPublishDate();
    }

    public ReviewListEntry(DataReviewInfo review) {
        this.reviewId = review.getReviewId().toString();
        this.subject = review.getSubject().getSubject();
        this.rating = new Rating(review.getRating());
        this.authorId = review.getAuthorId().toString();
        this.publishDate = review.getPublishDate().getTime();
    }

    public ReviewListEntry(String reviewId, String subject, Rating rating, String authorId, long
            publishDate) {
        this.reviewId = reviewId;
        this.subject = subject;
        this.rating = rating;
        this.authorId = authorId;
        this.publishDate = publishDate;
    }

    public String getReviewId() {
        return reviewId;
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

    public ReviewListEntry toInverseDate() {
        return new ReviewListEntry(getReviewId(), getSubject(), getRating(), getAuthorId(), -getPublishDate());
    }
}
