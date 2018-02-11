/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FbReviewsStructure extends FbReviewsUpdateable {
    String AGGREGATES = "Aggregates";
    String PLAYLISTS = "Playlists";
    String PLAYLIST_INDEX = "NamesIndex";

    FbAuthorsDb getAuthorsDb(AuthorId authorId);

    Firebase getListEntriesDb(Firebase root, AuthorId authorId);

    Firebase getListEntryDb(Firebase root, AuthorId authorId, ReviewId ReviewId);

    Firebase getAggregatesDb(Firebase root, AuthorId authorId, ReviewId ReviewId);

    Firebase getReviewDb(Firebase root, AuthorId authorId, ReviewId ReviewId);

    Firebase getCollectionDb(Firebase root, AuthorId authorId, String playlistName);

    Firebase getCollectionEntryDb(Firebase root, AuthorId authorId, String playlistName, ReviewId reviewId);

    DbUpdater<ReviewId> getCollectionUpdater(String name, AuthorId authorId);
}
