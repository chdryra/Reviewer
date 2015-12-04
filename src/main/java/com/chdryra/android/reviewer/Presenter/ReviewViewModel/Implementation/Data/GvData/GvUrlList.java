/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
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

    //Constructors
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
