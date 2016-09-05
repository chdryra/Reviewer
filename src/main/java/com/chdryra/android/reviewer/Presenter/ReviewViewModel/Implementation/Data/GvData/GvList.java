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

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvList extends GvDataListParcelable<GvDataParcelable> {
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

    public static final GvDataType<GvDataParcelable> TYPE =
            new GvDataType<>(GvDataParcelable.class, "Review Data", "Review Data");

    public GvList() {
        super(TYPE, null);
    }

    private GvList(Parcel in) {
        super(in);
    }

    public GvList(GvReviewId id) {
        super(TYPE, id);
    }

    public GvList(GvList data) {
        super(data);
    }

    @Override
    public void sort() {
    }
}
