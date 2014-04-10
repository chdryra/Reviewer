package com.chdryra.android.reviewer;

public class TraverserSearchDepthFirstPre implements TraverserSearchMethod {
	private RCollection<Integer> mDepthMap = new RCollection<Integer>();
	
	@Override
	public RCollection<Integer> search(ReviewNode node, VisitorReviewNode visitor, int depth) {
		mDepthMap.put(node.getID(), depth);
		
		node.acceptVisitor(visitor);
		depth++;
		for(ReviewNode child : node.getChildren())
			search(child, visitor, depth);
		
		return mDepthMap;
	}

}
