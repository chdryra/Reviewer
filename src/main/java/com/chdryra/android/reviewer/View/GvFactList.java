/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Controller.DataFact;
import com.chdryra.android.reviewer.Controller.DataValidator;

import java.util.Comparator;

public class GvFactList extends GvDataList<GvFactList.GvFact> {
    public static final GvDataType TYPE = new GvDataType("fact");

    public GvFactList() {
        super(GvFact.class, TYPE);
    }

    public GvFactList(GvReviewId id, GvFactList data) {
        super(id, data);
    }

    public GvUrlList getUrls() {
        GvUrlList urls = new GvUrlList();
        for (GvFact fact : this) {
            if (fact.isUrl()) urls.add((GvUrlList.GvUrl) fact);
        }

        return hasHoldingReview() ? new GvUrlList(getHoldingReviewId(), urls) : urls;
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
     * {@link GvData} version of: {@link com.chdryra
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

        public GvFact(GvReviewId id, String label, String value) {
            super(id, label, value);
        }

        protected GvFact(Parcel in) {
            super(in);
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

        @Override
        public boolean isUrl() {
            return false;
        }
    }
}
