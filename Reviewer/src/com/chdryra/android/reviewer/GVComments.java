package com.chdryra.android.reviewer;

import java.util.Comparator;

import android.view.View;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVComments extends GVList<GVComment> {

	public void add(String comment) {
		add(new GVComment(comment));
	}
	
	public void remove(String comment) {
		remove(new GVComment(comment));
	}
	
	public boolean has(String comment) {
		return has(new GVComment(comment));
	}
	
	public GVComments getSplitComments() {
		GVComments splitComments = new GVComments();
		for(GVComment comment : this)
			splitComments.add(comment.getSplitComments());
		
		return splitComments;
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
	protected Comparator<GVComment> getDefaultComparator() {
		return new Comparator<GVComment>() {
			@Override
			public int compare(GVComment lhs, GVComment rhs) {
				int comp = 0;
				if(mData.indexOf(lhs) > mData.indexOf(rhs))
					comp = 1;
				else if (mData.indexOf(lhs) < mData.indexOf(rhs))
					comp = -1;
				
				return comp;
			}
		};
	}
}
