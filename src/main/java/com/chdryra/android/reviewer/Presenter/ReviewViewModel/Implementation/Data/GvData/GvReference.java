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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewReference;

public class GvReference extends GvDataBasic<GvReference> implements DataReviewInfo {
    public static final GvDataType<GvReference> TYPE =
            new GvDataType<>(GvReference.class, "review");
    public static final Creator<GvReference> CREATOR = new Creator<GvReference>() {
        @Override
        public GvReference createFromParcel(Parcel in) {
            return new GvReference(in);
        }

        @Override
        public GvReference[] newArray(int size) {
            return new GvReference[size];
        }
    };

    //Not really parcelable but should not need to parcel this class anyway...
    private ReviewReference mReference;
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    public GvReference() {
        super(GvReference.TYPE);
    }

    public GvReference(ReviewReference reference,
                       TagsManager tagsManager,
                       GvConverterImages converterImages,
                       GvConverterComments converterComments,
                       GvConverterLocations converterLocations) {
        super(GvReference.TYPE, new GvReviewId(reference.getReviewId()));
        mReference = reference;
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    private GvReference(Parcel in) {
        super(in);
    }

    public ReviewReference getReference() {
        return mReference;
    }

    @Override
    public DataSubject getSubject() {
        return mReference.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReference.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mReference.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReference.getPublishDate();
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhReviewReference(mTagsManager, mConverterImages, mConverterComments, mConverterLocations);
    }

    @Override
    public boolean isValidForDisplay() {
        return getGvReviewId() != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mReference.isValidReference();
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
