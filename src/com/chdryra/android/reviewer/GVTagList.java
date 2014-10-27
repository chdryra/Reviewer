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

class GVTagList extends GVReviewDataList<GVTagList.GVTag> {

    GVTagList() {
        super(GVType.TAGS);
    }

    /**
     * {@link } version of: {@link com.chdryra.android.reviewer.TagsManager.ReviewTag}
     * {@link ViewHolder}: {@link VHTag}
     * <p/>
     * <p>
     * Ignores case when comparing tags.
     * </p>
     */
    static class GVTag extends GVText {
        public static final Parcelable.Creator<GVTag> CREATOR = new Parcelable
                .Creator<GVTag>() {
            public GVTag createFromParcel(Parcel in) {
                return new GVTag(in);
            }

            public GVTag[] newArray(int size) {
                return new GVTag[size];
            }
        };

        GVTag() {
            super();
        }

        GVTag(String tag) {
            super(tag);
        }

        GVTag(Parcel in) {
            super(in);
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHTag();
        }
    }

    void add(String string) {
        if (string != null && string.length() > 0) {
            add(new GVTag(string));
        }
    }

    void remove(String string) {
        remove(new GVTag(string));
    }

    @Override
    protected Comparator<GVTag> getDefaultComparator() {
        return new Comparator<GVTag>() {

            @Override
            public int compare(GVTag lhs, GVTag rhs) {
                return lhs.get().compareTo(rhs.get());
            }
        };
    }
}
