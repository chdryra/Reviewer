package com.chdryra.android.reviewer;

public class TraverserSearchDepthFirstPost implements TraverserSearchMethod {
	private RCollection<Integer> mDepthMap = new RCollection<Integer>();
	
	@Override
	public RCollection<Integer> search(ReviewNode node, VisitorReviewNode visitor, int depth) {
		mDepthMap.put(node.getID(), depth);
		depth++;
		for(ReviewNode child : node.getChildren())
			search(child, visitor, depth);
		
		node.acceptVisitor(visitor);
		
		return mDepthMap;
	}

}
