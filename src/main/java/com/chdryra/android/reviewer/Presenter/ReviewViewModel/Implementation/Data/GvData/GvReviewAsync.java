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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterDateReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewOverview;

/**
 * {@link GvData} version of: {@link Review}
 * {@link ViewHolder ): {@link VhReviewOverview }
 */
public class GvReviewAsync extends GvDataBasic<GvReviewAsync> {
    public static final GvDataType<GvReviewAsync> TYPE =
            new GvDataType<>(GvReviewAsync.class, "reviewAsync");
    public static final Creator<GvReviewAsync> CREATOR = new Creator<GvReviewAsync>() {
        @Override
        public GvReviewAsync createFromParcel(Parcel in) {
            return new GvReviewAsync(in);
        }

        @Override
        public GvReviewAsync[] newArray(int size) {
            return new GvReviewAsync[size];
        }
    };

    //Not really parcelable but should not need to parcel this class anyway...
    private ReviewsRepository mRepo;
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private GvConverterDateReviews mConverterDate;
    private GvConverterAuthors mGvConverterAuthor;

    public GvReviewAsync() {
        super(GvReviewAsync.TYPE);
    }

    public GvReviewAsync(GvReviewId reviewId,
                         ReviewsRepository repo,
                         TagsManager tagsManager,
                         GvConverterImages converterImages,
                         GvConverterComments converterComments,
                         GvConverterLocations converterLocations,
                         GvConverterDateReviews converterDate,
                         GvConverterAuthors gvConverterAuthor) {
        super(GvReviewAsync.TYPE, reviewId);
        mRepo = repo;
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mConverterDate = converterDate;
        mGvConverterAuthor = gvConverterAuthor;
    }

    private GvReviewAsync(Parcel in) {
        super(in);
    }

    public void getReview(CallbackRepository callback) {
        mRepo.getReview(getReviewId(), callback);
    }

    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    public GvConverterImages getConverterImages() {
        return mConverterImages;
    }

    public GvConverterComments getConverterComments() {
        return mConverterComments;
    }

    public GvConverterLocations getConverterLocations() {
        return mConverterLocations;
    }

    public GvConverterDateReviews getConverterDate() {
        return mConverterDate;
    }

    public GvConverterAuthors getGvConverterAuthor() {
        return mGvConverterAuthor;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhReviewAsync();
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
