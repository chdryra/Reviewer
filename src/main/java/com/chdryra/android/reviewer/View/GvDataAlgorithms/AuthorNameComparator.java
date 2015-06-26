/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 25/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorNameComparator implements GvComparator<GvAuthorList.GvAuthor> {
    @Override
    public int compare(GvAuthorList.GvAuthor datum1, GvAuthorList.GvAuthor datum2) {
        return datum1.getName().compareTo(datum2.getName());
    }

    @Override
    public GvKey getKey(GvAuthorList.GvAuthor datum) {
        return new AuthorNameKey(datum);
    }

    public static class AuthorNameKey implements GvKey {
        private String mName;

        public AuthorNameKey(GvAuthorList.GvAuthor author) {
            mName = author.getName();
        }

        @Override
        public String getLabel() {
            return mName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AuthorNameKey)) return false;

            AuthorNameKey that = (AuthorNameKey) o;

            return mName.equals(that.mName);

        }

        @Override
        public int hashCode() {
            return mName.hashCode();
        }
    }
}
