/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateComparator implements Comparator<DateTime> {
    @Override
    public int compare(DateTime lhs, DateTime rhs) {
        Date lhsDate = new Date(lhs.getTime());
        Date rhsDate = new Date(rhs.getTime());
        return lhsDate.compareTo(rhsDate);
    }
}
