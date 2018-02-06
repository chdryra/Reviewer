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
 * Includes method for generating split comments {@link GvCommentList} from current list.
 */
public class GvCommentList extends GvDataListParcelable<GvComment> {
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

    public GvCommentList() {
        super(GvComment.TYPE, new GvReviewId());
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

    public GvCommentList getSplitComments() {
        GvCommentList splitComments = new GvCommentList(getGvReviewId());
        for (GvComment comment : this) {
            splitComments.addAll(comment.getSplitComments());
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
