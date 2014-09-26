/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

class GVTagList extends GVReviewDataList<GVString> {

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
