package com.chdryra.android.reviewer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridViewCellAdapter extends BaseAdapter {	
		private Activity mActivity;
		private GridViewData mData;
		private int mCellView;
		private int mCellWidth;
		private int mCellHeight;
		private int mTextColour;
		
		public GridViewCellAdapter(Activity activity, GridViewData data, int cellView, int cellWidth, int cellHeight, int textColour){
		    mActivity = activity;
			mData = data;
			mCellView = cellView;
			mCellWidth = cellWidth;
			mCellHeight = cellHeight;
		    mTextColour = textColour;
		}
		
		public void setData(GridViewData data) {
			mData = data;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return mData.size();
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return mData.getItem(position);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			
			if (convertView == null) {						
				convertView = mActivity.getLayoutInflater().inflate(mCellView, parent, false);
				convertView.getLayoutParams().height = mCellHeight;
				convertView.getLayoutParams().width = mCellWidth;
				vh = mData.getViewHolder(convertView);
				convertView.setTag(vh);
			} else
				vh = (ViewHolder)convertView.getTag();
			
			if(vh != null)
				vh.updateView(getItem(position), mTextColour);
			
			return(convertView);
		};		
	};
