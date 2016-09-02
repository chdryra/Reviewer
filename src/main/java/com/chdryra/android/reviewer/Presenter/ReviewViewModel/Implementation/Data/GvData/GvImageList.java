/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Includes methods for adding captions and getting images designated as "covers" which can be
 * used as a background image for a review.
 */
public class GvImageList extends GvDataListParcelable<GvImage> {
    public static final Parcelable.Creator<GvImageList> CREATOR = new Parcelable
            .Creator<GvImageList>() {
        @Override
        public GvImageList createFromParcel(Parcel in) {
            return new GvImageList(in);
        }

        @Override
        public GvImageList[] newArray(int size) {
            return new GvImageList[size];
        }
    };
    private static final Random RANDOM = new Random();

    public GvImageList() {
        super(GvImage.TYPE, null);
    }

    public GvImageList(Parcel in) {
        super(in);
    }

    public GvImageList(GvReviewId id) {
        super(GvImage.TYPE, id);
    }

    public GvImageList(GvImageList data) {
        super(data);
    }

    public GvImage getRandomCover() {
        GvImageList covers = getCovers();
        if (covers.size() == 0) return new GvImage();

        return covers.getItem(RANDOM.nextInt(covers.size()));
    }

    public GvImageList getCovers() {
        GvImageList covers = new GvImageList(getGvReviewId());
        for (GvImage image : this) {
            if (image.isCover()) covers.add(image);
        }

        return covers;
    }

    public boolean contains(Bitmap bitmap) {
        for (GvImage image : this) {
            if (image.getBitmap().sameAs(bitmap)) return true;
        }

        return false;
    }
}
