package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.ItemTag;

/**
 * {@link } version of: {@link ItemTag}
 * {@link ViewHolder}: {@link VhTag}
 * <p/>
 * <p>
 * Ignores case when comparing tags.
 * </p>
 */
public class GvTag extends GvText<GvTag> {
    public static final GvDataType<GvTag> TYPE = new GvDataType<>(GvTag.class, "tag");
    public static final Creator<GvTag> CREATOR = new Creator<GvTag>() {
        @Override
        public GvTag createFromParcel(Parcel in) {
            return new GvTag(in);
        }

        @Override
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