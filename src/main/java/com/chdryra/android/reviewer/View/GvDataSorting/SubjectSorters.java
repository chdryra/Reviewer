package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectSorters extends SorterCollection<GvSubjectList.GvSubject> {
    private static SubjectSorters sSorters = new SubjectSorters();

    private SubjectSorters() {
        super(new SubjectComparator());
    }

    public static SubjectSorters getSorters() {
        return sSorters;
    }

    private static class SubjectComparator implements Comparator<GvSubjectList.GvSubject> {
        @Override
        public int compare(GvSubjectList.GvSubject lhs, GvSubjectList.GvSubject rhs) {
            return lhs.get().compareToIgnoreCase(rhs.get());
        }
    }
}
