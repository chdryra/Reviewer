/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Follow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbSocialStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbSocialProfile implements ProfileSocial {
    private AuthorId mId;
    private Firebase mRoot;
    private FbSocialStructure mStructure;
    private FbDataReferencer mReferencer;

    public FbSocialProfile(AuthorId id, Firebase root, FbSocialStructure structure,
                           FbDataReferencer referencer) {
        mId = id;
        mRoot = root;
        mStructure = structure;
        mReferencer = referencer;
    }

    @Override
    public AuthorId getAuthorId() {
        return mId;
    }

    @Override
    public RefAuthorList getFollowing() {
        return mReferencer.newAuthorList(mStructure.getFollowingDb(mRoot, mId));
    }

    @Override
    public RefAuthorList getFollowers() {
        return mReferencer.newAuthorList(mStructure.getFollowersDb(mRoot, mId));
    }

    @Override
    public void followUnfollow(final AuthorId authorId, final FollowUnfollow type, final FollowCallback callback) {
        DbUpdater.UpdateType updateType = type.equals(FollowUnfollow.FOLLOW) ?
                DbUpdater.UpdateType.INSERT_OR_UPDATE : DbUpdater.UpdateType.DELETE;

        DbUpdater<Follow> updater = mStructure.getSocialUpdater();
        Map<String, Object> map = updater.getUpdatesMap(new Follow(mId, authorId), updateType);

        mRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                callback.onFollowingCallback(authorId, type, getCallbackMessage(firebaseError));
            }
        });
    }

    @NonNull
    private CallbackMessage getCallbackMessage(FirebaseError firebaseError) {
        CallbackMessage message = CallbackMessage.ok();
        if(firebaseError != null) {
            BackendError backendError = FirebaseBackend.backendError(firebaseError);
            message = CallbackMessage.error(backendError.getMessage());
        }
        return message;
    }
}
