/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Implementation.UserModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;

/**
 * Holds Author data. Currently only wraps a name and unique {@link UserId}.
 */
public class Author implements DataAuthor{
    private final UserId mId;
    private final String mName;

    //Constructors
    public Author(String name, UserId id) {
        mName = name;
        mId = id;
    }

    //Overridden
    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getUserId() {
        return mId != null ? mId.toString() : "";
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
