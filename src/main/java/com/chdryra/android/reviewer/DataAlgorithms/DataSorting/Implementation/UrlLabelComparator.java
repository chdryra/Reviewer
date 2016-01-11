package com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class UrlLabelComparator implements Comparator<DataUrl> {
    @Override
    public int compare(DataUrl lhs, DataUrl rhs) {
        int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
        if (comp == 0) {
            comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
        }

        return comp;
    }
}
