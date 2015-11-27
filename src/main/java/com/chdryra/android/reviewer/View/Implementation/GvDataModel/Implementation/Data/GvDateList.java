/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDateList extends GvDataListImpl<GvDate> {
    public static final Parcelable.Creator<GvDateList> CREATOR = new Parcelable
            .Creator<GvDateList>() {
        @Override
        public GvDateList createFromParcel(Parcel in) {
            return new GvDateList(in);
        }

        @Override
        public GvDateList[] newArray(int size) {
            return new GvDateList[size];
        }
    };

    //Constructors
    public GvDateList() {
        super(GvDate.TYPE, null);
    }

    public GvDateList(Parcel in) {
        super(in);
    }

    public GvDateList(GvReviewId id) {
        super(GvDate.TYPE, id);
    }

    public GvDateList(GvDateList data) {
        super(data);
    }
}
