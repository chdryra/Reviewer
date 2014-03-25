package com.chdryra.android.reviewer;

import java.util.UUID;

public class ReviewIDGenerator {
	private static ReviewIDGenerator sInstance = null;
	public static ReviewID generateID() {
		if(sInstance == null)
			sInstance= new ReviewIDGenerator();
		
		return sInstance.new ReviewID();
	}
	
	public class ReviewID {
		private UUID mID;
		
		private ReviewID() {
			mID = UUID.randomUUID();
		}
		
		public boolean equals(ReviewID reviewID) {
			return mID.equals(reviewID.mID);
		}
		
		public String toString() {
			return mID.toString();
		}
	}
}
