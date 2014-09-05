package com.chdryra.android.reviewer;

public class VisitorTreePublisher implements VisitorReviewNode {

	private ReviewTreePublisher mPublisher;
	private ReviewNode mPublishedNode;
	
	public VisitorTreePublisher(ReviewTreePublisher publisher) {
		mPublisher = publisher;
	}
	
	@Override
	public void visit(ReviewNode reviewNode) {
		if(mPublishedNode == null)
			mPublishedNode = reviewNode.getReview().publish(mPublisher);

		for(ReviewNode child : reviewNode.getChildren()) {
			VisitorTreePublisher publisher = new VisitorTreePublisher(mPublisher);
			child.acceptVisitor(publisher);
			mPublishedNode.addChild(publisher.getPublishedTree());
		}
	}
	
	public ReviewNode getPublishedTree() {
		return mPublishedNode;
	}

}
