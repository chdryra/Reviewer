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
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorList extends GvDataListParcelable<GvAuthorName> {
    public static final Parcelable.Creator<GvAuthorList> CREATOR = new Parcelable
            .Creator<GvAuthorList>() {
        @Override
        public GvAuthorList createFromParcel(Parcel in) {
            return new GvAuthorList(in);
        }

        @Override
        public GvAuthorList[] newArray(int size) {
            return new GvAuthorList[size];
        }
    };

    //Constructors
    public GvAuthorList() {
        super(GvAuthorName.TYPE, new GvReviewId());
    }

    public GvAuthorList(Parcel in) {
        super(in);
    }

    public GvAuthorList(GvReviewId id) {
        super(GvAuthorName.TYPE, id);
    }

    public GvAuthorList(GvAuthorList data) {
        super(data);
    }

//Classes

}

