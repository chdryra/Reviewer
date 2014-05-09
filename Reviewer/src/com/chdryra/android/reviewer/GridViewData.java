package com.chdryra.android.reviewer;

import android.view.View;

public interface GridViewData {
	public int size();
	public Object getItem(int position);
	public ViewHolder getViewHolder(View convertView);
}
