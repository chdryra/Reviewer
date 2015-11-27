/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorList extends GvDataListImpl<GvAuthor> {
    public static final Parcelable.Creator<GvAuthorList> CREATOR = new Parcelable
            .Creator<GvAuthorList>() {
        //Overridden
        public GvAuthorList createFromParcel(Parcel in) {
            return new GvAuthorList(in);
        }

        public GvAuthorList[] newArray(int size) {
            return new GvAuthorList[size];
        }
    };

    //Constructors
    public GvAuthorList() {
        super(GvAuthor.TYPE, null);
    }

    public GvAuthorList(Parcel in) {
        super(in);
    }

    public GvAuthorList(GvReviewId id) {
        super(GvAuthor.TYPE, id);
    }

    public GvAuthorList(GvAuthorList data) {
        super(data);
    }

//Classes

}

