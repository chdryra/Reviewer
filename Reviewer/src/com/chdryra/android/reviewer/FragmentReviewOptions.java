package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.mygenerallibrary.VHStringView;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Controller.GVType;
import com.chdryra.android.reviewer.FragmentReviewOptions.GVCellManagerList.GVCellManager;

public class FragmentReviewOptions extends FragmentReviewGrid<GVCellManager> {
	public final static int IMAGE_REQUEST = 10;
	public final static int IMAGE_ADD = 11;
	public final static int LOCATION_REQUEST = 20;
	public final static int LOCATION_ADD = 21;
	public final static int COMMENT_REQUEST = 30;
	public final static int COMMENT_ADD = 31;
	public final static int FACTS_REQUEST = 40;
	public final static int FACTS_ADD = 41;
	public final static int URL_REQUEST = 50;
	public final static int URL_ADD = 51;
	public final static int URL_BROWSE = 52;
	public final static int CHILDREN_REQUEST = 60;
	public final static int CHILDREN_ADD = 61;
	public final static int PROSCONS_REQUEST = 70;
	public final static int PROSCONS_ADD = 71;
	public final static int TAGS_REQUEST = 80;
	public final static int TAGS_ADD = 81;
	
	private GVCellManagerList mCellManagerList;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initCellManagerList();
		
		setGridViewData(mCellManagerList);
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setDismissOnDone(false);
		setBannerButtonText(getResources().getString(R.string.button_add_review_data));
		setIsEditable(true);
	}
	
	@Override
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		GVCellManager cellManager = (GVCellManager)parent.getItemAtPosition(position);
		cellManager.executeIntent();
	}
	
	@Override
	protected void onDoneSelected() {
	}
	
	@Override
	protected void onDeleteSelected() {
	}

	private Class<? extends Activity> getRequestDataClass(GVType dataType) {
		if(dataType == GVType.TAGS) return ActivityReviewTags.class;
		if(dataType == GVType.PROCONS) return ActivityReviewProsCons.class;
		if(dataType == GVType.CRITERIA) return ActivityReviewChildren.class;
		if(dataType == GVType.COMMENTS) return ActivityReviewComments.class;
		if(dataType == GVType.IMAGES) return ActivityReviewImages.class;
		if(dataType == GVType.FACTS) return ActivityReviewFacts.class;
		if(dataType == GVType.LOCATIONS) return ActivityReviewLocations.class;
		if(dataType == GVType.URLS) return ActivityReviewURLs.class;
		
		return null;
	}
	
	private int getRequestCode(GVType dataType) {
		if(dataType == GVType.TAGS) return TAGS_REQUEST;
		if(dataType == GVType.PROCONS) return PROSCONS_REQUEST;
		if(dataType == GVType.CRITERIA) return CHILDREN_REQUEST;
		if(dataType == GVType.COMMENTS) return COMMENT_REQUEST;
		if(dataType == GVType.IMAGES) return IMAGE_REQUEST;
		if(dataType == GVType.FACTS) return FACTS_REQUEST;
		if(dataType == GVType.LOCATIONS) return LOCATION_REQUEST;
		if(dataType == GVType.URLS) return URL_REQUEST;
		
		return 0;
	}

	private String getDataString(GVType dataType) {
		Resources r = getResources();
		if(dataType == GVType.TAGS) return r.getString(R.string.tags); 
		if(dataType == GVType.PROCONS) return r.getString(R.string.procons);
		if(dataType == GVType.CRITERIA) return r.getString(R.string.criteria);
		if(dataType == GVType.COMMENTS) return r.getString(R.string.comments);
		if(dataType == GVType.IMAGES) return r.getString(R.string.images);
		if(dataType == GVType.FACTS) return r.getString(R.string.facts);
		if(dataType == GVType.LOCATIONS) return r.getString(R.string.locations);
		if(dataType == GVType.URLS) return r.getString(R.string.links);
		
		return null;	
	}
	
	private ViewHolder getDatumViewHolder(GVType dataType) {
		if(dataType == GVType.TAGS) return new VHTagView(); 
		if(dataType == GVType.PROCONS) return new VHProConSummaryView();
		if(dataType == GVType.CRITERIA) return new VHReviewNodeCollection();
		if(dataType == GVType.COMMENTS) return new VHCommentView();
		if(dataType == GVType.IMAGES) return new VHImageView();
		if(dataType == GVType.FACTS) return new VHFactView();
		if(dataType == GVType.LOCATIONS) return new VHLocationView();
		if(dataType == GVType.URLS) return new VHUrlView();
		
		return null;	
	}
	
	private void initCellManagerList() {
		mCellManagerList = new GVCellManagerList();
		mCellManagerList.add(GVType.TAGS);
		mCellManagerList.add(GVType.CRITERIA);
		mCellManagerList.add(GVType.COMMENTS);
		mCellManagerList.add(GVType.PROCONS);
		mCellManagerList.add(GVType.IMAGES);
		mCellManagerList.add(GVType.LOCATIONS);
		mCellManagerList.add(GVType.FACTS);
		mCellManagerList.add(GVType.URLS);
	}
	
	private <T> void requestIntent(Class<T> c, int requestCode) {
		Intent i = new Intent(getActivity(), c);
		Controller.pack(getController(), i);
		startActivityForResult(i, requestCode);
	}
	
	@Override
	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new ReviewOptionsGridCellAdapter();
	}
	
	class ReviewOptionsGridCellAdapter extends GridViewCellAdapter {
		public ReviewOptionsGridCellAdapter() {
			super(getActivity(), getGridData(), getGridCellWidth(), getGridCellHeight());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getGridData().getItem(position).updateView(parent);
			convertView.getLayoutParams().height = getGridCellHeight();
			convertView.getLayoutParams().width = getGridCellWidth();
			
			return convertView;
		}
	}
	
	class GVCellManagerList extends GVList<GVCellManagerList.GVCellManager> {

		private void add(GVType dataType) {
			add(new GVCellManager(dataType));
		}
					
		class GVCellManager implements GVData {
			private Controller.GVType mDataType;

			private GVCellManager(Controller.GVType dataType) {
				mDataType = dataType;
			}

			private void executeIntent() {
				requestIntent(getRequestDataClass(mDataType), getRequestCode(mDataType));
			}

			@Override
			public ViewHolder getViewHolder() {
				return null;
			}
			
			public View updateView(ViewGroup parent) {
				int size = getController().getData(mDataType).size();
				if(size == 0)
					return getNoDatumView(parent);
				else if(size == 1)
					return getSingleDatumView(parent, 0);
				else
					return getMultiDataView(parent);
			}
			
			public View getNoDatumView(ViewGroup parent) {
				ViewHolder vh = new VHStringView(R.layout.grid_cell_options, R.id.text_view);
				vh.inflate(getActivity(), parent);
				return vh.updateView(new GVString(getDataString(mDataType)));
			}

			public View getSingleDatumView(ViewGroup parent, int position) {
				if(position > getController().getData(mDataType).size())
					return getNoDatumView(parent);

				ViewHolder vh = getDatumViewHolder(mDataType);
				vh.inflate(getActivity(), parent);
				return vh.updateView(getController().getData(mDataType).getItem(position));
			}
						
			public View getMultiDataView(ViewGroup parent) {
				ViewHolder vh = new VHDualStringView();
				vh.inflate(getActivity(), parent);
				return vh.updateView(new GVDualString(String.valueOf(getController().getData(mDataType).size()), getDataString(mDataType)));
			}
		}
	}
}
