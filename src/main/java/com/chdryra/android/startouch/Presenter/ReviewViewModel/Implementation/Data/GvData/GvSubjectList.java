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
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSubjectList extends GvTextList<GvSubject> {
    public static final Parcelable.Creator<GvSubjectList> CREATOR = new Parcelable
            .Creator<GvSubjectList>() {
        @Override
        public GvSubjectList createFromParcel(Parcel in) {
            return new GvSubjectList(in);
        }

        @Override
        public GvSubjectList[] newArray(int size) {
            return new GvSubjectList[size];
        }
    };

    //Constructors
    public GvSubjectList() {
        super(GvSubject.TYPE, null);
    }

    public GvSubjectList(Parcel in) {
        super(in);
    }

    public GvSubjectList(GvReviewId id) {
        super(GvSubject.TYPE, id);
    }

    public GvSubjectList(GvSubjectList data) {
        super(data);
    }
}
