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

public class VgTagList extends VgDataList<VgTagList.VgTag> {

    public VgTagList() {
        super(GvType.TAGS);
    }

    public void add(String string) {
        if (string != null && string.length() > 0) {
            add(new VgTag(string));
        }
    }

    @Override
    protected Comparator<VgTag> getDefaultComparator() {
        return new Comparator<VgTag>() {

            @Override
            public int compare(VgTag lhs, VgTag rhs) {
                return lhs.get().compareTo(rhs.get());
            }
        };
    }

    /**
     * {@link } version of: {@link com.chdryra.android.reviewer.TagsManager.ReviewTag}
     * {@link ViewHolder}: {@link VHTag}
     * <p/>
     * <p>
     * Ignores case when comparing tags.
     * </p>
     */
    public static class VgTag extends VgText {
        public static final Parcelable.Creator<VgTag> CREATOR = new Parcelable
                .Creator<VgTag>() {
            public VgTag createFromParcel(Parcel in) {
                return new VgTag(in);
            }

            public VgTag[] newArray(int size) {
                return new VgTag[size];
            }
        };

        VgTag() {
            super();
        }

        VgTag(String tag) {
            super(tag);
        }

        VgTag(Parcel in) {
            super(in);
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHTag();
        }
    }
}
