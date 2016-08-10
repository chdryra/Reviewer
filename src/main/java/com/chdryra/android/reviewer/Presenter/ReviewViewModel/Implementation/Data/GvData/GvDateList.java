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

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDateList extends GvDataListParcelable<GvDate> {
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
