/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

public class GvUrlList extends GvDataListImpl<GvUrl> {
    public static final Parcelable.Creator<GvUrlList> CREATOR = new Parcelable
            .Creator<GvUrlList>() {
        @Override
        public GvUrlList createFromParcel(Parcel in) {
            return new GvUrlList(in);
        }

        @Override
        public GvUrlList[] newArray(int size) {
            return new GvUrlList[size];
        }
    };

    public GvUrlList() {
        super(GvUrl.TYPE, null);
    }

    public GvUrlList(Parcel in) {
        super(in);
    }

    public GvUrlList(GvReviewId id) {
        super(GvUrl.TYPE, id);
    }

    public GvUrlList(GvUrlList data) {
        super(data);
    }

}
