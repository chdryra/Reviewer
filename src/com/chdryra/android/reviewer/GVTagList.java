package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVTagList extends GVReviewDataList<GVString> {

	public GVTagList() {
		super(GVType.TAGS);
	}
	
	public void add(String string) {
		if(string != null && string.length() > 0)
			add(new GVString(string));
	}
	
	public boolean contains(String string) {
		return contains(new GVString(string));
	}
	
	public void remove(String string) {
		remove(new GVString(string));
	}

	@Override
	protected Comparator<GVString> getDefaultComparator() {
		return new Comparator<GVString>() {

			@Override
			public int compare(GVString lhs, GVString rhs) {
				return lhs.toString().compareTo(rhs.toString());
			}
		};
	}
	
	@Override
	public ViewHolder getViewHolder(int position) {
		return new VHTagView();
	}
	
}
