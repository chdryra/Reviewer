package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationComparators extends ComparatorCollection<GvLocationList.GvLocation> {
    private static LocationComparators sComparators = new LocationComparators();

    private LocationComparators() {
        super(new LocationNameAlphabetical());
    }

    //Static methods
    public static LocationComparators getComparators() {
        return sComparators;
    }

    private static class LocationNameAlphabetical implements Comparator<GvLocationList.GvLocation> {
        //Overridden
        @Override
        public int compare(GvLocationList.GvLocation lhs, GvLocationList.GvLocation rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
