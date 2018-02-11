/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Factories;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Authentication.Implementation.AuthorProfileDefault;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumDateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ProfileImageDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorProfileSnapshot {
    public AuthorProfileDefault newProfile(String name, AuthorId id, long dateJoined, @Nullable Bitmap photo) {
        AuthorName author = new AuthorNameDefault(name, id);
        return new AuthorProfileDefault(author, new DatumDateTime(dateJoined), new ProfileImageDefault(id, photo));
    }

    public AuthorProfileDefault newProfile(String name, @Nullable Bitmap photo) {
        return newProfile(name, AuthorIdGenerator.newId(), new Date().getTime(), photo);
    }

    public AuthorProfile newUpdatedProfile(AuthorProfile oldProfile, @Nullable String name, @Nullable Bitmap photo) {
        AuthorName author = oldProfile.getAuthor();
        if(name == null || name.length() == 0) name = author.getName();

        return newProfile(name, author.getAuthorId(), oldProfile.getJoined().getTime(), photo);
    }
}
