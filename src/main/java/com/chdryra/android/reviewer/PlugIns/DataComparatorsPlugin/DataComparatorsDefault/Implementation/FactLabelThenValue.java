/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactLabelThenValue implements Comparator<DataFact> {
    @Override
    public int compare(DataFact lhs, DataFact rhs) {
        int comp = lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
        if (comp == 0) {
            comp = lhs.getValue().compareToIgnoreCase(rhs.getValue());
        }

        return comp;
    }
}
