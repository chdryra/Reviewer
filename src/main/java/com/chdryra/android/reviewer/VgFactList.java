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

public class VgFactList extends VgDataList<VgFactList.VgFact> {

    VgFactList() {
        super(GvType.FACTS);
    }

    @Override
    protected Comparator<VgFact> getDefaultComparator() {

        return new Comparator<VgFact>() {
            @Override
            public int compare(VgFact lhs, VgFact rhs) {
                int comp = lhs.getLabel().compareTo(rhs.getLabel());
                if (comp == 0) {
                    comp = lhs.getValue().compareTo(rhs.getValue());
                }

                return comp;
            }
        };
    }

    void add(String label, String value) {
        add(new VgFact(label, value));
    }

    /**
     * {@link VgDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdFactList.MdFact}
     * {@link ViewHolder}: {@link VHFact}
     */

    public static class VgFact extends VgDualText implements DataFact {
        public static final Parcelable.Creator<VgFact> CREATOR = new Parcelable
                .Creator<VgFact>() {
            public VgFact createFromParcel(Parcel in) {
                return new VgFact(in);
            }

            public VgFact[] newArray(int size) {
                return new VgFact[size];
            }
        };

        VgFact(String label, String value) {
            super(label, value);
        }

        private VgFact(Parcel in) {
            super(in.readString(), in.readString());
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHFact();
        }

        @Override
        public boolean isValidForDisplay() {
            return getLabel() != null && getLabel().length() > 0 && getValue() != null &&
                    getValue().length() > 0;
        }

        @Override
        public String getLabel() {
            return getUpper();
        }

        @Override
        public String getValue() {
            return getLower();
        }
    }
}
