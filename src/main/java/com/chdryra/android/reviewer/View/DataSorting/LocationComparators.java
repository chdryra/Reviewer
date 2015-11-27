package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationComparators extends ComparatorCollection<DataLocation> {
    private static LocationComparators sComparators = new LocationComparators();

    private LocationComparators() {
        super(new LocationNameAlphabetical());
    }

    //Static methods
    public static LocationComparators getComparators() {
        return sComparators;
    }

    private static class LocationNameAlphabetical implements Comparator<DataLocation> {
        //Overridden
        @Override
        public int compare(DataLocation lhs, DataLocation rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
