package com.chdryra.android.reviewer;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GridViewCellAdapter.GridViewable;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVComments implements GridViewable<GVComments.GVComment>, Iterable<GVComments.GVComment> {
	private LinkedList<GVComment> mData = new LinkedList<GVComment>();
	private boolean mIsSorted = false;
	
	public GVComments() {
	}
	
	public void add(String comment) {
		if(comment == null || comment.length() == 0)
			return;
		
		mData.add(new GVComment(comment));
		mIsSorted = false;
	}
	
	public void add(GVComment comment) {
		mData.add(comment);
		mIsSorted = false;
	}
	
	public void add(GVComments comments) {
		for(GVComment comment : comments)
			mData.add(comment);
		mIsSorted = false;
	}
	
	public void remove(GVComment comment) {
		mData.remove(comment);
		mIsSorted = false;
	}
	
	public void remove(String comment) {
		remove(new GVComment(comment));
	}
	
	public void removeAll() {
		mData.clear();
	}
	
	public boolean hasComment(GVComment comment) {
		return mData.contains(comment);
	}
	
	public boolean hasComment(String comment) {
		return hasComment(new GVComment(comment));
	}

	public GVComments getSplitComments() {
		GVComments splitComments = new GVComments();
		for(GVComment comment : mData)
			splitComments.add(comment.getSplitComments());
		
		return splitComments;
	}
	
	@Override
	public int size() {
		return mData.size();
	}

	@Override
	public GVComment getItem(int position) {
		return mData.get(position);
	}

	@Override
	public ViewHolder getViewHolder(View convertView) {
		TextView comment = (TextView)convertView.findViewById(R.id.text_view);
		return new VHCommentView(comment);
	}

	class VHCommentView implements ViewHolder {
		private TextView mComment;
		
		public VHCommentView(TextView comment) {
			mComment = comment;
		}
		
		@Override
		public void updateView(Object data) {
			GVComment comment = (GVComment)data;
			mComment.setText(comment.getCommentHeadline());
		}
	}
	
	@Override
	public boolean isSorted() {
		return mIsSorted;
	}

	@Override
	public void sort() {
		//Same as in LinkedList.
	}

	@Override
	public void sort(Comparator<GVComment> comparator) {
		if(!isSorted())
			Collections.sort(mData, comparator);
		mIsSorted = true;
	}
	
	class GVComment {
		private GVComment mUnsplitParent;
		private String mComment;
		
		private GVComment(String comment, GVComment unsplitParent) {
			mComment = comment;
			mUnsplitParent = unsplitParent;
		}
		
		private GVComment(String comment) {
			mComment = comment;
		}
		
		public String getComment() {
			return mComment;
		}
		
		public GVComments getSplitComments() {
			GVComments splitComments = new GVComments();
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
		public boolean equals(Object obj) {
			if(obj == null || obj.getClass() != getClass())
				return false;
			
			GVComment objComment = (GVComment)obj;
			if(this.mComment == objComment.mComment)
				return true;
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return mComment.hashCode();
		}
	}
	
	@Override
	public Iterator<GVComment> iterator() {
		return new GVCommentIterator();
	}
	
	class GVCommentIterator implements Iterator<GVComment> {
		int position = 0;
		
		@Override
		public boolean hasNext() {
			return position < size() && getItem(position) != null;
		}

		@Override
		public GVComment next() {
			if(hasNext())
				return (GVComment)getItem(position++);
			else				throw new NoSuchElementException("No more elements left");
		}

		@Override
		public void remove() {
			if(position <= 0) {
				throw new IllegalStateException("Have to do at least one next() before you can delete");
			} else
				mData.remove(((GVComment)getItem(position-1)).getComment());
		}
	}
}
