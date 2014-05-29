package com.chdryra.android.reviewer;

import android.view.View;

import com.chdryra.android.mygenerallibrary.GVStringList;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVProConList extends GVStringList {

	@Override
	public ViewHolder getViewHolder(View convertView) {
		return new VHProConView(convertView);
	}
	
}
