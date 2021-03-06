/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;

/**
 * Holds Author data. Currently only wraps a name and unique {@link AuthorIdGenerator}.
 */
public class AuthorNameDefault implements AuthorName {
    private AuthorId mId;
    private String mName;

    public AuthorNameDefault() {
        this("", new AuthorIdParcelable(""));
    }

    public AuthorNameDefault(String name, AuthorId id) {
        mName = name;
        mId = id;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public AuthorId getAuthorId() {
        return mId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorNameDefault)) return false;

        AuthorNameDefault author = (AuthorNameDefault) o;

        return !(mId != null ? !mId.equals(author.mId) : author.mId != null)
                && !(mName != null ? !mName.equals(author.mName) : author.mName != null);

    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }
}
