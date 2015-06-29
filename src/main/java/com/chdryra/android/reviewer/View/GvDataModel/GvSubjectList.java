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

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSubjectList extends GvTextList<GvSubjectList.GvSubject> {
    public static final GvDataType<GvSubjectList> TYPE
            = GvTypeMaker.newType(GvSubjectList.class, GvSubject.TYPE);

    public GvSubjectList() {
        super(GvSubject.class, TYPE, null);
    }

    public GvSubjectList(GvReviewId id) {
        super(GvSubject.class, TYPE, id);
    }

    public GvSubjectList(GvSubjectList data) {
        super(data);
    }

    public static class GvSubject extends GvText {
        public static final GvDataType<GvSubject> TYPE =
                GvTypeMaker.newType(GvSubject.class, "subject");
        public static final Parcelable.Creator<GvSubject> CREATOR = new Parcelable
                .Creator<GvSubject>() {
            public GvSubject createFromParcel(Parcel in) {
                return new GvSubject(in);
            }

            public GvSubject[] newArray(int size) {
                return new GvSubject[size];
            }
        };

        public GvSubject() {
            super();
        }

        public GvSubject(String subject) {
            super(subject);
        }

        public GvSubject(GvReviewId id, String subject) {
            super(id, subject);
        }

        public GvSubject(GvSubject subject) {
            this(subject.getReviewId(), subject.get());
        }

        GvSubject(Parcel in) {
            super(in);
        }

        @Override
        public GvDataType<GvSubject> getGvDataType() {
            return GvSubject.TYPE;
        }
    }
}
