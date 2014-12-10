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

public class GVFactList extends GVDataList<GVFactList.GvFact> {

    GVFactList() {
        super(GvType.FACTS);
    }

    @Override
    protected Comparator<GvFact> getDefaultComparator() {

        return new Comparator<GvFact>() {
            @Override
            public int compare(GvFact lhs, GvFact rhs) {
                int comp = lhs.getLabel().compareTo(rhs.getLabel());
                if (comp == 0) {
                    comp = lhs.getValue().compareTo(rhs.getValue());
                }

                return comp;
            }
        };
    }

    void add(String label, String value) {
        add(new GvFact(label, value));
    }

    /**
     * {@link GVDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdFactList.MdFact}
     * {@link ViewHolder}: {@link VHFact}
     */

    public static class GvFact extends GVDualText implements DataFact {
        public static final Parcelable.Creator<GvFact> CREATOR = new Parcelable
                .Creator<GvFact>() {
            public GvFact createFromParcel(Parcel in) {
                return new GvFact(in);
            }

            public GvFact[] newArray(int size) {
                return new GvFact[size];
            }
        };

        GvFact(String label, String value) {
            super(label, value);
        }

        private GvFact(Parcel in) {
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
