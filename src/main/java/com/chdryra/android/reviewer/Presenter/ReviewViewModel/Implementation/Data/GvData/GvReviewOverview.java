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
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhReviewOverview;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * {@link GvData} version of: {@link Review}
 * {@link ViewHolder ): {@link VhReviewOverview }
 */
public class GvReviewOverview extends GvDataBasic<GvReviewOverview> implements DataReviewSummary {
    public static final GvDataType<GvReviewOverview> TYPE =
            new GvDataType<>(GvReviewOverview.class, "review");
    public static final Creator<GvReviewOverview> CREATOR = new Creator<GvReviewOverview>() {
        @Override
        public GvReviewOverview createFromParcel(Parcel in) {
            return new GvReviewOverview(in);
        }

        @Override
        public GvReviewOverview[] newArray(int size) {
            return new GvReviewOverview[size];
        }
    };

    private GvReviewId mParentId;
    private String mSubject;
    private float mRating;
    private Bitmap mCoverImage;
    private String mHeadline;
    private ArrayList<String> mLocationNames;
    private ArrayList<String> mTags;
    private GvAuthor mAuthor;
    private GvDate mPublishDate;

    public GvReviewOverview() {
        super(GvReviewOverview.TYPE);
    }

    public GvReviewOverview(@Nullable GvReviewId parentId, GvReviewId reviewId, GvAuthor author,
                            GvDate publishDate,
                            String subject, float rating, @Nullable Bitmap coverImage, 
                            @Nullable String headline,
                            ArrayList<String> locationNames, ArrayList<String> tags) {
        super(GvReviewOverview.TYPE, reviewId);
        mParentId = parentId;
        mSubject = subject;
        mRating = rating;
        mCoverImage = coverImage;
        mHeadline = headline;
        mLocationNames = locationNames;
        mTags = tags;
        mAuthor = author;
        mPublishDate = publishDate;
    }

    public GvReviewOverview(GvReviewOverview review) {
        this(review.getParentGvReviewId(), review.getGvReviewId(), review.getAuthor(), review.getPublishDate
                (), review.getSubject(), review.getRating(), review.getCoverImage(), review
                .getHeadline(), review.mLocationNames, review.mTags);
    }

    private GvReviewOverview(Parcel in) {
        super(in);
        mParentId = in.readParcelable(GvReviewId.class.getClassLoader());
        mSubject = in.readString();
        mRating = in.readFloat();
        mCoverImage = in.readParcelable(Bitmap.class.getClassLoader());
        mHeadline = in.readString();
        mLocationNames = TextUtils.splitString(in.readString(), ",");
        mTags = TextUtils.splitString(in.readString(), ",");
        mAuthor = in.readParcelable(GvAuthor.class.getClassLoader());
        mPublishDate = in.readParcelable(GvDate.class.getClassLoader());
    }

    public GvReviewId getParentGvReviewId() {
        return mParentId;
    }


    @Override
    public ReviewId getParentId() {
        return new GvReviewId(mParentId);
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public Bitmap getCoverImage() {
        return mCoverImage;
    }

    @Override
    public String getLocationString() {
        String location = "";
        int locs = mLocationNames.size();
        if (locs > 0) {
            location = mLocationNames.get(0);
            if (locs > 1) {
                String loc = locs == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(mLocationNames.size() - 1) + loc;
            }
        }

        return location;
    }

    @Override
    public ArrayList<String> getTags() {
        return mTags;
    }

    @Override
    public String getHeadline() {
        return mHeadline;
    }

    @Override
    public GvAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public GvDate getPublishDate() {
        return mPublishDate;
    }

    //Overridden
    @Override
    public ViewHolder getViewHolder() {
        return new VhReviewOverview();
    }

    @Override
    public boolean isValidForDisplay() {
        String name = mAuthor.getName();
        return mSubject != null && mSubject.length() > 0 && mPublishDate != null &&
                name != null && name.length() > 0 && mTags != null && mTags.size() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(getParentId()) && dataValidator.validateString(mSubject)
                && dataValidator.validateString(mAuthor.getName()) && dataValidator.NotNull
                (mPublishDate) && mTags != null && mTags.size() > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(mParentId, i);
        parcel.writeString(mSubject);
        parcel.writeFloat(mRating);
        parcel.writeParcelable(mCoverImage, i);
        parcel.writeString(mHeadline);
        parcel.writeString(TextUtils.commaDelimited(mLocationNames));
        parcel.writeString(TextUtils.commaDelimited(mTags));
        parcel.writeParcelable(mAuthor, i);
        parcel.writeParcelable(mPublishDate, i);
    }

    //ignoring cover image as randomly chosen so screws up equality when comparing two
    // generated GvReviewLists...
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvReviewOverview)) return false;
        if (!super.equals(o)) return false;

        GvReviewOverview that = (GvReviewOverview) o;

        if (Float.compare(that.mRating, mRating) != 0) return false;
        if (mParentId != null ? !mParentId.equals(that.mParentId) : that.mParentId != null) return false;
        if (mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null)
            return false;
        if (mHeadline != null ? !mHeadline.equals(that.mHeadline) : that.mHeadline != null)
            return false;
        if (mLocationNames != null ? !mLocationNames.equals(that.mLocationNames) : that
                .mLocationNames != null)
            return false;
        if (mTags != null ? !mTags.equals(that.mTags) : that
                .mTags != null)
            return false;
        if (mAuthor != null ? !mAuthor.equals(that.mAuthor) : that.mAuthor != null)
            return false;
        return !(mPublishDate != null ? !mPublishDate.equals(that.mPublishDate) : that
                .mPublishDate != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mParentId != null ? mParentId.hashCode() : 0);
        result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
        result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + (mCoverImage != null ? mCoverImage.hashCode() : 0);
        result = 31 * result + (mHeadline != null ? mHeadline.hashCode() : 0);
        result = 31 * result + (mLocationNames != null ? mLocationNames.hashCode() : 0);
        result = 31 * result + (mTags != null ? mTags.hashCode() : 0);
        result = 31 * result + (mAuthor != null ? mAuthor.hashCode() : 0);
        result = 31 * result + (mPublishDate != null ? mPublishDate.hashCode() : 0);
        return result;
    }

    @Override
    public String getStringSummary() {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return getSubject() + ": " + RatingFormatter.outOfFive(getRating()) + "by " +
                getAuthor().getName() + " on " + format.format(getPublishDate());
    }
}
