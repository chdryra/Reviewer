/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTextList<T extends GvText> extends GvDataListParcelable<T> {
    public static final Parcelable.Creator<GvTextList> CREATOR = new Parcelable
            .Creator<GvTextList>() {
        @Override
        public GvTextList createFromParcel(Parcel in) {
            return new GvTextList(in);
        }

        @Override
        public GvTextList[] newArray(int size) {
            return new GvTextList[size];
        }
    };

    GvTextList(Parcel in)  {
        super(in);
    }

    GvTextList(GvDataType<T> type) {
        this(type, null);
    }

    GvTextList(GvDataType<T> type, GvReviewId id) {
        super(type, id);
    }

    <T2 extends GvTextList<T>> GvTextList(T2 data) {
        super(data);
    }
}
