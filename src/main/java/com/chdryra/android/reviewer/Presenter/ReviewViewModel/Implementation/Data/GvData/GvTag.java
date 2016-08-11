/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhTag;

/**
 * {@link } version of: {@link ItemTag}
 * {@link ViewHolder}: {@link VhTag}
 * <p/>
 * <p>
 * Ignores case when comparing tags.
 * </p>
 */
public class GvTag extends GvText<GvTag> implements DataTag {
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

    public GvTag() {
        super(TYPE);
    }

    public GvTag(String tag) {
        super(TYPE, process(tag));
    }

    private static String process(String string) {
        return TextUtils.toCamelCase(string.replaceAll("[^A-Za-z0-9 ]", ""));
    }

    public GvTag(@Nullable GvReviewId id, String tag) {
        super(TYPE, id, process(tag));
    }

    public GvTag(GvTag tag) {
        this(tag.getGvReviewId(), tag.getString());
    }

    GvTag(Parcel in) {
        super(in);
    }

    @Override
    public GvDataType<GvTag> getGvDataType() {
        return GvTag.TYPE;
    }

    @Override
    public String getTag() {
        return getString();
    }

    @Override
    public String getStringSummary() {
        return "#" + getString();
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhTag();
    }

    public static class Reference extends GvDataRef<Reference, DataTag, VhTag> {
        public static final GvDataType<GvTag.Reference> TYPE
                = new GvDataType<>(GvTag.Reference.class, GvTag.TYPE);

        public Reference(ReviewItemReference<DataTag> reference,
                         DataConverter<DataTag, GvTag, ?> converter) {
            super(TYPE, reference, converter, VhTag.class, new PlaceHolderFactory<DataTag>() {
                @Override
                public DataTag newPlaceHolder(String placeHolder) {
                    return new GvTag(placeHolder);
                }
            });
        }
    }
}
