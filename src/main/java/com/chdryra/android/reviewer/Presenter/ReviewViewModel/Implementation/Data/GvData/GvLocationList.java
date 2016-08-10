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

public class GvLocationList extends GvDataListParcelable<GvLocation> {
    public static final Parcelable.Creator<GvLocationList> CREATOR = new Parcelable
            .Creator<GvLocationList>() {
        @Override
        public GvLocationList createFromParcel(Parcel in) {
            return new GvLocationList(in);
        }

        @Override
        public GvLocationList[] newArray(int size) {
            return new GvLocationList[size];
        }
    };

    //Constructors
    public GvLocationList() {
        super(GvLocation.TYPE, null);
    }

    public GvLocationList(Parcel in) {
        super(in);
    }

    public GvLocationList(GvReviewId id) {
        super(GvLocation.TYPE, id);
    }

    public GvLocationList(GvLocationList data) {
        super(data);
    }
}
