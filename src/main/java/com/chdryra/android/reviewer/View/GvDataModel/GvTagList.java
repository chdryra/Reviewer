/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;

import java.util.ArrayList;
import java.util.Comparator;

public class GvTagList extends GvDataList<GvTagList.GvTag> {
    public static final GvDataType TYPE = new GvDataType("tag");
    public static final Class<GvTag> DATA_CLASS = GvTag.class;

    public GvTagList() {
        super(null, DATA_CLASS, TYPE);
    }

    public GvTagList(GvReviewId id) {
        super(id, DATA_CLASS, TYPE);
    }

    public GvTagList(GvTagList data) {
        super(data);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> tags = new ArrayList<>();
        for (GvTag tag : this) {
            tags.add(tag.get());
        }

        return tags;
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
     * {@link } version of: {@link TagsManager.ReviewTag}
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

        public GvTag(GvReviewId id, String tag) {
            super(id, tag);
        }

        public GvTag(GvTag tag) {
            this(tag.get());
        }

        GvTag(Parcel in) {
            super(in);
        }

        @Override
        public GvDataType getGvDataType() {
            return GvTagList.TYPE;
        }

        @Override
        public String getStringSummary() {
            return "#" + get();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhTag(false);
        }
    }
}
