/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorProfileSnapshot {
    public AuthorProfileSnapshot newProfile(String name, AuthorId id, long dateJoined, @Nullable Bitmap photo) {
        NamedAuthor author = new DefaultNamedAuthor(name, id);
        return new AuthorProfileSnapshot(author, new DatumDateTime(dateJoined), new DefaultProfileImage(id, photo));
    }

    public AuthorProfileSnapshot newProfile(String name, @Nullable Bitmap photo) {
        return newProfile(name, AuthorIdGenerator.newId(), new Date().getTime(), photo);
    }

    public AuthorProfileSnapshot newUpdatedProfile(AuthorProfileSnapshot oldProfile, @Nullable String name, @Nullable Bitmap photo) {
        NamedAuthor author = oldProfile.getNamedAuthor();
        if(name == null || name.length() == 0) name = author.getName();

        return newProfile(name, author.getAuthorId(), oldProfile.getJoined().getTime(), photo);
    }
}
