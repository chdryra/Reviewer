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

class GVFactList extends GVReviewDataList<GVFactList.GVFact> {

    public GVFactList() {
        super(GVType.FACTS);
    }

    public void add(String label, String value) {
        add(new GVFact(label, value));
    }

    public boolean containsLabel(String label) {
        boolean contains = false;
        for (GVFact fact : this) {
            contains = fact.getLabel().equalsIgnoreCase(label);
            if (contains) break;
        }

        return contains;
    }

    public void remove(String label, String value) {
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

    class GVFact extends GVDualString {
        public GVFact(String label, String value) {
            super(label, value);
        }

        public String getLabel() {
            return getUpper();
        }

        public String getValue() {
            return getLower();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHFactView();
        }


    }
}
