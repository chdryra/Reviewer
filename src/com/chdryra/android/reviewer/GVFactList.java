/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;

/**
 * GVReviewDataList: GVFact
 * <p>
 * ViewHolder: VHFactView
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewFacts
 * @see com.chdryra.android.reviewer.VHFactView
 */
class GVFactList extends GVReviewDataList<GVFactList.GVFact> {

    GVFactList() {
        super(GVType.FACTS);
    }

    void add(String label, String value) {
        add(new GVFact(label, value));
    }

    boolean containsLabel(String label) {
        boolean contains = false;
        for (GVFact fact : this) {
            contains = fact.getLabel().equalsIgnoreCase(label);
            if (contains) break;
        }

        return contains;
    }

    void remove(String label, String value) {
        remove(new GVFact(label, value));
    }

    @Override
    protected Comparator<GVFact> getDefaultComparator() {

        return new Comparator<GVFactList.GVFact>() {
            @Override
            public int compare(GVFact lhs, GVFact rhs) {
                int comp = lhs.getLabel().compareTo(rhs.getLabel());
                if (comp == 0) {
                    comp = lhs.getValue().compareTo(rhs.getValue());
                }

                return comp;
            }
        };
    }

    /**
     * GVData version of: RDFact
     * ViewHolder: VHFactView
     * <p/>
     * <p>
     * Really just a GVDualString with label as upper and value as lower.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVDualString
     * @see com.chdryra.android.reviewer.RDFact
     * @see com.chdryra.android.reviewer.VHFactView
     */

    class GVFact extends GVDualString {
        GVFact(String label, String value) {
            super(label, value);
        }

        String getLabel() {
            return getUpper();
        }

        String getValue() {
            return getLower();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHFactView();
        }
    }
}
