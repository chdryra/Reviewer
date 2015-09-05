package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorSorters extends SorterCollection<GvAuthorList.GvAuthor> {
    private static AuthorSorters sSorters = new AuthorSorters();

    private AuthorSorters() {
        super(new NameComparator());
    }

    public static AuthorSorters getSorters() {
        return sSorters;
    }

    private static class NameComparator implements Comparator<GvAuthorList.GvAuthor> {
        @Override
        public int compare(GvAuthorList.GvAuthor lhs, GvAuthorList.GvAuthor rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
