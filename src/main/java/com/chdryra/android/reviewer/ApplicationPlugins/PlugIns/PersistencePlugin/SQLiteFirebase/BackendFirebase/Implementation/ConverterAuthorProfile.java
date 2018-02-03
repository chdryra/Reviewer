/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterAuthorProfile implements SnapshotConverter<AuthorProfile> {
    private final UserProfileConverter mConverter;

    public ConverterAuthorProfile(UserProfileConverter converter) {
        mConverter = converter;
    }

    @Nullable
    @Override
    public AuthorProfile convert(DataSnapshot snapshot) {
        Profile value = snapshot.getValue(Profile.class);
        if(value == null) return null;
        return mConverter.newProfile(value);
    }
}
