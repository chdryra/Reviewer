/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ConverterAuthorProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.FbAuthorProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryAuthorProfile {
    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FbDataReferencer mReferencer;
    private final UserProfileConverter mConverter;

    public FactoryAuthorProfile(Firebase dataRoot, FbUsersStructure structure, FbDataReferencer
            referencer, UserProfileConverter converter) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferencer = referencer;
        mConverter = converter;
    }

    public ProfileAuthor newProfile(AuthorId authorId) {
        return new FbAuthorProfile(authorId, mDataRoot, mStructure, mReferencer, new ConverterAuthorProfile(mConverter));
    }
}
