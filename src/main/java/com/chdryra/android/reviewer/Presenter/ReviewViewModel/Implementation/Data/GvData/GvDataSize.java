/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataSize extends GvDualText {
    public static final GvDataType<GvDataSize> TYPE = new GvDataType<>(GvDataSize.class, "size");
    public static final Creator<GvDataSize> CREATOR = new Creator<GvDataSize>() {
        @Override
        public GvDataSize createFromParcel(Parcel in) {
            return new GvDataSize(in);
        }

        @Override
        public GvDataSize[] newArray(int size) {
            return new GvDataSize[size];
        }
    };

    private static final String PLACEHOLDER = "--";

    public GvDataSize() {
    }

    public GvDataSize(GvReviewId id, GvDataType<?> type, int size) {
        super(id, String.valueOf(size), size == 1 ? type.getDatumName() : type.getDataName());
    }

    public GvDataSize(GvReviewId id, GvDataType<?> type) {
        super(id, PLACEHOLDER, type.getDataName());
    }

    public GvDataSize(Parcel in) {
        super(in);
    }
}
