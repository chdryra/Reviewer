/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Interfaces.Playlist;
import com.chdryra.android.reviewer.Persistence.Interfaces.PlaylistCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 19/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlaylistDeleter implements PlaylistCallback {
    private final ReviewId mReviewId;
    private final Playlist mPlaylist;
    private final DeleteCallback mCallback;

    public interface DeleteCallback {
        void onDeletedFromPlaylist(String playlistName, ReviewId reviewId, CallbackMessage message);
    }

    public PlaylistDeleter(ReviewId reviewId, Playlist playlist, DeleteCallback callback) {
        mReviewId = reviewId;
        mPlaylist = playlist;
        mCallback = callback;
    }

    public void delete() {
        mPlaylist.hasEntry(mReviewId, this);
    }

    @Override
    public void onAddedToPlaylistCallback(CallbackMessage message) {

    }

    @Override
    public void onRemovedFromPlaylistCallback(CallbackMessage message) {
        mCallback.onDeletedFromPlaylist(mPlaylist.getName(), mReviewId, message);
    }

    @Override
    public void onPlaylistHasReviewCallback(boolean hasReview, CallbackMessage message) {
        if (!hasReview && message.isOk()) {
            String name = mPlaylist.getName();
            mCallback.onDeletedFromPlaylist(name, mReviewId, CallbackMessage.ok("Review  not in " + name));
        } else {
            mPlaylist.removeEntry(mReviewId, this);
        }
    }
}
