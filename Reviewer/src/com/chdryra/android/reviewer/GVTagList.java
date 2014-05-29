package com.chdryra.android.reviewer;

import android.view.View;

import com.chdryra.android.mygenerallibrary.GVStringList;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVTagList extends GVStringList {

	@Override
	public ViewHolder getViewHolder(View convertView) {
		return new VHTagView(convertView);
	}
	
}
