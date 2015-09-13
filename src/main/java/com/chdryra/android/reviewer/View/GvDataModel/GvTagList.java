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

public class GvTagList extends GvTextList<GvTagList.GvTag> {
    public GvTagList() {
        super(GvTag.TYPE);
    }

    public GvTagList(GvReviewId id) {
        super(GvTag.TYPE, id);
    }

    public GvTagList(GvTagList data) {
        super(data);
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
        public static final GvDataType<GvTag> TYPE = new GvDataType<>(GvTag.class, "tag");
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
            super(TYPE);
        }

        public GvTag(String tag) {
            super(TYPE, tag);
        }

        public GvTag(GvReviewId id, String tag) {
            super(TYPE, id, tag);
        }

        public GvTag(GvTag tag) {
            this(tag.getReviewId(), tag.get());
        }

        GvTag(Parcel in) {
            super(in);
        }

        @Override
        public GvDataType<GvTag> getGvDataType() {
            return GvTag.TYPE;
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
