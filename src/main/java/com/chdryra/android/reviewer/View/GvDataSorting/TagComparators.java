package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagComparators extends ComparatorCollection<GvTag> {
    private static TagComparators sComparators = new TagComparators();

    private TagComparators() {
        super(new TextComparators.AlphabeticalIgnoreCase<GvTag>());
    }

    //Static methods
    public static TagComparators getComparators() {
        return sComparators;
    }
}
