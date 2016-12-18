/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
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

    FbAuthorsReviews getAuthorsDb(AuthorId authorId);

    Firebase getListEntriesDb(Firebase root, AuthorId authorId);

    Firebase getListEntryDb(Firebase root, AuthorId authorId, ReviewId ReviewId);

    Firebase getAggregatesDb(Firebase root, AuthorId authorId, ReviewId ReviewId);

    Firebase getReviewDb(Firebase root, AuthorId authorId, ReviewId ReviewId);

    Firebase getPlaylistDb(Firebase root, AuthorId authorId, String playlistName);

    DbUpdater<ReviewListEntry> getPlaylistUpdater(String name, AuthorId authorId);
}
