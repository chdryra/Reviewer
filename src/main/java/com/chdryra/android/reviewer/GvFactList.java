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

public class GvFactList extends GvDataList<GvFactList.GvFact> {
    public static final GvDataType TYPE = new GvDataType("fact");

    public GvFactList() {
        super(TYPE);
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

    /**
     * {@link GvDataList.GvData} version of: {@link com.chdryra
     * .android.reviewer.MdFactList.MdFact}
     * {@link ViewHolder}: {@link VhFact}
     */

    public static class GvFact extends GvDualText implements DataFact {
        public static final Parcelable.Creator<GvFact> CREATOR = new Parcelable
                .Creator<GvFact>() {
            public GvFact createFromParcel(Parcel in) {
                return new GvFact(in);
            }

            public GvFact[] newArray(int size) {
                return new GvFact[size];
            }
        };

        public GvFact() {
            super();
        }

        public GvFact(String label, String value) {
            super(label, value);
        }

        private GvFact(Parcel in) {
            super(in.readString(), in.readString());
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VhFact();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validate(this);
        }

        @Override
        public String getStringSummary() {
            return getLabel() + ": " + getValue();
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
