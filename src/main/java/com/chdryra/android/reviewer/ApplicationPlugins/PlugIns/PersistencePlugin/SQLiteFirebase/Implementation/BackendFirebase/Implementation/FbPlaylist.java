/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendInfoConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReviewReference;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbReviews;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbReviewsStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.Playlist;
import com.chdryra.android.reviewer.Persistence.Interfaces.PlaylistCallback;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FbPlaylist extends FbReferencesRepositoryBasic implements Playlist {
    private final String mName;
    private final AuthorId mAuthorId;
    private final FbReviewsStructure mStructure;
    private final BackendInfoConverter mConverter;

    public FbPlaylist(Firebase dataBase,
                      FbReviewsStructure structure,
                      SnapshotConverter<ReviewListEntry> entryConverter,
                      FactoryFbReviewReference referencer,
                      String name,
                      AuthorId authorId,
                      BackendInfoConverter converter) {
        super(dataBase, new PlaylistStructure(name, authorId, structure), entryConverter, referencer);
        mName = name;
        mAuthorId = authorId;
        mStructure = structure;
        mConverter = converter;
    }

    @Override
    protected Firebase getAggregatesDb(ReviewListEntry entry) {
        return mStructure.getAggregatesDb(getDataBase(), getAuthorId(entry), getReviewId(entry));
    }

    @Override
    protected Firebase getReviewDb(ReviewListEntry entry) {
        return mStructure.getReviewDb(getDataBase(), getAuthorId(entry), getReviewId(entry));
    }

    @Override
    public void addEntry(DataReviewInfo review, PlaylistCallback callback) {
        update(review, callback, DbUpdater.UpdateType.INSERT_OR_UPDATE);
    }

    @Override
    public void removeEntry(DataReviewInfo review, PlaylistCallback callback) {
        update(review, callback, DbUpdater.UpdateType.DELETE);
    }

    private void update(DataReviewInfo review, PlaylistCallback callback, DbUpdater.UpdateType
            updateType) {
        ReviewListEntry entry = mConverter.convert(review);
        Map<String, Object> map = getUpdatesMap(entry, updateType);
        getDataBase().updateChildren(map, newListener(entry, callback, updateType));
    }

    @NonNull
    private AuthorId getAuthorId(ReviewListEntry entry) {
        return new AuthorIdParcelable(entry.getAuthorId());
    }

    @NonNull
    private ReviewId getReviewId(ReviewListEntry entry) {
        return new DatumReviewId(entry.getReviewId());
    }

    @NonNull
    private Firebase.CompletionListener newListener(final ReviewListEntry entry,
                                                    final PlaylistCallback callback,
                                                    final DbUpdater.UpdateType updateType) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                RepositoryResult result;
                if (firebaseError == null) {
                    result = new RepositoryResult(mConverter.convert(entry));
                } else {
                    result = new RepositoryResult(CallbackMessage.error(firebaseError.getMessage
                            ()));
                }

                if (updateType.equals(DbUpdater.UpdateType.INSERT_OR_UPDATE)) {
                    callback.onAddedToPlaylistCallback(result);
                } else {
                    callback.onRemovedFromPlaylistCallback(result);
                }
            }
        };
    }

    @NonNull
    private Map<String, Object> getUpdatesMap(ReviewListEntry entry, DbUpdater.UpdateType type) {
        return mStructure.getPlaylistUpdater(mName, mAuthorId).getUpdatesMap(entry, type);
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 18/12/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class PlaylistStructure implements FbReviews {
        private final String mPlaylistName;
        private final AuthorId mPlaylistOwner;
        private final FbReviewsStructure mRootStructure;

        private PlaylistStructure(String playlistName, AuthorId playlistOwner, FbReviewsStructure
                rootStructure) {
            mPlaylistName = playlistName;
            mPlaylistOwner = playlistOwner;
            mRootStructure = rootStructure;
        }

        @Override
        public Firebase getListEntriesDb(Firebase root) {
            return mRootStructure.getPlaylistDb(root, mPlaylistOwner, mPlaylistName);
        }

        @Override
        public Firebase getListEntryDb(Firebase root, ReviewId reviewId) {
            return mRootStructure.getListEntryDb(root, reviewId);
        }
    }
}
