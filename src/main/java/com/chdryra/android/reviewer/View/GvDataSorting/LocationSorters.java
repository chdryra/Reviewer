package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationSorters extends SorterCollection<GvLocationList.GvLocation> {
    private static LocationSorters sSorters = new LocationSorters();

    private LocationSorters() {
        super(new LocationComparator());
    }

    public static LocationSorters getSorters() {
        return sSorters;
    }

    private static class LocationComparator implements Comparator<GvLocationList.GvLocation> {
        @Override
        public int compare(GvLocationList.GvLocation lhs, GvLocationList.GvLocation rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
