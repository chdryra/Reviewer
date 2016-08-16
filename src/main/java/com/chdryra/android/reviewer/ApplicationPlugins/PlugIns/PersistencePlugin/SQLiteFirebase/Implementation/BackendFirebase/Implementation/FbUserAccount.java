/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbUserAccount implements UserAccount {
    private static final AuthenticationError NO_USER_ERROR = new AuthenticationError
            (ApplicationInstance.APP_NAME, AuthenticationError.Reason.NO_AUTHENTICATED_USER);

    private static final AuthenticationError NO_PROFILE_ERROR = new AuthenticationError
            (FirebaseBackend.NAME, AuthenticationError.Reason.UNKNOWN_USER);

    private static final DbUpdater.UpdateType INSERT_OR_UPDATE
            = DbUpdater.UpdateType.INSERT_OR_UPDATE;

    private AuthenticatedUser mAccountHolder;
    private Firebase mDataRoot;
    private FbUsersStructure mStructure;
    private UserProfileConverter mConverter;

    public FbUserAccount(AuthenticatedUser accountHolder,
                         Firebase dataRoot,
                         FbUsersStructure structure,
                         UserProfileConverter converter) {
        mAccountHolder = accountHolder;
        if(mAccountHolder.getAuthorId() == null) {
            throw new IllegalArgumentException("User should be an author!");
        }
        mDataRoot = dataRoot;
        mStructure = structure;
        mConverter = converter;
    }

    @Override
    public AuthenticatedUser getAccountHolder() {
        return mAccountHolder;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAccountHolder.getAuthorId();
    }

    @Override
    public void getAuthorProfile(final GetAuthorProfileCallback callback) {
        AuthorId authorId = mAccountHolder.getAuthorId();
        if(authorId == null) {
            callback.onAuthorProfile(mConverter.newNullProfile(), NO_USER_ERROR);
        } else {
            Firebase profile = mStructure.getProfileDb(mDataRoot, authorId);
            doSingleEvent(profile, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Profile value = dataSnapshot.getValue(Profile.class);
                    if(value == null) {
                        callback.onAuthorProfile(mConverter.newNullProfile(), NO_PROFILE_ERROR);
                    } else {
                        callback.onAuthorProfile(mConverter.toAuthorProfile(value), null);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    callback.onAuthorProfile(mConverter.newNullProfile(),
                            FirebaseBackend.authenticationError(firebaseError));
                }
            });
        }
    }


    @Override
    public void updateAuthorProfile(final AuthorProfile newProfile, final
    UpdateAuthorProfileCallback callback) {
        Map<String, Object> map
                = mStructure.getProfileUpdater().getUpdatesMap(mConverter.toUser(mAccountHolder), INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                AuthenticationError error = firebaseError == null ?
                        null : FirebaseBackend.authenticationError(firebaseError);
                callback.onAuthorProfileUpdated(newProfile, error);
            }
        });
    }

    @Override
    public void getSocialProfile(GetSocialProfileCallback callback) {

    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }
}
