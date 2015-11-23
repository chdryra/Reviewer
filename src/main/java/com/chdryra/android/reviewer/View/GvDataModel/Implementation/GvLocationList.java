/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

public class GvLocationList extends GvDataListImpl<GvLocation> {
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
