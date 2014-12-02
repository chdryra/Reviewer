/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;
import java.util.Date;

/**
 * Used for Review summaries in published feed
 *
 * @see com.chdryra.android.reviewer.Administrator
 * @see com.chdryra.android.reviewer.FragmentFeed
 */
public class GVReviewOverviewList extends GVReviewDataList<GVReviewOverviewList.GVReviewOverview> {

    GVReviewOverviewList() {
        super(GVType.REVIEW);
    }

    @Override
    protected Comparator<GVReviewOverview> getDefaultComparator() {

        return new Comparator<GVReviewOverviewList.GVReviewOverview>() {
            @Override
            public int compare(GVReviewOverview lhs, GVReviewOverview rhs) {
                return lhs.getPublishDate().compareTo(rhs.getPublishDate());
            }
        };
    }

    void add(String id, String subject, float rating, Bitmap coverImage, String headline,
            String locationName, String author, Date publishDate) {
        if (!contains(id)) {
            add(new GVReviewOverview(id, subject, rating, coverImage, headline, locationName,
                    author, publishDate));
        }
    }

    boolean contains(String id) {
        GVReviewOverview review = new GVReviewOverview(id);
        return contains(review);
    }

    /**
     * {@link GVReviewData} version of: {@link Review}
     * {@link ViewHolder): {@link VHReviewNodeOverview}
     */
    static class GVReviewOverview implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVReviewOverview> CREATOR = new Parcelable
                .Creator<GVReviewOverview>() {
            public GVReviewOverview createFromParcel(Parcel in) {
                return new GVReviewOverview(in);
            }

            public GVReviewOverview[] newArray(int size) {
                return new GVReviewOverview[size];
            }
        };
        private final String mId;
        private       String mSubject;
        private       float  mRating;
        private       Bitmap mCoverImage;
        private       String mHeadline;
        private       String mLocationName;
        private       String mAuthor;
        private       Date   mPublishDate;

        private GVReviewOverview(String id) {
            mId = id;
        }

        GVReviewOverview(String id, String subject, float rating, Bitmap coverImage,
                String headline, String locationName, String Author,
                Date publishDate) {
            mId = id;
            mSubject = subject;
            mRating = rating;
            mCoverImage = coverImage;
            mHeadline = headline;
            mLocationName = locationName;
            mAuthor = Author;
            mPublishDate = publishDate;
        }

        GVReviewOverview(Parcel in) {
            mId = in.readString();
            mSubject = in.readString();
            mRating = in.readFloat();
            mCoverImage = in.readParcelable(Bitmap.class.getClassLoader());
            mHeadline = in.readString();
            mLocationName = in.readString();
            mAuthor = in.readString();
            mPublishDate = (Date) in.readSerializable();
        }

        @Override
        public ViewHolder newViewHolder() {
            return new VHReviewNodeOverview();
        }

        @Override
        public boolean isValidForDisplay() {
            return mId != null && mId.length() > 0 && mSubject != null && mSubject.length() > 0
                    && mAuthor != null && mAuthor.length() > 0 && mPublishDate != null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(mId);
            parcel.writeString(mSubject);
            parcel.writeFloat(mRating);
            parcel.writeParcelable(mCoverImage, i);
            parcel.writeString(mHeadline);
            parcel.writeString(mLocationName);
            parcel.writeString(mAuthor);
            parcel.writeSerializable(mPublishDate);
        }

        String getSubject() {
            return mSubject;
        }

        float getRating() {
            return mRating;
        }

        Bitmap getCoverImage() {
            return mCoverImage;
        }

        String getLocationName() {
            return mLocationName;
        }

        String getHeadline() {
            return mHeadline;
        }

        String getAuthor() {
            return mAuthor;
        }

        Date getPublishDate() {
            return mPublishDate;
        }
    }
}
