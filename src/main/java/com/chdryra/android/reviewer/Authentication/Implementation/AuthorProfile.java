
/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorProfile {
    private NamedAuthor mAuthor;
    private DateTime mDateJoined;

    public AuthorProfile() {
    }

    public AuthorProfile(NamedAuthor author, DateTime dateJoined) {
        mAuthor = author;
        mDateJoined = dateJoined;
    }

    public NamedAuthor getAuthor() {
        return mAuthor;
    }

    public DateTime getDateJoined() {
        return mDateJoined;
    }
}
