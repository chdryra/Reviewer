/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Includes method for generating split comments {@link GvCommentList} from current list.
 */
public class GvCommentList extends GvDataListImpl<GvComment> {
    public static final Parcelable.Creator<GvCommentList> CREATOR = new Parcelable
            .Creator<GvCommentList>() {
        @Override
        public GvCommentList createFromParcel(Parcel in) {
            return new GvCommentList(in);
        }

        @Override
        public GvCommentList[] newArray(int size) {
            return new GvCommentList[size];
        }
    };

    //Constructors
    public GvCommentList() {
        super(GvComment.TYPE, null);
    }

    public GvCommentList(GvReviewId id) {
        super(GvComment.TYPE, id);
    }

    public GvCommentList(GvCommentList data) {
        super(data);
    }

    public GvCommentList(Parcel in) {
        super(in);
    }

    //public methods
    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList(getGvReviewId());
        for (GvComment comment : this) {
            splitComments.addList(comment.getSplitComments());
        }

        return splitComments;
    }

    public GvCommentList getHeadlines() {
        GvCommentList headlines = new GvCommentList(getGvReviewId());
        for (GvComment comment : this) {
            if (comment.isHeadline()) headlines.add(comment);
        }

        return headlines;
    }
}
