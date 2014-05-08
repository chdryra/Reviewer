package com.chdryra.android.reviewer;

import java.util.ArrayList;

//import org.apache.commons.lang3.text.WordUtils;

public class ReviewTagsManager {
	private static ReviewTagsManager sInstance;
	private ReviewTagCollection mTags;
	
	private ReviewTagsManager() {
		mTags = new ReviewTagCollection();
	}
	
	public static ReviewTagsManager getManager() {
		if(sInstance == null)
			sInstance = new ReviewTagsManager();
		
		return sInstance;
	}
	
	public static ReviewTagCollection getTags() {
		return getManager().mTags;
	}
	
	public static ReviewTagCollection getTags(Review review) {
		ReviewTagCollection tags = new ReviewTagCollection();
		for(ReviewTag tag : getTags()) {
			if(tag.hasReview(review))
				tags.add(tag);
		}
		
		return tags;
	}

	public static void tag(Review review, ArrayList<String> tags) {
		for(String tag : tags)
			getManager().tag(review, tag);
	}


	public static void untag(Review review) {
		ReviewTagCollection reviewTags = getTags(review);
		for(ReviewTag reviewTag : reviewTags) {
			reviewTag.removeReview(review);
			if(!reviewTag.isValid())
				getTags().remove(reviewTag);
		}
	}
	
	public static boolean hasTags(Review review) {
		return getTags(review).size() > 0;
	}
	
	public void tag(Review review, String tag) {
		ReviewTag reviewTag = getTags().get(tag);
		if(reviewTag == null) {
			reviewTag = getManager().new ReviewTag(tag, review);
			getTags().add(reviewTag);
		} else
			reviewTag.addReview(review);
	}

	public void tag(Review review, ReviewTag tag) {
		tag.addReview(review);
	}
		
	public void untag(Review review, ReviewTag tag) {
		tag.removeReview(review);
	}

	public void untag(Review review, String tag) {
		untag(review, mTags.get(tag));
	}

	public ReviewTagCollection getTags(RCollectionReview reviews) {
		ReviewTagCollection tags = new ReviewTagCollection();
		for(ReviewTag tag : getTags()) {
			for(Review review : reviews)
				if(tag.hasReview(review))
					tags.add(tag);
		}
		
		return tags;
	}
	
	public boolean hasTag(Review review, ReviewTag tag) {
		return mTags.contains(tag);
	}
	
	public boolean hasTag(Review review, String tag) {
		return mTags.contains(tag);
	}
	
	public class ReviewTag implements Comparable<ReviewTag> {
		private RCollectionReview mReviews;
		private String mTag;
		
		private ReviewTag(String tag, Review review) {
			//mTag = WordUtils.capitalize(tag);
			mTag = tag;
			mReviews = new RCollectionReview();
			mReviews.add(review);
		}
		
		public String toString() {
			return mTag;
		}
		
		public RCollectionReview getReviews() {
			return mReviews;
		}
		
		public boolean hasReview(Review r) {
			return mReviews.containsID(r.getID());
		}

		public boolean equals(ReviewTag tag) {
			return equals(tag.toString());
		}

		public boolean equals(String tag) {
			return mTag.equalsIgnoreCase(tag);
		}

		private boolean isValid() {
			return mReviews.size() > 0;
		}
		
		private void addReview(Review review) {
			mReviews.add(review);
		}
		
		private void removeReview(Review review) {
			mReviews.remove(review.getID());
		}

		@Override
		public int compareTo(ReviewTag another) {
			return mTag.compareToIgnoreCase(another.mTag);
		}		
	}

}
