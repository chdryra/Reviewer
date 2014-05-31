package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVStringList;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVTagList extends GVStringList {

	@Override
	public ViewHolder getViewHolder(int position) {
		return new VHTagView();
	}
	
}
