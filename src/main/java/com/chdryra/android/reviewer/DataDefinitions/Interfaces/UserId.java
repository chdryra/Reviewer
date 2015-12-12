package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserId {
    String NULL_ID_STRING = "NULL";

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
