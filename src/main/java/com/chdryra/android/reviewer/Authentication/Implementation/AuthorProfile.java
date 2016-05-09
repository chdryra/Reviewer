
/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorProfile {
    private DataAuthor mAuthor;
    private DataDate mDateJoined;

    public AuthorProfile() {
    }

    public AuthorProfile(DataAuthor author, DataDate dateJoined) {
        mAuthor = author;
        mDateJoined = dateJoined;
    }

    public DataAuthor getAuthor() {
        return mAuthor;
    }

    public DataDate getDateJoined() {
        return mDateJoined;
    }
}
