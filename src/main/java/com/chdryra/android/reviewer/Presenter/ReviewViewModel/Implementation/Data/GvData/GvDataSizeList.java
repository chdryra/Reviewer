/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

public class GvDataSizeList extends GvDataListImpl<GvDataSize> {
    public static final Creator<GvDataSizeList> CREATOR = new Creator<GvDataSizeList>() {
        @Override
        public GvDataSizeList createFromParcel(Parcel in) {
            return new GvDataSizeList(in);
        }

        @Override
        public GvDataSizeList[] newArray(int size) {
            return new GvDataSizeList[size];
        }
    };

    public GvDataSizeList() {
        super(GvDataSize.TYPE, null);
    }

    public GvDataSizeList(GvReviewId id) {
        super(GvDataSize.TYPE, id);
    }

    public GvDataSizeList(GvDataSizeList data) {
        super(data);
    }

    public GvDataSizeList(Parcel in) {
        super(in);
    }

    @Override
    public void sort() {
        
    }
}
