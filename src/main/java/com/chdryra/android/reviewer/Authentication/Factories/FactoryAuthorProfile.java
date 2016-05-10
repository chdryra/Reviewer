/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorProfile {
    public AuthorProfile newProfile(String name, String authorId, long dateJoined) {
        DataAuthor author = new DatumAuthor(name, new DatumAuthorId(authorId));
        return new AuthorProfile(author, new DatumDate(dateJoined));
    }

    public AuthorProfile newProfile(String name) {
        DataAuthor author = new DatumAuthor(name, AuthorId.generateId());
        return new AuthorProfile(author, new DatumDate(new Date().getTime()));
    }

    public AuthorProfile newNullAuthorProfile() {
        return new AuthorProfile();
    }
}
