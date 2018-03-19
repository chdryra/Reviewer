/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Factories;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterAuthorProfile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.ConverterSocialProfile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbAuthorProfileRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation.FbSocialProfileRef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryFbProfile {
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FbDataReferencer mReferencer;
    private final UserProfileConverter mConverter;

    public FactoryFbProfile(Firebase dataRoot, FbUsersStructure structure, FbDataReferencer
            referencer, UserProfileConverter converter) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferencer = referencer;
        mConverter = converter;
    }

    public AuthorProfileRef newAuthorProfile(AuthorId authorId) {
        return new FbAuthorProfileRef(mStructure.getProfileDb(mDataRoot, authorId),
                new ConverterAuthorProfile(mConverter), authorId, mReferencer);
    }

    public SocialProfileRef newSocialProfile(AuthorId authorId) {
        return new FbSocialProfileRef(mDataRoot,
                new ConverterSocialProfile(authorId), authorId, mStructure, mReferencer);
    }
}
