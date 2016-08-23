/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhReviewLive;


public class GvReview extends GvDataParcelableBasic<GvReview> implements ReviewInfo {
    public static final GvDataType<GvReview> TYPE =
            new GvDataType<>(GvReview.class, "review");
    public static final Creator<GvReview> CREATOR = new Creator<GvReview>() {
        @Override
        public GvReview createFromParcel(Parcel in) {
            return new GvReview(in);
        }

        @Override
        public GvReview[] newArray(int size) {
            return new GvReview[size];
        }
    };

    //Not really parcelable but should not need to parcel this class anyway...
    private Review mReview;
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    public GvReview() {
        super(GvReview.TYPE);
    }

    public GvReview(Review review,
                    TagsManager tagsManager,
                    GvConverterImages converterImages,
                    GvConverterComments converterComments,
                    GvConverterLocations converterLocations) {
        super(GvReview.TYPE, new GvReviewId(review.getReviewId()));
        mReview = review;
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    private GvReview(Parcel in) {
        super(in);
    }

    public Review getReview() {
        return mReview;
    }

    @Override
    public DataSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReview.getRating();
    }

    @Override
    public DataDate getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mReview.getAuthorId();
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhReviewLive(mTagsManager, mConverterImages, mConverterComments,
                mConverterLocations);
    }

    @Override
    public boolean isValidForDisplay() {
        return getGvReviewId() != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(getGvReviewId());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getStringSummary() {
        return getGvReviewId().getStringSummary();
    }
}
