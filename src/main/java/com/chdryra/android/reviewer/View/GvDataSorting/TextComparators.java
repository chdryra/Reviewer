package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvText;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TextComparators extends ComparatorCollection<GvText> {
    private static TextComparators sComparators = new TextComparators();

    private TextComparators() {
        super(new AlphabeticalIgnoreCase<>());
    }

    //Static methods
    public static TextComparators getComparators() {
        return sComparators;
    }

    //Classes
    public static class AlphabeticalIgnoreCase<T extends GvText> implements Comparator<T> {
        //Overridden
        @Override
        public int compare(T lhs, T rhs) {
            return lhs.getString().compareToIgnoreCase(rhs.getString());
        }
    }
}
