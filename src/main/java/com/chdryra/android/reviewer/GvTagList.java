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

public class GvTagList extends GvDataList<GvTagList.GvTag> {
    public static final GvDataType TYPE = new GvDataType("tag");

    public GvTagList() {
        super(GvTag.class, TYPE);
    }

    @Override
    protected Comparator<GvTag> getDefaultComparator() {
        return new Comparator<GvTag>() {

            @Override
            public int compare(GvTag lhs, GvTag rhs) {
                return lhs.get().compareToIgnoreCase(rhs.get());
            }
        };
    }

    /**
     * {@link } version of: {@link com.chdryra.android.reviewer.TagsManager.ReviewTag}
     * {@link ViewHolder}: {@link VhTag}
     * <p/>
     * <p>
     * Ignores case when comparing tags.
     * </p>
     */
    public static class GvTag extends GvText {
        public static final Parcelable.Creator<GvTag> CREATOR = new Parcelable
                .Creator<GvTag>() {
            public GvTag createFromParcel(Parcel in) {
                return new GvTag(in);
            }

            public GvTag[] newArray(int size) {
                return new GvTag[size];
            }
        };

        public GvTag() {
            super();
        }

        public GvTag(String tag) {
            super(tag);
        }

        GvTag(Parcel in) {
            super(in);
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VhTag(false);
        }

        @Override
        public String getStringSummary() {
            return "#" + get();
        }
    }
}
