/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;



import android.graphics.Bitmap;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.firebase.client.Firebase;

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

    public FbAuthorProfile(AuthorId authorId,
                           Firebase dataRoot,
                           FbUsersStructure structure,
                           FactoryFbReference referenceFactory) {
        mAuthorId = authorId;
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public AuthorReference getAuthor() {
        return mReferenceFactory.newNamedAuthor(getDb().child(Profile.AUTHOR), mAuthorId);
    }

    private Firebase getDb() {
        return mStructure.getProfileDb(mDataRoot, mAuthorId);
    }

    @Override
    public DataReference<DateTime> getDateJoined() {
        return mReferenceFactory.newDateReference(getDb().child(Profile.DATE));
    }

    @Override
    public DataReference<Bitmap> getProfileImage() {
        return mReferenceFactory.newImageReference(getDb().child(Profile.PHOTO));
    }
}
