/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbUserAccount implements UserAccount {
    private final AuthenticatedUser mAccountHolder;
    private final SocialProfile mSocialProfile;

    public FbUserAccount(AuthenticatedUser accountHolder, SocialProfile profile) {
        mAccountHolder = accountHolder;
        if(mAccountHolder.getAuthorId() == null) {
            throw new IllegalArgumentException("User should be an author!");
        }
        mSocialProfile = profile;
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
    public SocialProfile getSocialProfile() {
        return mSocialProfile;
    }

    //
//    @Override
//    public void getAuthorProfile(final GetAuthorProfileCallback callback) {
//        AuthorId authorId = mAccountHolder.getAuthorId();
//        if(authorId == null) {
//            callback.onAuthorProfile(mConverter.newNullProfile(), NO_USER_ERROR);
//        } else {
//            Firebase profile = mProfiles.getProfileDb(mDataRoot, authorId);
//            doSingleEvent(profile, new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Profile value = dataSnapshot.getValue(Profile.class);
//                    if(value == null) {
//                        callback.onAuthorProfile(mConverter.newNullProfile(), NO_PROFILE_ERROR);
//                    } else {
//                        callback.onAuthorProfile(mConverter.toAuthorProfile(value), null);
//                    }
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    callback.onAuthorProfile(mConverter.newNullProfile(),
//                            FirebaseBackend.authenticationError(firebaseError));
//                }
//            });
//        }
//    }
//
//
//    @Override
//    public void updateAuthorProfile(final AuthorProfile newProfile, final
//    UpdateAuthorProfileCallback callback) {
//        User user = mConverter.toUser(mAccountHolder);
//        Map<String, Object> map
//                = mProfiles.getProfileUpdater().getUpdatesMap(user, INSERT_OR_UPDATE);
//        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
//            @Override
//            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                AuthenticationError error = firebaseError == null ?
//                        null : FirebaseBackend.authenticationError(firebaseError);
//                callback.onAuthorProfileUpdated(newProfile, error);
//            }
//        });
//    }
//
//    private void doSingleEvent(Firebase root, ValueEventListener listener) {
//        root.addListenerForSingleValueEvent(listener);
//    }
}
