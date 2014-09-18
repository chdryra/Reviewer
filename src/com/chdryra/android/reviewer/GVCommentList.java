package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVCommentList extends GVReviewDataList<GVCommentList.GVComment> {

	public GVCommentList() {
		super(GVType.COMMENTS);
	}
	
	public void add(String comment) {
		add(new GVComment(comment));
	}
	
	public void remove(String comment) {
		remove(new GVComment(comment));
	}
	
	public boolean contains(String comment) {
		return contains(new GVComment(comment));
	}
	
	public GVCommentList getSplitComments() {
		GVCommentList splitComments = new GVCommentList();
		for(GVComment comment : this)
			splitComments.add(comment.getSplitComments());
		
		return splitComments;
	}
	
	public class GVComment implements GVData {
		private GVComment mUnsplitParent;
		private String mComment;
		
		private GVComment(String comment, GVComment unsplitParent) {
			mComment = comment;
			mUnsplitParent = unsplitParent;
		}
		
		public GVComment(String comment) {
			mComment = comment;
		}
		
		public String getComment() {
			return mComment;
		}
		
		public GVCommentList getSplitComments() {
			GVCommentList splitComments = new GVCommentList();
			for(String comment : CommentFormatter.split(mComment))
				splitComments.add(new GVComment(comment, this));
			
			return splitComments;
		}
		
		public GVComment getUnSplitComment() {
			if(mUnsplitParent != null)
				return mUnsplitParent.getUnSplitComment();
			else
				return this;
		}

		public String getCommentHeadline() {
			return CommentFormatter.getHeadline(mComment);
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHCommentView();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((mComment == null) ? 0 : mComment.hashCode());
			result = prime
					* result
					+ ((mUnsplitParent == null) ? 0 : mUnsplitParent.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVComment other = (GVComment) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mComment == null) {
				if (other.mComment != null)
					return false;
			} else if (!mComment.equals(other.mComment))
				return false;
			if (mUnsplitParent == null) {
				if (other.mUnsplitParent != null)
					return false;
			} else if (!mUnsplitParent.equals(other.mUnsplitParent))
				return false;
			return true;
		}

		private GVCommentList getOuterType() {
			return GVCommentList.this;
		}
	}
}
