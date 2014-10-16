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

import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;

/**
 * GVReviewDataList: GVTag
 * <p>
 * ViewHolder: VHTagView
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewTags`
 * @see com.chdryra.android.reviewer.VHTagView
 */
class GVTagList extends GVReviewDataList<GVTagList.GVTag> {

    GVTagList() {
        super(GVType.TAGS);
    }

    /**
     * GVData version of: ReviewTag
     * ViewHolder: VHTagView
     * <p/>
     * <p>
     * Ignores case when comparing tags.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.TagsManager.ReviewTag
     * @see com.chdryra.android.reviewer.VHTagView
     */
    static class GVTag extends GVString {
        public static final Parcelable.Creator<GVTag> CREATOR = new Parcelable
                .Creator<GVTag>() {
            public GVTag createFromParcel(Parcel in) {
                return new GVTag(in);
            }

            public GVTag[] newArray(int size) {
                return new GVTag[size];
            }
        };

        GVTag(String tag) {
            super(tag);
        }

        GVTag(Parcel in) {
            super(in);
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHTagView();
        }

        @Override
        public int describeContents() {
            return super.describeContents();
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
        }
    }

    void add(String string) {
        if (string != null && string.length() > 0) {
            add(new GVTag(string));
        }
    }

    boolean contains(String string) {
        return contains(new GVTag(string));
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
