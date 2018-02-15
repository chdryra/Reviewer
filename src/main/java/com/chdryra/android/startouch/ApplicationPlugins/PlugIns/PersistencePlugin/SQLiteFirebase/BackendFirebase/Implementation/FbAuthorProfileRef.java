/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.ProfileAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.Authentication.Implementation.AuthorProfileDefault;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 02/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FbAuthorProfileRef extends FbRefData<AuthorProfile> implements AuthorProfileRef {
    private static final AuthorProfileDefault NULL_PROFILE = new AuthorProfileDefault();

    private final AuthorId mAuthorId;
    private final FbDataReferencer mReferenceFactory;

    public FbAuthorProfileRef(Firebase dbRoot,
                              SnapshotConverter<AuthorProfile> converter,
                              AuthorId authorId,
                              FbDataReferencer referenceFactory) {
        super(dbRoot, converter);
        mAuthorId = authorId;
        mReferenceFactory = referenceFactory;
    }

    @Nullable
    @Override
    protected AuthorProfile getNullValue() {
        return NULL_PROFILE;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public AuthorRef getAuthor() {
        return mReferenceFactory.newAuthorName(getReference().child(ProfileAuthor.AUTHOR), mAuthorId);
    }

    @Override
    public DataReference<ProfileImage> getProfileImage() {
        return mReferenceFactory.newImageReference(getReference().child(ProfileAuthor.PHOTO), mAuthorId);
    }
}
