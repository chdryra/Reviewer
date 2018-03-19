/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Follow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbSocialStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.List;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbSocialProfileRef extends FbRefData<SocialProfile> implements SocialProfileRef {
    private final Firebase mRootReference;
    private AuthorId mAuthorId;
    private FbSocialStructure mStructure;
    private FbDataReferencer mReferencer;

    public FbSocialProfileRef(Firebase rootReference,
                              SnapshotConverter<SocialProfile> converter,
                              AuthorId authorId,
                              FbSocialStructure structure,
                              FbDataReferencer referencer) {
        super(structure.getSocialDb(rootReference, authorId), converter);
        mRootReference = rootReference;
        mAuthorId = authorId;
        mStructure = structure;
        mReferencer = referencer;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public AuthorListRef getFollowing() {
        return mReferencer.newAuthorList(mStructure.getFollowingDb(mRootReference, mAuthorId));
    }

    @Override
    public AuthorListRef getFollowers() {
        return mReferencer.newAuthorList(mStructure.getFollowersDb(mRootReference, mAuthorId));
    }

    @Override
    public void followUnfollow(final AuthorId toFollow, final FollowUnfollow type, final
    FollowCallback callback) {
        DbUpdater.UpdateType updateType = type.equals(FollowUnfollow.FOLLOW) ?
                DbUpdater.UpdateType.INSERT_OR_UPDATE : DbUpdater.UpdateType.DELETE;

        DbUpdater<Follow> updater = mStructure.getSocialUpdater();
        Map<String, Object> map = updater.getUpdatesMap(new Follow(mAuthorId, toFollow),
                updateType);

        mRootReference.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                callback.onFollow(toFollow, type, getCallbackMessage(firebaseError));
            }
        });
    }

    @Override
    public void isFollowing(final AuthorId authorId, final IsFollowingCallback callback) {
        getFollowing().dereference(new DereferenceCallback<List<AuthorId>>() {
            @Override
            public void onDereferenced(DataValue<List<AuthorId>> value) {
                if (value.hasValue()) {
                    for (AuthorId author : value.getData()) {
                        if (author.equals(authorId)) {
                            callback.onIsFollowing(authorId, true, CallbackMessage.ok());
                            break;
                        }
                    }
                    callback.onIsFollowing(authorId, false, CallbackMessage.ok());
                } else {
                    callback.onIsFollowing(authorId, false, CallbackMessage.error("Error " +
                            "dereferencing"));
                }
            }
        });
    }

    @NonNull
    private CallbackMessage getCallbackMessage(FirebaseError firebaseError) {
        CallbackMessage message = CallbackMessage.ok();
        if (firebaseError != null) {
            BackendError backendError = FirebaseBackend.backendError(firebaseError);
            message = CallbackMessage.error(backendError.getMessage());
        }
        return message;
    }
}
