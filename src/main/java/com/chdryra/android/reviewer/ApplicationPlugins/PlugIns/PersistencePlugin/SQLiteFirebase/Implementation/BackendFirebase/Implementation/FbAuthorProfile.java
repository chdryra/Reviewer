/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by: Rizwan Choudrey
 * On: 02/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FbAuthorProfile implements AuthorProfile {
    private final AuthorId mAuthorId;
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FactoryFbReference mReferenceFactory;
    private final SnapshotConverter<AuthorProfileSnapshot> mConverter;

    public FbAuthorProfile(AuthorId authorId,
                           Firebase dataRoot,
                           FbUsersStructure structure,
                           FactoryFbReference referenceFactory,
                           SnapshotConverter<AuthorProfileSnapshot> converter) {
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
    public void getProfileSnapshot(final ProfileCallback callback) {
        getDb().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AuthorProfileSnapshot profile = mConverter.convert(dataSnapshot);
                CallbackMessage message = CallbackMessage.ok();
                if(profile == null) {
                    profile = new AuthorProfileSnapshot();
                    message = CallbackMessage.error("Error getting profile");
                }
                callback.onProfile(profile, message);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onProfile(new AuthorProfileSnapshot(), CallbackMessage.error(firebaseError.getMessage()));
            }
        });
    }

    private Firebase getDb() {
        return mStructure.getProfileDb(mDataRoot, mAuthorId);
    }
}
