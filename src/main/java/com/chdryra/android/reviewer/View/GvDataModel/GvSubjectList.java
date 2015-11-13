/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSubjectList extends GvTextList<GvSubjectList.GvSubject> {
    public static final Parcelable.Creator<GvSubjectList> CREATOR = new Parcelable
            .Creator<GvSubjectList>() {
        //Overridden
        public GvSubjectList createFromParcel(Parcel in) {
            return new GvSubjectList(in);
        }

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

    //Classes
    public static class GvSubject extends GvText<GvSubject> implements DataSubject {
        public static final GvDataType<GvSubject> TYPE =
                new GvDataType<>(GvSubject.class, "subject");
        public static final Parcelable.Creator<GvSubject> CREATOR = new Parcelable
                .Creator<GvSubject>() {
            //Overridden
            public GvSubject createFromParcel(Parcel in) {
                return new GvSubject(in);
            }

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
}
