/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class GvTagList extends GvTextList<GvTag> {
    public static final Parcelable.Creator<GvTagList> CREATOR = new Parcelable
            .Creator<GvTagList>() {
        @Override
        public GvTagList createFromParcel(Parcel in) {
            return new GvTagList(in);
        }

        @Override
        public GvTagList[] newArray(int size) {
            return new GvTagList[size];
        }
    };

    //Constructors
    public GvTagList() {
        super(GvTag.TYPE);
    }

    public GvTagList(Parcel in) {
        super(in);
    }

    public GvTagList(GvReviewId id) {
        super(GvTag.TYPE, id);
    }

    public GvTagList(GvTagList data) {
        super(data);
    }

}
