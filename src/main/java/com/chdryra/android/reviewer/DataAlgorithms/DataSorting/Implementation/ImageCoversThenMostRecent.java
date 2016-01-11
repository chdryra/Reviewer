package com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ImageCoversThenMostRecent implements Comparator<DataImage> {
    private Comparator<DataDate> mDateComparator;

    public ImageCoversThenMostRecent(Comparator<DataDate> dateComparator) {
        mDateComparator = dateComparator;
    }

    @Override
    public int compare(DataImage lhs, DataImage rhs) {
        int comp;
        if (lhs.isCover() && !rhs.isCover()) {
            comp = -1;
        } else if (!lhs.isCover() && rhs.isCover()) {
            comp = 1;
        } else {
            return mDateComparator.compare(lhs.getDate(), rhs.getDate());
        }

        return comp;
    }
}
