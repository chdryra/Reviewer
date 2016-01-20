package com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationNameAlphabetical implements Comparator<DataLocation> {
    @Override
    public int compare(DataLocation lhs, DataLocation rhs) {
        return lhs.getName().compareToIgnoreCase(rhs.getName());
    }
}
