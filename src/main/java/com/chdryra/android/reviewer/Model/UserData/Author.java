/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.UserData;

/**
 * Holds Author data. Currently only wraps a name and unique {@link UserId}.
 */
public class Author {
    public final static Author NULL_AUTHOR = new Author("", UserId.NULL_ID);
    private final UserId mId;
    private final String mName;

    //Constructors
    public Author(String name, UserId id) {
        mName = name;
        mId = id;
    }

    //public methods
    public String getName() {
        return mName;
    }

    public UserId getUserId() {
        return mId;
    }

    //Overridden
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
