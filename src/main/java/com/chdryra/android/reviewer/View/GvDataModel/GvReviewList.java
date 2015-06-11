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
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.View.Utils.RatingFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Used for Review summaries in published feed
 *
 * @see Administrator
 */
public class GvReviewList extends GvDataList<GvReviewList.GvReviewOverview> {
    public static final GvDataType TYPE = new GvDataType("review");
    public static final Class<GvReviewOverview> DATA_CLASS = GvReviewOverview.class;

    public GvReviewList() {
        super(null, DATA_CLASS, TYPE);
    }

    public GvReviewList(GvReviewId parentId) {
        super(parentId, DATA_CLASS, TYPE);
    }

    public GvReviewList(GvReviewList data) {
        super(data);
    }

    public void add(String id, Author author, Date publishDate, String subject, float rating,
            Bitmap coverImage, String headline, ArrayList<String> locationNames) {
        if (!contains(id)) {
            add(new GvReviewOverview(super.getReviewId(), id, author, publishDate, subject,
                    rating, coverImage, headline, locationNames));
        }
    }

    @Override
    public boolean contains(GvReviewOverview item) {
        return contains(item.getId());
    }

    @Override
    protected Comparator<GvReviewOverview> getDefaultComparator() {

        return new Comparator<GvReviewOverview>() {
            @Override
            public int compare(GvReviewOverview lhs, GvReviewOverview rhs) {
                return rhs.getPublishDate().compareTo(lhs.getPublishDate());
            }
        };
    }

    private boolean contains(String id) {
        for (GvReviewOverview review : this) {
            if (review.getId().equals(id)) return true;
        }

        return false;
    }

    /**
     * {@link GvData} version of: {@link Review}
     * {@link ViewHolder): {@link VhFeed }
     */
    public static class GvReviewOverview extends GvDataBasic {
        public static final Parcelable.Creator<GvReviewOverview> CREATOR = new Parcelable
                .Creator<GvReviewOverview>() {
            public GvReviewOverview createFromParcel(Parcel in) {
                return new GvReviewOverview(in);
            }

            public GvReviewOverview[] newArray(int size) {
                return new GvReviewOverview[size];
            }
        };

        private String mId;
        private String mSubject;
        private float  mRating;
        private Bitmap mCoverImage;
        private String mHeadline;
        private ArrayList<String> mLocationNames;
        private Author mAuthor;
        private Date   mPublishDate;

        public GvReviewOverview() {
        }

        public GvReviewOverview(String id, Author author, Date publishDate, String subject,
                float rating, Bitmap coverImage, String headline, ArrayList<String> locationNames) {
            mId = id;
            mSubject = subject;
            mRating = rating;
            mCoverImage = coverImage;
            mHeadline = headline;
            mLocationNames = locationNames;
            mAuthor = author;
            mPublishDate = publishDate;
        }

        public GvReviewOverview(GvReviewId parentId, String id, Author author, Date publishDate,
                String subject, float rating, Bitmap coverImage, String headline,
                ArrayList<String> locationNames) {
            super(parentId);
            mId = id;
            mSubject = subject;
            mRating = rating;
            mCoverImage = coverImage;
            mHeadline = headline;
            mLocationNames = locationNames;
            mAuthor = author;
            mPublishDate = publishDate;
        }

        public GvReviewOverview(GvReviewOverview review) {
            this(review.getReviewId(), review.getId(), review.getAuthor(), review.getPublishDate
                    (), review.getSubject(), review.getRating(), review.getCoverImage(), review
                    .getHeadline(), review.mLocationNames);
        }

        private GvReviewOverview(Parcel in) {
            super(in);
            mId = in.readString();
            mSubject = in.readString();
            mRating = in.readFloat();
            mCoverImage = in.readParcelable(Bitmap.class.getClassLoader());
            mHeadline = in.readString();
            mLocationNames = TextUtils.splitString(in.readString(), ",");
            mAuthor = new Author(in.readString(), UserId.fromString(in.readString()));
            mPublishDate = (Date) in.readSerializable();
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VhFeed();
        }

        @Override
        public boolean isValidForDisplay() {
            return DataValidator.validateString(mId) && DataValidator.validateString(mSubject)
                    && DataValidator.validateString(mAuthor.getName()) && DataValidator.NotNull
                    (mPublishDate);
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
            parcel.writeString(mAuthor.getName());
            parcel.writeString(mAuthor.getUserId().toString());
            parcel.writeSerializable(mPublishDate);
        }

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

        public String getHeadline() {
            return mHeadline;
        }

        public Author getAuthor() {
            return mAuthor;
        }

        public Date getPublishDate() {
            return mPublishDate;
        }

        @Override
        public String getStringSummary() {
            DateFormat format = SimpleDateFormat.getDateInstance();
            return getSubject() + ": " + RatingFormatter.outOfFive(getRating()) + "by " +
                    getAuthor().getName() + " on " + format.format(getPublishDate());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GvReviewOverview)) return false;

            GvReviewOverview that = (GvReviewOverview) o;

            if (Float.compare(that.mRating, mRating) != 0) return false;
            if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
            if (mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null)
                return false;
            if (mCoverImage != null ? !mCoverImage.equals(that.mCoverImage) : that.mCoverImage !=
                    null) {
                return false;
            }
            if (mHeadline != null ? !mHeadline.equals(that.mHeadline) : that.mHeadline != null) {
                return false;
            }
            if (mLocationNames != null ? !mLocationNames.equals(that.mLocationNames) : that
                    .mLocationNames != null) {
                return false;
            }
            if (mAuthor != null ? !mAuthor.equals(that.mAuthor) : that.mAuthor != null)
                return false;
            return !(mPublishDate != null ? !mPublishDate.equals(that.mPublishDate) : that
                    .mPublishDate != null);

        }

        @Override
        public int hashCode() {
            int result = mId != null ? mId.hashCode() : 0;
            result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
            result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
            result = 31 * result + (mCoverImage != null ? mCoverImage.hashCode() : 0);
            result = 31 * result + (mHeadline != null ? mHeadline.hashCode() : 0);
            result = 31 * result + (mLocationNames != null ? mLocationNames.hashCode() : 0);
            result = 31 * result + (mAuthor != null ? mAuthor.hashCode() : 0);
            result = 31 * result + (mPublishDate != null ? mPublishDate.hashCode() : 0);
            return result;
        }
    }
}
