/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GvSubject extends GvText<GvSubject> implements DataSubject {
    public static final GvDataType<GvSubject> TYPE =
            new GvDataType<>(GvSubject.class, "subject");
    public static final Creator<GvSubject> CREATOR = new Creator<GvSubject>() {
        @Override
        public GvSubject createFromParcel(Parcel in) {
            return new GvSubject(in);
        }

        @Override
        public GvSubject[] newArray(int size) {
            return new GvSubject[size];
        }
    };

    //Constructors
    public GvSubject() {
        super(TYPE);
    }

    public GvSubject(String subject) {
        super(TYPE, subject);
    }

    public GvSubject(@Nullable GvReviewId id, String subject) {
        super(TYPE, id, subject);
    }

    public GvSubject(GvSubject subject) {
        this(new GvReviewId(subject.getReviewId()), subject.getString());
    }

    GvSubject(Parcel in) {
        super(in);
    }

    @Override
    public String getSubject() {
        return getString();
    }
}
