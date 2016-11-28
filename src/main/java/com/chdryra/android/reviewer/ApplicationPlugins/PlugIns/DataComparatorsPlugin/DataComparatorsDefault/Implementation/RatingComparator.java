/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingComparator implements Comparator<DataRating> {
    private final FloatComparator mComparator;

    public RatingComparator(FloatComparator comparator) {
        mComparator = comparator;
    }

    @Override
    public int compare(DataRating lhs, DataRating rhs) {
        return mComparator.compare(lhs.getRating(), rhs.getRating());
    }
}
