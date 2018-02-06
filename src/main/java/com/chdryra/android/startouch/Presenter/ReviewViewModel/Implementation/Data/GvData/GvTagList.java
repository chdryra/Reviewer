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
