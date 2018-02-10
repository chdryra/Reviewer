/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by: Rizwan Choudrey
 * On: 02/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FbAuthorProfile implements ProfileReference {
    private final AuthorId mAuthorId;
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FbDataReferencer mReferenceFactory;
    private final SnapshotConverter<AuthorProfile> mConverter;

    public FbAuthorProfile(AuthorId authorId,
                           Firebase dataRoot,
                           FbUsersStructure structure,
                           FbDataReferencer referenceFactory,
                           SnapshotConverter<AuthorProfile> converter) {
        mAuthorId = authorId;
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferenceFactory = referenceFactory;
        mConverter = converter;
    }

    @Override
    public AuthorReference getAuthor() {
        return mReferenceFactory.newNamedAuthor(getDb().child(Profile.AUTHOR), mAuthorId);
    }

    @Override
    public DataReference<ProfileImage> getProfileImage() {
        return mReferenceFactory.newImageReference(getDb().child(Profile.PHOTO), mAuthorId);
    }

    @Override
    public void dereference(final Callback callback) {
        getDb().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AuthorProfile profile = mConverter.convert(dataSnapshot);
                CallbackMessage message = CallbackMessage.ok();
                if(profile == null) {
                    profile = new AuthorProfile();
                    message = CallbackMessage.error("Error getting profile");
                }
                callback.onProfile(profile, message);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onProfile(new AuthorProfile(), CallbackMessage.error(firebaseError.getMessage()));
            }
        });
    }

    private Firebase getDb() {
        return mStructure.getProfileDb(mDataRoot, mAuthorId);
    }
}
