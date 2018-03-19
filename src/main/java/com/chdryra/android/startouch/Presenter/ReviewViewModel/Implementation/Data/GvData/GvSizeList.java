/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

public class GvSizeList extends GvDataListParcelable<GvSize> {
    public static final Creator<GvSizeList> CREATOR = new Creator<GvSizeList>() {
        @Override
        public GvSizeList createFromParcel(Parcel in) {
            return new GvSizeList(in);
        }

        @Override
        public GvSizeList[] newArray(int size) {
            return new GvSizeList[size];
        }
    };

    public GvSizeList() {
        super(GvSize.TYPE, null);
    }

    public GvSizeList(GvReviewId id) {
        super(GvSize.TYPE, id);
    }

    public GvSizeList(GvSizeList data) {
        super(data);
    }

    public GvSizeList(Parcel in) {
        super(in);
    }

    @Override
    public void sort() {

    }
}
