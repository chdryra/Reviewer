package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorComparators extends ComparatorCollection<DataAuthor> {
    private static AuthorComparators sComparators = new AuthorComparators();

    private AuthorComparators() {
        super(new AlphabeticalIgnoreCase());
    }

    //Static methods
    public static AuthorComparators getComparators() {
        return sComparators;
    }

    private static class AlphabeticalIgnoreCase implements Comparator<DataAuthor> {
        //Overridden
        @Override
        public int compare(DataAuthor lhs, DataAuthor rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
