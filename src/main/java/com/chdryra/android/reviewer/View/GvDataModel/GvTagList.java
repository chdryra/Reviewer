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
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.ItemTag;

public class GvTagList extends GvTextList<GvTagList.GvTag> {
    public static final Parcelable.Creator<GvTagList> CREATOR = new Parcelable
            .Creator<GvTagList>() {
        //Overridden
        public GvTagList createFromParcel(Parcel in) {
            return new GvTagList(in);
        }

        public GvTagList[] newArray(int size) {
            return new GvTagList[size];
        }
    };

    //Constructors
    public GvTagList() {
        super(GvTag.TYPE);
    }

    public GvTagList(Parcel in) {
        super(in);
    }

    public GvTagList(GvReviewId id) {
        super(GvTag.TYPE, id);
    }

    public GvTagList(GvTagList data) {
        super(data);
    }

//Classes

    /**
     * {@link } version of: {@link ItemTag}
     * {@link ViewHolder}: {@link VhTag}
     * <p/>
     * <p>
     * Ignores case when comparing tags.
     * </p>
     */
    public static class GvTag extends GvText<GvTag> {
        public static final GvDataType<GvTag> TYPE = new GvDataType<>(GvTag.class, "tag");
        public static final Parcelable.Creator<GvTag> CREATOR = new Parcelable
                .Creator<GvTag>() {
            //Overridden
            public GvTag createFromParcel(Parcel in) {
                return new GvTag(in);
            }

            public GvTag[] newArray(int size) {
                return new GvTag[size];
            }
        };

        //Constructors
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
            this(tag.getGvReviewId(), tag.getString());
        }

        GvTag(Parcel in) {
            super(in);
        }

        //Overridden
        @Override
        public GvDataType<GvTag> getGvDataType() {
            return GvTag.TYPE;
        }

        @Override
        public String getStringSummary() {
            return "#" + getString();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhTag(false);
        }
    }
}
