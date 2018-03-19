/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.ProfileSocial;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Interfaces.SnapshotConverter;
import com.chdryra.android.startouch.Authentication.Implementation.SocialProfileDefault;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.firebase.client.DataSnapshot;

/**
 * Created by: Rizwan Choudrey
 * On: 28/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterSocialProfile implements SnapshotConverter<SocialProfile> {
    private final AuthorId mAuthorId;

    public ConverterSocialProfile(AuthorId authorId) {
        mAuthorId = authorId;
    }

    @Nullable
    @Override
    public SocialProfile convert(DataSnapshot snapshot) {
        ProfileSocial value = snapshot.getValue(ProfileSocial.class);
        if (value == null) return null;
        return new SocialProfileDefault(mAuthorId, value.getFollowing(), value.getFollowers());
    }
}
