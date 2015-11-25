/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTextList<T extends GvText> extends GvDataListImpl<T> {
    public static final Parcelable.Creator<GvTextList> CREATOR = new Parcelable
            .Creator<GvTextList>() {
        //Overridden
        public GvTextList createFromParcel(Parcel in) {
            return new GvTextList(in);
        }

        public GvTextList[] newArray(int size) {
            return new GvTextList[size];
        }
    };

    private static final GvDataType<GvTextList> TYPE
            = GvTypeMaker.newType(GvTextList.class, GvText.TYPE);

    //Constructors
    public GvTextList(Parcel in)  {
        super(in);
    }

    public GvTextList(GvDataType<T> type) {
        this(type, null);
    }

    public GvTextList(GvDataType<T> type, GvReviewId id) {
        super(type, id);
    }

    public <T2 extends GvTextList<T>> GvTextList(T2 data) {
        super(data);
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> strings = new ArrayList<>();
        for (T tag : this) {
            strings.add(tag.getString());
        }

        return strings;
    }
}
