/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsDb implements FbAuthorsReviews {
    private Author mAuthor;
    private FbReviews mParent;

    public FbAuthorsDb(Author author, FbReviews parent) {
        mAuthor = author;
        mParent = parent;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public Firebase getListEntriesDb(Firebase root) {
        return mParent.getListEntriesDb(root, mAuthor);
    }

    @Override
    public Firebase getListEntryDb(Firebase root, String reviewId) {
        return mParent.getListEntryDb(root, mAuthor, reviewId);
    }

    @Override
    public Firebase getReviewDb(Firebase root, String reviewId) {
        return mParent.getReviewDb(root, mAuthor, reviewId);
    }

    @Override
    public Firebase getAggregatesDb(Firebase root) {
        return mParent.getAggregatesDb(root, mAuthor);
    }

    @Override
    public Firebase getAggregatesDb(Firebase root, String reviewId) {
        return mParent.getAggregatesDb(root, mAuthor, reviewId);
    }

    @Override
    public DbUpdater<ReviewDb> getReviewUploadUpdater() {
        return mParent.getReviewUploadUpdater();
    }
}
