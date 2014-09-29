/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public class TraverserSearchDepthFirstPre implements TraverserSearchMethod {

	@Override
	public void search(ReviewNode node, VisitorReviewNode visitor, int depth) {
		node.acceptVisitor(visitor);
		depth++;
		for(ReviewNode child : node.getChildren())
			search(child, visitor, depth);
	}

}
