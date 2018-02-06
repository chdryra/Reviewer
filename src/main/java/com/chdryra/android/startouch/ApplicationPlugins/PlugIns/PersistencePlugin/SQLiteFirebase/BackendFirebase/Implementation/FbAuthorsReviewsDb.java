/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ReviewDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbAuthorsDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthorsReviewsDb implements FbAuthorsDb {
    private final AuthorId mAuthorId;
    private final FbReviewsStructure mParent;

    public FbAuthorsReviewsDb(AuthorId authorId, FbReviewsStructure parent) {
        mAuthorId = authorId;
        mParent = parent;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
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
    public DbUpdater<ReviewDb> getReviewUpdater() {
        return mParent.getReviewUpdater();
    }
}
