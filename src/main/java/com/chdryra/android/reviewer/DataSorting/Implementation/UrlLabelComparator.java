package com.chdryra.android.reviewer.DataSorting.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlLabelComparator implements Comparator<DataUrl> {
    //Overridden
    @Override
    public int compare(DataUrl lhs, DataUrl rhs) {
        int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
        if (comp == 0) {
            comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
        }

        return comp;
    }
}
