package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectComparators extends ComparatorCollection<GvSubject> {
    private static SubjectComparators sComparators = new SubjectComparators();

    private SubjectComparators() {
        super(new TextComparators.AlphabeticalIgnoreCase<GvSubject>());
    }

    //Static methods
    public static SubjectComparators getComparators() {
        return sComparators;
    }
}
