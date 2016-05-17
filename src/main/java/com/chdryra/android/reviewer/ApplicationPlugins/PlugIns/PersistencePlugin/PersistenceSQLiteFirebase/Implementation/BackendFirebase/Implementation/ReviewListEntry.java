/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;


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
    private String subject;
    private Rating rating;
    private long date;

    public ReviewListEntry(ReviewDb review) {
        this.subject = review.getSubject();
        this.rating = review.getRating();
        this.date = review.getPublishDate();
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
