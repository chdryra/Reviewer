package com.chdryra.android.reviewer.DataSorting.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectAlphabetical implements Comparator<DataSubject> {
    //Overridden
    @Override
    public int compare(DataSubject lhs, DataSubject rhs) {
        return lhs.getSubject().compareToIgnoreCase(rhs.getSubject());
    }
}
