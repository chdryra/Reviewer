package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class CollectionReviewNode extends RCollection<ReviewNode> implements Parcelable {
	public CollectionReviewNode() {
	}

	public CollectionReviewNode(CollectionReview reviews) {
		for(Review r : reviews)
			add(FactoryReview.createReviewNode(r));
	}
	
	public void add(ReviewNode reviewNode) {
		put(reviewNode.getID(), reviewNode);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	public CollectionReviewNode(Parcel in) {
		Parcelable[] nodes = in.readParcelableArray(ReviewNode.class.getClassLoader());
		for(int i = 0; i < nodes.length; ++i) {
			ReviewNode node = (ReviewNode)nodes[i];
			mData.put(node.getID(), node);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		ReviewNode[] nodes = mData.values().toArray(new ReviewNode[mData.size()]);
		dest.writeParcelableArray(nodes, flags);
	}
	
	public static final Parcelable.Creator<CollectionReviewNode> CREATOR 
	= new Parcelable.Creator<CollectionReviewNode>() {
	    public CollectionReviewNode createFromParcel(Parcel in) {
	        return new CollectionReviewNode(in);
	    }

	    public CollectionReviewNode[] newArray(int size) {
	        return new CollectionReviewNode[size];
	    }
	};
}	