package com.chdryra.android.reviewer.DataSorting.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactLabelThenValue implements Comparator<DataFact> {
    //Overridden
    @Override
    public int compare(DataFact lhs, DataFact rhs) {
        int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
        if (comp == 0) {
            comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
        }

        return comp;
    }
}
