/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvList extends GvDataListImpl<GvData> {
    public static final Parcelable.Creator<GvList> CREATOR = new Parcelable
            .Creator<GvList>() {
        @Override
        public GvList createFromParcel(Parcel in) {
            return new GvList(in);
        }

        @Override
        public GvList[] newArray(int size) {
            return new GvList[size];
        }
    };

    public static final GvDataType<GvData> TYPE =
            new GvDataType<>(GvData.class, "Review Data", "Review Data");

    //Constructors
    public GvList() {
        super(TYPE, null);
    }

    public GvList(Parcel in) {
        super(in);
    }

    public GvList(GvReviewId id) {
        super(TYPE, id);
    }

    public GvList(GvList data) {
        super(data);
    }
}
