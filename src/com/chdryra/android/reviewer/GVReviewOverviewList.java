/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.graphics.Bitmap;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.util.Comparator;
import java.util.Date;

/**
 * GVReviewDataList: GVReviewOverview
 * ViewHolder: VHReviewNodeOverview
 * <p/>
 * <p>
 * Used for Review summaries in published feed
 * </p>
 *
 * @see com.chdryra.android.reviewer.Administrator
 * @see com.chdryra.android.reviewer.FragmentFeed
 * @see com.chdryra.android.reviewer.VHReviewNodeOverview
 */
class GVReviewOverviewList extends GVReviewDataList<GVReviewOverviewList.GVReviewOverview> {

    GVReviewOverviewList() {
        super(GVType.REVIEW);
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

    @Override
    protected Comparator<GVReviewOverview> getDefaultComparator() {

        return new Comparator<GVReviewOverviewList.GVReviewOverview>() {
            @Override
            public int compare(GVReviewOverview lhs, GVReviewOverview rhs) {
                return lhs.getPublishDate().compareTo(rhs.getPublishDate());
            }
        };
    }

    /**
     * GVData version of: Review
     * ViewHolder: VHReviewNodeOverview
     * <p>
     * Methods for getting subject, rating, cover image, comment headline, location,
     * publish author and date.
     * </p>
     *
     * @see com.chdryra.android.mygenerallibrary.GVData
     * @see com.chdryra.android.reviewer.Review
     * @see com.chdryra.android.reviewer.VHReviewNodeSubjectRating
     */
    class GVReviewOverview implements GVData {
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

        @Override
        public ViewHolder getViewHolder() {
            return new VHReviewNodeOverview();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            GVReviewOverview overview = (GVReviewOverview) obj;
            return mId.equals(overview.getId());
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        String getId() {
            return mId;
        }
    }
}
