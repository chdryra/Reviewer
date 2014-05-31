package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
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
	
	private CellManager getCellManager(GVType dataType) {
		if(dataType == GVType.TAGS) return new CellManager(GVType.TAGS, ActivityReviewTags.class, TAGS_REQUEST, new VHTagView());
		if(dataType == GVType.PROCONS) return new CellManager(GVType.PROCONS, ActivityReviewProsCons.class, PROSCONS_REQUEST, new VHFactView(R.layout.grid_cell_procon_summary, R.id.pros_text_view, R.id.cons_text_view));
		if(dataType == GVType.CRITERIA) return new CellManager(GVType.CRITERIA, ActivityReviewChildren.class, CHILDREN_REQUEST, new VHReviewNodeCollection());
		if(dataType == GVType.COMMENTS) return new CellManager(GVType.COMMENTS, ActivityReviewComments.class, COMMENT_REQUEST, new VHCommentView());
		if(dataType == GVType.IMAGES) return new CellManager(GVType.IMAGES, ActivityReviewImages.class, IMAGE_REQUEST, new VHImageView());
		if(dataType == GVType.FACTS) return new CellManager(GVType.FACTS, ActivityReviewFacts.class, FACTS_REQUEST, new VHFactView());
		if(dataType == GVType.LOCATIONS) return new CellManager(GVType.LOCATIONS, ActivityReviewLocations.class, LOCATION_REQUEST, new VHStringView(R.layout.grid_cell_location, R.id.text_view));
		if(dataType == GVType.URLS) return new CellManager(GVType.URLS, ActivityReviewURLs.class, URL_REQUEST, new VHStringView(R.layout.grid_cell_url, R.id.text_view));
		
		return null;
	}
	
	private GVData getCellUpdateData(GVType dataType) {
		if(dataType == GVType.TAGS) return getTagCellUpdateData();
		if(dataType == GVType.PROCONS) return getProConCellUpdateData();
		if(dataType == GVType.CRITERIA) return getCriteriaCellUpdateData();
		if(dataType == GVType.COMMENTS) return getCommentCellUpdateData();
		if(dataType == GVType.IMAGES) return getImageCellUpdateData();
		if(dataType == GVType.FACTS) return getFactCellUpdateData();
		if(dataType == GVType.LOCATIONS) return getLocationCellUpdateData();
		if(dataType == GVType.URLS) return getURLCellUpdateData();
		
		return null;
	}
	
	private GVString getTagCellUpdateData() {
		GVTagList tags = getController().getTags();
		String title = getResources().getString(R.string.tags);
		GVString data = null;
		int size = tags.size();
		if(size > 1)
			title = size + " " + title;
		else if(size == 1)
			data = tags.getItem(0);
		
		if(data == null)
			data = new GVString(title);
		
		return data;
	}
	
	private GVCriterion getCriteriaCellUpdateData() {
		ControllerReviewNodeCollection controller = getController().getCollectionController();
		GVCriterionList criteria = controller.getGridViewiableData();
		GVCriterion data = null;
		int size = criteria.size();
		String title = getResources().getString(R.string.criteria);
		if(size > 1)
			title = size + " " + getResources().getString(R.string.criteria); 
		else if(size == 1)
			data = criteria.getItem(0);
				
		if(data == null)
			data = criteria.new GVCriterion(title, controller.getRating());
		
		return data;
	}
	
	private GVComment getCommentCellUpdateData() {
		GVCommentList comments = getController().getComments();
		String title = getResources().getString(R.string.comments);
		GVComment data = null;
		int size = comments.size();
		if(size > 1)
			title = size + " " + title;
		else if(size == 1)
			data = comments.getItem(0);
		
		if(data == null)
			data = comments.new GVComment(title);
		
		return data;
	}
	
	private GVImage getImageCellUpdateData() {
		GVImageList images = getController().getImages();
		String title = getResources().getString(R.string.images);
		GVImage data = null;
		int size = images.size();
		if(size > 1)
			title = size + " " + title;
		else if(size == 1)
			 data = images.getItem(0);
		
		if(data == null)
			data = images.new GVImage(null, null, title);
		
		return data;
	}

	private GVFact getFactCellUpdateData() {
		GVFactList facts = getController().getFacts();
		String title = getResources().getString(R.string.facts);
		GVFact data = null;
		int size = facts.size();
		if(size == 1)
			data = facts.getItem(0);
		
		if(data == null)
			data = facts.new GVFact(String.valueOf(size), title);
		
		return data;
	}
	
	private GVString getLocationCellUpdateData() {
		GVLocationList locations = getController().getLocations();
		String title = getResources().getString(R.string.locations);
		int size = locations.size();
		if(size > 1)
			title = "@" + size + " " + title;
		else if(size == 1)
			title = "@" + locations.getItem(0).getShortenedName();
		
		return new GVString(title);
	}
	
	private GVString getURLCellUpdateData() {
		GVUrlList urls = getController().getURLs();
		String title = getResources().getString(R.string.links);
		int size = urls.size();
		if(size > 1)
			title = size + " " + title;
		else if(size == 1)
			 title = urls.getItem(0).toShortenedString();

		return new GVString(title);
	}
	
	private GVFact getProConCellUpdateData() {
		//Hack to have similar dual string look to GVFact
		GVStringList prosList = getController().getPros(); 
		GVStringList consList = getController().getCons();
		int pros = prosList.size();
		int cons = consList.size();
		String prosTitle = null;
		String consTitle = null;
		if(pros == 0 && cons == 0) {
			prosTitle = "+" + getResources().getString(R.string.pros);
			consTitle = "-" + getResources().getString(R.string.cons);
		} else {
			if(pros > 0)
				prosTitle = pros == 1? "+" + prosList.getItem(0).toString() : "+" + pros;
			if(cons > 0)
				consTitle = cons == 1? "-" + consList.getItem(0).toString() : "-" + cons;
		}
	
		return getController().getFacts().new GVFact(prosTitle, consTitle);
	}
	
	private <T> void requestIntent(Class<T> c, int requestCode) {
		Intent i = new Intent(getActivity(), c);
		Controller.pack(getController(), i);
		startActivityForResult(i, requestCode);
	}
	
	private class CellManager {
		private Controller.GVType mDataType;
		private Class<?> mRequestDataActivity;
		private int mRequestCode;
		private ViewHolder mViewholder;
		
		private CellManager(Controller.GVType dataType,  Class<?> requestDataActivity, int requestCode, ViewHolder viewHolder) {
			mDataType = dataType;
			mRequestDataActivity = requestDataActivity;
			mRequestCode = requestCode;
			mViewholder = viewHolder;
		}
		
		private Controller.GVType getDataType() {
			return mDataType;
		}

		private void requestGetDataIntent() {
			requestIntent(mRequestDataActivity, mRequestCode);
		}
		
		private ViewHolder getViewHolder() {
			return mViewholder;
		}
		
		private void updateView() {
			mViewholder.updateView(getCellUpdateData(mDataType));
		}
	}
	
	class GVCellManagerList extends GVList<GVCellManagerList.GVCellManager> {

		private void add(GVType dataType) {
			add(new GVCellManager(dataType));
		}
		
		@Override
		public ViewHolder getViewHolder(int position) {
			return new VHReviewOptionView(getItem(position));
		}
			
		private class VHReviewOptionView implements ViewHolder {
			private GVCellManager mManager;
			
			private VHReviewOptionView(GVCellManager manager) {
				mManager = manager;
			}
			
			@Override
			public View inflate(Activity activity, ViewGroup parent) {
				View view = mManager.getViewHolder().inflate(activity, parent);
				view .setTag(this);
				return view;
			}

			@Override
			public void updateView(GVData data) {
				mManager.updateCellDisplay();
			}
			
		}
		
		class GVCellManager implements GVData {
			private CellManager mCellManager;
			
			private GVCellManager(Controller.GVType dataType) {
				mCellManager = getCellManager(dataType);
			}
			
			public GVType getDataType() {
				return mCellManager.getDataType();
			}
			
			private void executeIntent() {
				if(mCellManager != null)
					mCellManager.requestGetDataIntent();
			}

			private void updateCellDisplay() {
				mCellManager.updateView();
			}

			private ViewHolder getViewHolder() {
				return mCellManager.getViewHolder();
			}
			
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + getOuterType().hashCode();
				result = prime
						* result
						+ ((mCellManager == null) ? 0 : mCellManager.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				GVCellManager other = (GVCellManager) obj;
				if (!getOuterType().equals(other.getOuterType()))
					return false;
				if (mCellManager == null) {
					if (other.mCellManager != null)
						return false;
				} else if (!mCellManager.equals(other.mCellManager))
					return false;
				return true;
			}

			private GVCellManagerList getOuterType() {
				return GVCellManagerList.this;
			}
		}
	}
}
