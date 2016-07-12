/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Author;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FbReviews extends FbReviewsStructure{
    FbAuthorsReviews getAuthorsDb(Author author);


    Firebase getListEntriesDb(Firebase root, Author author);


    Firebase getListEntryDb(Firebase root, Author author, String reviewId);

    Firebase getAggregatesDb(Firebase root, Author author);

    Firebase getAggregatesDb(Firebase root, Author author, String reviewId);

    Firebase getReviewDb(Firebase root, Author author, String reviewId);
}
