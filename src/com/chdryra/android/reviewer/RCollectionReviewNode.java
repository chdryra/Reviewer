/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class RCollectionReviewNode extends RCollection<ReviewNode> {
	public RCollectionReviewNode() {
	}
	
	public void add(ReviewNode reviewNode) {
		put(reviewNode.getID(), reviewNode);
	}
}	