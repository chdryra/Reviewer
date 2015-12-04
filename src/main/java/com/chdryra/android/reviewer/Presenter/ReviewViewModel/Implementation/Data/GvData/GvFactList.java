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

public class GvFactList extends GvDataListImpl<GvFact> {
    public static final Parcelable.Creator<GvFactList> CREATOR = new Parcelable
            .Creator<GvFactList>() {
        @Override
        public GvFactList createFromParcel(Parcel in) {
            return new GvFactList(in);
        }

        @Override
        public GvFactList[] newArray(int size) {
            return new GvFactList[size];
        }
    };

    //Constructors
    public GvFactList() {
        super(GvFact.TYPE, null);
    }

    public GvFactList(GvReviewId id) {
        super(GvFact.TYPE, id);
    }

    public GvFactList(GvFactList data) {
        super(data);
    }

    public GvFactList(Parcel in) {
        super(in);
    }
}
