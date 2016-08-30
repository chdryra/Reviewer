/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorProfile {
    public AuthorProfile newProfile(String name, String authorId, long dateJoined) {
        NamedAuthor author = new DefaultNamedAuthor(name, new AuthorIdParcelable(authorId));
        return new AuthorProfile(author, new DatumDateTime(dateJoined));
    }

    public AuthorProfile newProfile(String name) {
        NamedAuthor author = new DefaultNamedAuthor(name, AuthorIdGenerator.newId());
        return new AuthorProfile(author, new DatumDateTime(new Date().getTime()));
    }

    public AuthorProfile newNullAuthorProfile() {
        return new AuthorProfile();
    }
}
