package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewNodeCollection extends RCollection<ReviewNode> implements Parcelable {
	private static final String REVIEWS = "REVIEWS";
	private static final String DATA = "com.chdryra.android.reviewer.review_node_collection_data";
	
	public ReviewNodeCollection() {
	}
	
	public void add(ReviewNode reviewNode) {
		put(reviewNode.getID(), reviewNode);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	public ReviewNodeCollection(Parcel in) {
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
	
	public static final Parcelable.Creator<ReviewNodeCollection> CREATOR 
	= new Parcelable.Creator<ReviewNodeCollection>() {
	    public ReviewNodeCollection createFromParcel(Parcel in) {
	        return new ReviewNodeCollection(in);
	    }

	    public ReviewNodeCollection[] newArray(int size) {
	        return new ReviewNodeCollection[size];
	    }
	};
}	