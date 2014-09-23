/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Comparator;
import java.util.Date;

import android.graphics.Bitmap;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVReviewOverviewList extends GVReviewDataList<GVReviewOverviewList.GVReviewOverview> {
	
	public GVReviewOverviewList() {
		super(GVType.REVIEW);
	}
	
	public void add(String id, String subject, float rating, Bitmap coverImage, String headline, String locationName, String author, Date publishDate) {
		if(!contains(id))
			add(new GVReviewOverview(id, subject, rating, coverImage, headline, locationName, author, publishDate));
	}
	
	public boolean contains(String id) {
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
	
	public class GVReviewOverview implements GVData{
		private String mId;
		private String mSubject;
		private float mRating;
		private Bitmap mCoverImage;
		private String mHeadline;
		private String mLocationName;
		private String mAuthor;
		private Date mPublishDate;
		
		private GVReviewOverview(String id) {
			mId = id;
		}
		
		public GVReviewOverview(String id, String subject, float rating, Bitmap coverImage, String headline, String locationName, String Author, Date publishDate) {
			mId = id;
			mSubject = subject;
			mRating = rating;
			mCoverImage = coverImage;
			mHeadline = headline;
			mLocationName = locationName;
			mAuthor = Author;
			mPublishDate = publishDate;
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

		public String getLocationName() {
			return mLocationName;
		}
		
		public String getHeadline() {
			return mHeadline;
		}
		
		public String getAuthor() {
			return mAuthor;
		}
		
		public Date getPublishDate() {
			return mPublishDate;
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHReviewNodeOverview();
		}

		@Override
		public boolean equals(Object obj) {
			return mId.equals(obj);
		}
		
		@Override
		public int hashCode() {
			return mId.hashCode();
		}
	}
}
