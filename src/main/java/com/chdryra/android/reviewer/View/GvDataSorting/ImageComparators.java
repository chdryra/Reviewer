package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageComparators extends ComparatorCollection<GvImage> {
    private static ImageComparators sComparators = new ImageComparators();

    private ImageComparators() {
        super(new CoverThenMostRecent());
    }

    //Static methods
    public static ImageComparators getComparators() {
        return sComparators;
    }

    private static class CoverThenMostRecent implements Comparator<GvImage> {
        //Overridden
        @Override
        public int compare(GvImage lhs, GvImage rhs) {
            int comp = 0;
            if (lhs.isCover() && !rhs.isCover()) {
                comp = -1;
            } else if (!lhs.isCover() && rhs.isCover()) {
                comp = 1;
            } else {
                return DateComparators.getComparators().getDefault().compare(lhs.getDate(), rhs.getDate());
            }

            return comp;
        }
    }
}
