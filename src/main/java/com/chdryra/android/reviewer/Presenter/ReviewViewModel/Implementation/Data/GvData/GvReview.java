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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewBasicInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewLive;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewOverview;


/**
 * {@link GvData} version of: {@link Review}
 * {@link ViewHolder ): {@link VhReviewOverview }
 */
public class GvReview extends GvDataBasic<GvReview> implements DataReviewBasicInfo{
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
    private GvConverterAuthors mGvConverterAuthor;

    public GvReview() {
        super(GvReview.TYPE);
    }

    public GvReview(Review review,
                    TagsManager tagsManager,
                    GvConverterImages converterImages,
                    GvConverterComments converterComments,
                    GvConverterLocations converterLocations,
                    GvConverterAuthors gvConverterAuthor) {
        super(GvReview.TYPE, new GvReviewId(review.getReviewId()));
        mReview = review;
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mGvConverterAuthor = gvConverterAuthor;
    }

    private GvReview(Parcel in) {
        super(in);
    }

    public Review getReview() {
        return mReview;
    }

    @Override
    public String getSubject() {
        return mReview.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mReview.getRating().getRating();
    }

    @Override
    public DataDate getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhReviewLive(mTagsManager, mConverterImages, mConverterComments,
                mConverterLocations, mGvConverterAuthor);
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
