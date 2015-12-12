/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.UserModel;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Holds Author data. Currently only wraps a name and unique {@link AuthorId}.
 */
public class Author implements DataAuthor{
    private final AuthorId mId;
    private final String mName;

    public Author(String name, AuthorId id) {
        mName = name;
        mId = id;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public UserId getUserId() {
        return mId;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        return !(mId != null ? !mId.equals(author.mId) : author.mId != null)
                && !(mName != null ? !mName.equals(author.mName) : author.mName != null);

    }

    @Override
    public int hashCode() {
        int result = mId != null ? mId.hashCode() : 0;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }
}
