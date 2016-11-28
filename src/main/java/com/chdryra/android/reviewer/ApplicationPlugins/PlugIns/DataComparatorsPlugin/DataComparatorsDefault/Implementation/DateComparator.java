/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateComparator implements Comparator<DateTime> {
    private final Ordering mOrdering;

    public DateComparator(Ordering ordering) {
        mOrdering = ordering;
    }

    @Override
    public int compare(DateTime lhs, DateTime rhs) {
        Date lhsDate = new Date(lhs.getTime());
        Date rhsDate = new Date(rhs.getTime());
        return lhsDate.compareTo(rhsDate)*mOrdering.getFactor();
    }
}
