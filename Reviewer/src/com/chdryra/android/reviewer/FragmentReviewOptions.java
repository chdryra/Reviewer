package com.chdryra.android.reviewer;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.mygenerallibrary.VHDualStringView;
import com.chdryra.android.mygenerallibrary.VHStringView;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.FragmentReviewOptions.GVCellManagerList.GVCellManager;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.google.android.gms.maps.model.LatLng;

public class FragmentReviewOptions extends FragmentReviewGrid<GVCellManager> {
	private final static String DIALOG_TAG_TAG = "TagDialog";
	private final static String DIALOG_COMMENT_TAG = "CommentDialog";
	private final static String DIALOG_IMAGE_TAG = "ImageDialog";
	private final static String DIALOG_LOCATION_TAG = "LocationDialog";
	private final static String DIALOG_URL_TAG = "URLDialog";
	private final static String DIALOG_CHILD_TAG = "ChildDialog";
	private final static String DIALOG_FACTS_TAG = "FactsDialog";
	private final static String DIALOG_PROSCONS_TAG = "ProConDialog";
	
	public final static int IMAGE_REQUEST = 10;
	public final static int IMAGE_ADD = 11;
	public final static int LOCATION_REQUEST = 20;
	public final static int LOCATION_ADD = 21;
	public final static int LOCATION_MAP = 22;
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
	private HelperReviewImage mHelperReviewImage;
	
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
		cellManager.executeIntent(false);
	}
	
	@Override
	protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
		GVCellManager cellManager = (GVCellManager)parent.getItemAtPosition(position);
		cellManager.executeIntent(true);
	}
	
	@Override
	protected void onDoneSelected() {
	}
	
	@Override
	protected void onDeleteSelected() {
	}

	private DialogFragment getDialogFragment(GVType dataType) {
		if(dataType == GVType.TAGS) return new DialogTagAddFragment(); 
		if(dataType == GVType.PROCONS) return new DialogProAndConAddFragment();
		if(dataType == GVType.CRITERIA) return new DialogChildAddFragment();
		if(dataType == GVType.COMMENTS) return new DialogCommentAddFragment();
		if(dataType == GVType.IMAGES) return new DialogImageEditFragment();
		if(dataType == GVType.FACTS) return new DialogFactAddFragment();
		if(dataType == GVType.LOCATIONS) return new DialogLocationFragment();
		if(dataType == GVType.URLS) return new DialogURLFragment();
		
		return null;
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
	
	private int getRequestCode(GVType dataType, boolean isDialog) {
		if(dataType == GVType.TAGS) return isDialog? TAGS_ADD : TAGS_REQUEST;
		if(dataType == GVType.PROCONS) return isDialog? PROSCONS_ADD : PROSCONS_REQUEST;
		if(dataType == GVType.CRITERIA) return isDialog? CHILDREN_ADD : CHILDREN_REQUEST;
		if(dataType == GVType.COMMENTS) return isDialog? COMMENT_ADD : COMMENT_REQUEST;
		if(dataType == GVType.IMAGES) return isDialog? IMAGE_ADD : IMAGE_REQUEST;
		if(dataType == GVType.FACTS) return isDialog? FACTS_ADD : FACTS_REQUEST;
		if(dataType == GVType.LOCATIONS) return isDialog? LOCATION_ADD : LOCATION_REQUEST;
		if(dataType == GVType.URLS) return isDialog? URL_ADD : URL_REQUEST;
		
		return 0;
	}

	private String getDialogTag(GVType dataType) {
		if(dataType == GVType.TAGS) return DIALOG_TAG_TAG;
		if(dataType == GVType.PROCONS) return DIALOG_PROSCONS_TAG;
		if(dataType == GVType.CRITERIA) return DIALOG_CHILD_TAG;
		if(dataType == GVType.COMMENTS) return DIALOG_COMMENT_TAG;
		if(dataType == GVType.IMAGES) return DIALOG_IMAGE_TAG;
		if(dataType == GVType.FACTS) return DIALOG_FACTS_TAG;
		if(dataType == GVType.LOCATIONS) return DIALOG_LOCATION_TAG;
		if(dataType == GVType.URLS) return DIALOG_URL_TAG;
		
		return null;
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
		if(dataType == GVType.CRITERIA) return new VHReviewNodeCollection();
		if(dataType == GVType.COMMENTS) return new VHCommentView();
		if(dataType == GVType.IMAGES) return new VHImageView();
		if(dataType == GVType.FACTS) return new VHFactView();
		if(dataType == GVType.LOCATIONS) return new VHLocationView(true);
		if(dataType == GVType.URLS) return new VHUrlView();
		
		return null;	
	}

	private void initCellManagerList() {
		mCellManagerList = new GVCellManagerList();
		mCellManagerList.add(GVType.TAGS);
		mCellManagerList.add(GVType.CRITERIA);
		mCellManagerList.add(GVType.IMAGES);
		mCellManagerList.add(GVType.COMMENTS);
		mCellManagerList.add(GVType.LOCATIONS);
		mCellManagerList.add(GVType.PROCONS);
		mCellManagerList.add(GVType.FACTS);
		mCellManagerList.add(GVType.URLS);
	}
	
	private <T> void requestIntent(Class<T> c, int requestCode) {
		Intent i = new Intent(getActivity(), c);
		Controller.pack(getController(), i);
		startActivityForResult(i, requestCode);
	}

	private <T> void requestIntent(Class<T> c, int requestCode, Intent data) {
		Intent i = new Intent(getActivity(), c);
		i.putExtras(data);
		startActivityForResult(i, requestCode);
	}

	private void showQuickDialog(DialogFragment dialog, int requestCode, String tag) {
		dialog.setTargetFragment(FragmentReviewOptions.this, requestCode);
		Bundle args = Controller.pack(getController());
		args.putBoolean(DialogAddReviewDataFragment.QUICK_SET, true);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), tag);
	}
	
	private void showQuickImageDialog() {
		if(mHelperReviewImage == null)
			mHelperReviewImage = HelperReviewImage.getInstance(getController());
		
        startActivityForResult(mHelperReviewImage.getImageChooserIntents(getActivity()), IMAGE_REQUEST);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ActivityResultCode resCode = ActivityResultCode.get(resultCode);
		switch (requestCode) {
		case IMAGE_REQUEST:	
			if(mHelperReviewImage.processOnActivityResult(getActivity(), resultCode, data))
				addImage();
			break;
		case LOCATION_ADD:
			if(resCode.equals(DialogLocationFragment.RESULT_MAP.getResultCode()))
				requestIntent(ActivityReviewLocationMap.class, LOCATION_MAP, data);
			break;
		case URL_ADD:
			if(resCode.equals(DialogURLFragment.RESULT_BROWSE.getResultCode()))
				requestIntent(ActivityReviewURLBrowser.class, URL_BROWSE, data);
			break;
		case LOCATION_MAP:
			if(resCode.equals(ActivityResultCode.DONE))
				addLocation(data);
			break;
		case URL_BROWSE:
			if(resCode.equals(ActivityResultCode.DONE))
				addURL(data);
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
		updateUI();
	}
	
	private void addImage() {
		final GVImageList images = new GVImageList();
		mHelperReviewImage.addReviewImage(getActivity(), images, new FunctionPointer<Void>() {
			@Override
			public void execute(Void data) {
				getController().setData(images);
				updateUI();
			}
		});
	}
	
	private void addLocation(Intent data) {
		LatLng latLng = data.getParcelableExtra(FragmentReviewLocationMap.LATLNG);
		String name = (String)data.getSerializableExtra(FragmentReviewLocationMap.NAME);
		if(latLng != null && name != null) {
			GVLocationList list = new GVLocationList();
			list.add(latLng, name);
			getController().setData(list);
		}
	}
	
	private void addURL(Intent data) {
		URL url = (URL)data.getSerializableExtra(FragmentReviewURLBrowser.URL);
		if(url != null) {
			GVUrlList urls = new GVUrlList();
			urls.add(url);
			getController().setData(urls);
		}
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
	
	class GVCellManagerList extends GVReviewDataList<GVCellManagerList.GVCellManager> {

		private void add(GVType dataType) {
			add(new GVCellManager(dataType));
		}
					
		class GVCellManager implements GVData {
			private GVType mDataType;

			private GVCellManager(GVType dataType) {
				mDataType = dataType;
			}

			private void executeIntent(boolean forceRequestIntent) {
				if(getController().getData(mDataType).size() == 0 && !forceRequestIntent) {
					if(mDataType == GVType.IMAGES)
						showQuickImageDialog();
					else
						showQuickDialog(getDialogFragment(mDataType), getRequestCode(mDataType, true), getDialogTag(mDataType));
				}
				else
					requestIntent(getRequestDataClass(mDataType), getRequestCode(mDataType, false));
			}

			@Override
			public ViewHolder getViewHolder() {
				return null;
			}
			
			public View updateView(ViewGroup parent) {
				int size = getController().getData(mDataType).size();
				
				if(size == 0)
					return getNoDatumView(parent);

				if(mDataType == GVType.PROCONS)
					return getProConSummaryView(parent);
				else
					return size == 1? getSingleDatumView(parent, 0) : getMultiDataView(parent);
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
			
			public View getProConSummaryView(ViewGroup parent) {
				GVProConList pros = (GVProConList) getController().getData(GVType.PROS);
				GVProConList cons = (GVProConList) getController().getData(GVType.CONS);
				
				String proString = getResources().getString(R.string.text_view_pro_prefix);
				String conString = getResources().getString(R.string.text_view_con_prefix);
				
				if(pros.size() == 0)
					proString += getResources().getString(R.string.pros);
				else
					proString += pros.size() == 1? pros.getItem(0).toString() : String.valueOf(pros.size()) + " " + getResources().getString(R.string.pros);
				
				if(cons.size() == 0)
					conString += getResources().getString(R.string.cons);
				else
					conString += cons.size() == 1? cons.getItem(0).toString() : String.valueOf(cons.size()) + " " + getResources().getString(R.string.cons);
				
				ViewHolder vh = new VHDualStringView();
				vh.inflate(getActivity(), parent);
				
				return vh.updateView(new GVDualString(proString, conString));
			}
		}

		@Override
		public GVType getDataType() {
			return null;
		}
	}
}
