package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageSorters extends SortingCollection<GvImageList.GvImage> {
    private static ImageSorters sSorters = new ImageSorters();

    private ImageSorters() {
        super(new ImageComparator());
    }

    public static ImageSorters getSorters() {
        return sSorters;
    }

    private static class ImageComparator implements Comparator<GvImageList.GvImage> {
        @Override
        public int compare(GvImageList.GvImage lhs, GvImageList.GvImage rhs) {
            int comp = 0;
            if (lhs.isCover() && !rhs.isCover()) {
                comp = -1;
            } else if (!lhs.isCover() && rhs.isCover()) {
                comp = 1;
            } else {
                if (lhs.getDate().after(rhs.getDate())) {
                    comp = -1;
                } else if (lhs.getDate().before(rhs.getDate())) {
                    comp = 1;
                }
            }

            return comp;
        }
    }
}
