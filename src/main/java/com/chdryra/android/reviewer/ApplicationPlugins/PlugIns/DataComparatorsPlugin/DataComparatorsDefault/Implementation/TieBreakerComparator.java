/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TieBreakerComparator<ObjectType> implements Comparator<ObjectType> {
    private final Comparator<ObjectType> mComparator;
    private final Comparator<ObjectType>[] mTieBreakers;

    @SafeVarargs
    public TieBreakerComparator(Comparator<ObjectType> comparator,
                                Comparator<ObjectType>... tieBreakers) {
        mComparator = comparator;
        mTieBreakers = tieBreakers;
    }

    @Override
    public int compare(ObjectType lhs, ObjectType rhs) {
        int comp = mComparator.compare(lhs, rhs);
        if (comp == 0) {
            for(Comparator<ObjectType> comparator : mTieBreakers)
            comp = comparator.compare(lhs, rhs);
        }

        return comp;
    }
}
