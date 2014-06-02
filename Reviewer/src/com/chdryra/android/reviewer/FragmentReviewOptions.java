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
import com.chdryra.android.mygenerallibrary.GVStringList;
import com.chdryra.android.mygenerallibrary.VHStringView;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Controller.GVType;
import com.chdryra.android.reviewer.FragmentReviewOptions.GVCellManagerList.GVCellManager;
import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVCriterionList.GVCriterion;
import com.chdryra.android.reviewer.GVFactList.GVFact;
import com.chdryra.android.reviewer.GVImageList.GVImage;

public class FragmentReviewOptions extends FragmentReviewGrid<GVCellManager> {
	public final static int IMAGE_REQUEST = 10;
	public final static int IMAGE_EDIT = 11;
	public final static int LOCATION_REQUEST = 20;
	public final static int LOCATION_EDIT = 21;
	public final static int COMMENT_REQUEST = 30;
	public final static int COMMENT_EDIT = 31;
	public final static int FACTS_REQUEST = 40;
	public final static int FACTS_EDIT = 41;
	public final static int URL_REQUEST = 50;
	public final static int URL_EDIT = 51;
	public final static int URL_BROWSE = 52;
	public final static int CHILDREN_REQUEST = 60;
	public final static int CHILDREN_EDIT = 61;
	public final static int PROSCONS_REQUEST = 70;
	public final static int PROSCONS_EDIT = 71;
	public final static int TAGS_REQUEST = 80;
	public final static int TAGS_EDIT = 81;
	
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
	
	class GVCellManagerList extends GVList<GVCellManagerList.GVCellManager> {

		private void add(GVType dataType) {
			add(new GVCellManager(dataType));
		}
		
		@Override
		public ViewHolder getViewHolder(int position) {
			return getItem(position).getViewHolder();
		}
					
		class GVCellManager implements GVData {
			private Controller.GVType mDataType;
			private VHSummaryCellView mViewholder;
			
			private GVCellManager(Controller.GVType dataType) {
				mDataType = dataType;
				mViewholder = new VHSummaryCellView(getDataString(dataType), getController().getData(dataType).getViewHolder(0));
			}
		
			public GVType getDataType() {
				return mDataType;
			}
			
			private void executeIntent() {
				requestIntent(getRequestDataClass(mDataType), getRequestCode(mDataType));
			}

			private ViewHolder getViewHolder() {
				return mViewholder;
			}
		}
	}

	private class VHSummaryCellView implements ViewHolder {
		private String mDataString;
		
		private ViewHolder mNoDataVH;
		private ViewHolder mSingleDatumVH;
		private ViewHolder mSummaryDataVH;
		
		private View mNoDataView;
		private View mSingleDatumView;
		private View mSummaryDataView;
		
		private View mCurrentView;
		
		private VHSummaryCellView(String dataString, ViewHolder singleDatumVH) {
			mDataString = dataString;
			mNoDataVH = new VHStringView(R.layout.grid_cell_options, R.id.text_view);
			mSummaryDataVH = new VHDualStringView();
			mSingleDatumVH = singleDatumVH;
		}
		
		@Override
		public View inflate(Activity activity, ViewGroup parent) {
			mNoDataView = mNoDataVH.inflate(activity, parent);
			mNoDataVH.updateView(new GVString(mDataString));
			mSingleDatumView = mSingleDatumVH.inflate(activity, parent);
			mSummaryDataView = mSummaryDataVH.inflate(activity, parent);
			
			mNoDataView.setTag(this);
			mSingleDatumView.setTag(this);
			mSummaryDataView.setTag(this);
			
			mCurrentView = mNoDataView;
			
			return mCurrentView;
		}

		@Override
		public void updateView(GVData data) {
			GVCellManager manager = (GVCellManager)data;
			if(manager != null)
				updateView(manager.getDataType());
		}

		public void updateView(GVType dataType) {
			GVList<? extends GVData> data = getController().getData(dataType); 
			int size = data.size();
			if(size == 0) {
				mCurrentView = mNoDataView;
			} else if(size > 1) {
				mSummaryDataVH.updateView(new GVDualString(String.valueOf(size), mDataString));
				mCurrentView = mSummaryDataView;
			} else {
				mSingleDatumVH.updateView(data.getItem(0));
				mCurrentView = mSingleDatumView;
			}	
		}
	}	
}
