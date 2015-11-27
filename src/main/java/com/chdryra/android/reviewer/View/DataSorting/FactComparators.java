package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactComparators extends ComparatorCollection<DataFact> {
    private static FactComparators sComparators = new FactComparators();

    private FactComparators() {
        super(new AlphabeticalLabelThenValue());
    }

    //Static methods
    public static FactComparators getComparators() {
        return sComparators;
    }

    private static class AlphabeticalLabelThenValue implements Comparator<DataFact> {
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
}
