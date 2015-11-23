package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.os.Parcel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;

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

    public GvSubject(GvReviewId id, String subject) {
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
