package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageComparators extends ComparatorCollection<GvImageList.GvImage> {
    private static ImageComparators sComparators = new ImageComparators();

    private ImageComparators() {
        super(new CoverThenMostRecent());
    }

    //Static methods
    public static ImageComparators getComparators() {
        return sComparators;
    }

    private static class CoverThenMostRecent implements Comparator<GvImageList.GvImage> {
        //Overridden
        @Override
        public int compare(GvImageList.GvImage lhs, GvImageList.GvImage rhs) {
            int comp = 0;
            if (lhs.isCover() && !rhs.isCover()) {
                comp = -1;
            } else if (!lhs.isCover() && rhs.isCover()) {
                comp = 1;
            } else {
                return rhs.getDate().compareTo(lhs.getDate());
            }

            return comp;
        }
    }
}
