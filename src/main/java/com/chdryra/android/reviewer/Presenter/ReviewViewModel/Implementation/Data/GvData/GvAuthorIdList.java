/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorIdList extends GvDataListParcelable<GvAuthorId> {
    public static final Creator<GvAuthorIdList> CREATOR = new Creator<GvAuthorIdList>() {
        @Override
        public GvAuthorIdList createFromParcel(Parcel in) {
            return new GvAuthorIdList(in);
        }

        @Override
        public GvAuthorIdList[] newArray(int size) {
            return new GvAuthorIdList[size];
        }
    };

    //Constructors
    public GvAuthorIdList() {
        super(GvAuthorId.TYPE, null);
    }

    public GvAuthorIdList(Parcel in) {
        super(in);
    }

    public GvAuthorIdList(GvReviewId id) {
        super(GvAuthorId.TYPE, id);
    }

    public GvAuthorIdList(GvAuthorIdList data) {
        super(data);
    }
}

