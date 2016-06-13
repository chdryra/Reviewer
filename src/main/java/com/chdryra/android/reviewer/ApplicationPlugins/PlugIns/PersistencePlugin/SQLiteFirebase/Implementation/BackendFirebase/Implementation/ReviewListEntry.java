/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Rating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;

/**
 * Created by: Rizwan Choudrey
 * On: 17/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewListEntry {
    private String reviewId;
    private String subject;
    private Rating rating;
    private Author author;
    private long publishDate;

    public ReviewListEntry() {

    }

    public ReviewListEntry(ReviewDb review) {
        this.reviewId = review.getReviewId();
        this.subject = review.getSubject();
        this.rating = review.getRating();
        this.author = review.getAuthor();
        this.publishDate = review.getPublishDate();
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

    public Author getAuthor() {
        return author;
    }

    public long getPublishDate() {
        return publishDate;
    }
}
