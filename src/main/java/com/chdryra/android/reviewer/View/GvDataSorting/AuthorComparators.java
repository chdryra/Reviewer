package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorComparators extends ComparatorCollection<GvAuthorList.GvAuthor> {
    private static AuthorComparators sComparators = new AuthorComparators();

    private AuthorComparators() {
        super(new AlphabeticalIgnoreCase());
    }

    //Static methods
    public static AuthorComparators getComparators() {
        return sComparators;
    }

    private static class AlphabeticalIgnoreCase implements Comparator<GvAuthorList.GvAuthor> {
        //Overridden
        @Override
        public int compare(GvAuthorList.GvAuthor lhs, GvAuthorList.GvAuthor rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
