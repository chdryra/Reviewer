package com.chdryra.android.reviewer;

public class VisitorRatingAverageOverDescendents implements VisitorRatingCalculator{
	private float mRatingTotal = 0;
	private int mNumberRatings = 0;
	
	public VisitorRatingAverageOverDescendents() {
	}

	@Override
	public void visit(ReviewNode reviewNode) {
		if(reviewNode.getChildren().size() == 0) {
			mRatingTotal += reviewNode.getRating().get();
			mNumberRatings = 1;
		} else {
			for(ReviewNode child : reviewNode.getChildren()) {
				VisitorRatingAverageOverDescendents averager = new VisitorRatingAverageOverDescendents();
				child.acceptVisitor(averager);
				mRatingTotal += averager.getRating();
				mNumberRatings++;
			}
		}
	}

	@Override
	public float getRating() {
		if(mNumberRatings > 0)
			return mRatingTotal / mNumberRatings;
		else
			return 0;
	}

	@Override
	public void clear() {
		mRatingTotal = 0;
		mNumberRatings = 0;
	}

}
