/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;


import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.CollectionReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class FollowSubscriber implements CollectionReference.ItemSubscriber<AuthorId> {
    private final AuthorListRef mFollowing;
    private final FollowListener mListener;

    public interface FollowListener {
        void onFollowing(AuthorId authorId);

        void onUnfollowing(AuthorId authorId);

        void onFollowInvalidate();
    }

    public FollowSubscriber(AuthorListRef following, FollowListener listener) {
        mFollowing = following;
        mListener = listener;
    }

    public void bind() {
        mFollowing.subscribe(this);
    }

    public void unbind() {
        mFollowing.unsubscribe(this);
    }

    @Override
    public void onItemAdded(AuthorId item) {
        mListener.onFollowing(item);
    }

    @Override
    public void onItemRemoved(AuthorId item) {
        mListener.onUnfollowing(item);
    }

    @Override
    public void onCollectionChanged(Collection<AuthorId> newItems) {
        for (AuthorId item : newItems) {
            mListener.onFollowing(item);
        }
    }

    @Override
    public void onInvalidated(CollectionReference<AuthorId, ?, ?> reference) {
        mListener.onFollowInvalidate();
    }
}
