/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriterionSubjectThenRating implements Comparator<DataCriterion> {
    @Override
    public int compare(DataCriterion lhs, DataCriterion
            rhs) {
        int comp = lhs.getSubject().compareToIgnoreCase(rhs.getSubject());
        if (comp == 0) {
            if (lhs.getRating() < rhs.getRating()) {
                comp = 1;
            } else if (lhs.getRating() > rhs.getRating()) {
                comp = -1;
            }
        }

        return comp;
    }
}
