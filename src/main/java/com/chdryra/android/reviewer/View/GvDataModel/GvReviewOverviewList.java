/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.View.Utils.RatingFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Used for Review summaries in published feed
 *
 * @see ApplicationInstance
 */
public class GvReviewOverviewList extends GvDataList<GvReviewOverviewList.GvReviewOverview> {
    public static final Parcelable.Creator<GvReviewOverviewList> CREATOR = new Parcelable
            .Creator<GvReviewOverviewList>() {
        //Overridden
        public GvReviewOverviewList createFromParcel(Parcel in) {
            return new GvReviewOverviewList(in);
        }

        public GvReviewOverviewList[] newArray(int size) {
            return new GvReviewOverviewList[size];
        }
    };
    //Constructors
    public GvReviewOverviewList() {
        super(GvReviewOverview.TYPE, null);
    }

    public GvReviewOverviewList(Parcel in) {
        super(in);
    }

    public GvReviewOverviewList(GvReviewId parentId) {
        super(GvReviewOverview.TYPE, parentId);
    }

    public GvReviewOverviewList(GvReviewOverviewList data) {
        super(data);
    }

    private boolean contains(String id) {
        for (GvReviewOverview review : this) {
            if (review.getId().equals(id)) return true;
        }

        return false;
    }

    //Overridden
    public void add(GvReviewOverview overview) {
        if (!contains(overview.getId())) super.add(overview);
    }

    @Override
    public boolean contains(GvReviewOverview item) {
        return contains(item.getId());
    }

//Classes

    /**
     * {@link GvData} version of: {@link Review}
     * {@link ViewHolder): {@link VhReviewOverview }
     */
    public static class GvReviewOverview extends GvDataBasic<GvReviewOverview> {
        public static final GvDataType<GvReviewOverview> TYPE =
                new GvDataType<>(GvReviewOverview.class, "review");
        public static final Parcelable.Creator<GvReviewOverview> CREATOR = new Parcelable
                .Creator<GvReviewOverview>() {
            //Overridden
            public GvReviewOverview createFromParcel(Parcel in) {
                return new GvReviewOverview(in);
            }

            public GvReviewOverview[] newArray(int size) {
                return new GvReviewOverview[size];
            }
        };

        private String mId;
        private String mSubject;
        private float mRating;
        private Bitmap mCoverImage;
        private String mHeadline;
        private ArrayList<String> mLocationNames;
        private ArrayList<String> mTags;
        private GvAuthorList.GvAuthor mAuthor;
        private GvDateList.GvDate mPublishDate;

        //Constructors
        public GvReviewOverview() {
            super(GvReviewOverview.TYPE);
        }

        public GvReviewOverview(String id, GvAuthorList.GvAuthor author,
                                GvDateList.GvDate publishDate, String subject,
                                float rating, Bitmap coverImage, String headline,
                                ArrayList<String> locationNames,
                                ArrayList<String> tags) {
            this(null, id, author, publishDate, subject, rating, coverImage, headline,
                    locationNames, tags);
        }

        public GvReviewOverview(GvReviewId parentId, String id, GvAuthorList.GvAuthor author,
                                GvDateList.GvDate publishDate,
                                String subject, float rating, Bitmap coverImage, String headline,
                                ArrayList<String> locationNames, ArrayList<String> tags) {
            super(GvReviewOverview.TYPE, parentId);
            mId = id;
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
            this(review.getGvReviewId(), review.getId(), review.getAuthor(), review.getPublishDate
                    (), review.getSubject(), review.getRating(), review.getCoverImage(), review
                    .getHeadline(), review.mLocationNames, review.mTags);
        }

        private GvReviewOverview(Parcel in) {
            super(in);
            mId = in.readString();
            mSubject = in.readString();
            mRating = in.readFloat();
            mCoverImage = in.readParcelable(Bitmap.class.getClassLoader());
            mHeadline = in.readString();
            mLocationNames = TextUtils.splitString(in.readString(), ",");
            mTags = TextUtils.splitString(in.readString(), ",");
            mAuthor = in.readParcelable(GvAuthorList.GvAuthor.class.getClassLoader());
            mPublishDate = in.readParcelable(GvDateList.GvDate.class.getClassLoader());
        }

        //public methods
        public String getId() {
            return mId;
        }

        public String getSubject() {
            return mSubject;
        }

        public float getRating() {
            return mRating;
        }

        public Bitmap getCoverImage() {
            return mCoverImage;
        }

        public String getLocationString() {
            String location = null;
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

        public ArrayList<String> getTags() {
            return mTags;
        }

        public String getHeadline() {
            return mHeadline;
        }

        public GvAuthorList.GvAuthor getAuthor() {
            return mAuthor;
        }

        public GvDateList.GvDate getPublishDate() {
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
            return dataValidator.validateString(mId) && dataValidator.validateString(mSubject)
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
            parcel.writeString(mId);
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
            if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
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
            result = 31 * result + (mId != null ? mId.hashCode() : 0);
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
}
