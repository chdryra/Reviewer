package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactComparators extends ComparatorCollection<GvFactList.GvFact> {
    private static FactComparators sComparators = new FactComparators();

    private FactComparators() {
        super(new AlphabeticalLabelThenValue());
    }

    public static FactComparators getComparators() {
        return sComparators;
    }

    private static class AlphabeticalLabelThenValue implements Comparator<GvFactList.GvFact> {
        @Override
        public int compare(GvFactList.GvFact lhs, GvFactList.GvFact rhs) {
            int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
            if (comp == 0) {
                comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
            }

            return comp;
        }
    }
}
