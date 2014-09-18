package com.chdryra.android.reviewer;

public class VisitorRatingAverageOfChildren implements VisitorRatingCalculator {
	private float mRating = 0;
	
	@Override
	public void visit(ReviewNode reviewNode) {
		float total = 0;
		for(ReviewNode child : reviewNode.getChildren())
			total += child.getRating().get();
		
		mRating = total/reviewNode.getChildren().size();
	}

	@Override
	public float getRating() {
		return mRating;
	}

	@Override
	public void clear() {
		mRating = 0;
	}

}
