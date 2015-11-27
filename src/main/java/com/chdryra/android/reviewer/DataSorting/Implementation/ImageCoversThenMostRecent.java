package com.chdryra.android.reviewer.DataSorting.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;

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

    //Overridden
    @Override
    public int compare(DataImage lhs, DataImage rhs) {
        int comp = 0;
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
