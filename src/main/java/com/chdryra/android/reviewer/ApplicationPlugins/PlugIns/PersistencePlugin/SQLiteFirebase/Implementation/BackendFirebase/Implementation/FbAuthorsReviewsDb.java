/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbAuthorsReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsReviewsDb implements FbAuthorsReviews {
    private AuthorId mAuthorId;
    private FbReviews mParent;

    public FbAuthorsReviewsDb(AuthorId authorId, FbReviews parent) {
        mAuthorId = authorId;
        mParent = parent;
    }

    @Override
    public Firebase getListEntriesDb(Firebase root) {
        return mParent.getListEntriesDb(root, mAuthorId);
    }

    @Override
    public Firebase getListEntryDb(Firebase root, ReviewId reviewId) {
        return mParent.getListEntryDb(root, mAuthorId, reviewId);
    }

    @Override
    public Firebase getReviewDb(Firebase root, ReviewId reviewId) {
        return mParent.getReviewDb(root, mAuthorId, reviewId);
    }

    @Override
    public Firebase getAggregatesDb(Firebase root, ReviewId reviewId) {
        return mParent.getAggregatesDb(root, mAuthorId, reviewId);
    }

    @Override
    public DbUpdater<ReviewDb> getReviewUploadUpdater() {
        return mParent.getReviewUploadUpdater();
    }
}
