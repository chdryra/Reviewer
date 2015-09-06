package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectComparators extends ComparatorCollection<GvSubjectList.GvSubject> {
    private static SubjectComparators sComparators = new SubjectComparators();

    private SubjectComparators() {
        super(new TextComparators.AlphabeticalIgnoreCase<GvSubjectList.GvSubject>());
    }

    public static SubjectComparators getComparators() {
        return sComparators;
    }
}
