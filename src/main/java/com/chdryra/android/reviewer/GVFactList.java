/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;

class GVFactList extends GVReviewDataList<GVFactList.GVFact> {

    GVFactList() {
        super(GVType.FACTS);
    }

    /**
     * {@link GVReviewData} version of: {@link RDFact}
     * {@link ViewHolder}: {@link VHFact}
     */

    static class GVFact extends GVDualText {
        public static final Parcelable.Creator<GVFact> CREATOR = new Parcelable
                .Creator<GVFact>() {
            public GVFact createFromParcel(Parcel in) {
                return new GVFact(in);
            }

            public GVFact[] newArray(int size) {
                return new GVFact[size];
            }
        };

        GVFact(String label, String value) {
            super(label, value);
        }

        GVFact(Parcel in) {
            super(in.readString(), in.readString());
        }

        String getLabel() {
            return getUpper();
        }

        String getValue() {
            return getLower();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHFact();
        }

        @Override
        public boolean isValidForDisplay() {
            return getLabel() != null && getLabel().length() > 0 && getValue() != null &&
                   getValue().length() > 0;
        }
    }

    void add(String label, String value) {
        add(new GVFact(label, value));
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
}
