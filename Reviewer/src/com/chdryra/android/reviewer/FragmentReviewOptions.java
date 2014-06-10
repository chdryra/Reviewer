package com.chdryra.android.reviewer;

import java.net.URL;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
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
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.ReviewDataOptions.ReviewDataOption;
import com.google.android.gms.maps.model.LatLng;

public class FragmentReviewOptions extends FragmentReviewGrid<GVCellManager> {
	public final static int LOCATION_MAP = 22;
	public final static int URL_BROWSE = 52;
	
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
	protected void updateUI() {
		super.updateUI();
		updateBackground();
	}
	
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void updateBackground() {
		if(getController().hasData(GVType.IMAGES)) {
			GVImage image = (GVImage) getController().getData(GVType.IMAGES).getItem(0);
			BitmapDrawable bitmap = new BitmapDrawable(getResources(), image.getBitmap());
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				getLayout().setBackground(bitmap);
			} else {
				getLayout().setBackgroundDrawable(bitmap);
			}
		}
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_review_options, menu);
	}

	private ReviewDataOption getOption(GVType dataType) {
		return ReviewDataOptions.get(dataType);
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
	
	private void requestIntent(ReviewDataOption option) {
		Intent i = new Intent(getActivity(), option.getActivityRequestClass());
		Controller.pack(getController(), i);
		startActivityForResult(i, option.getActivityRequestCode());
	}

	private <T> void requestIntent(Class<T> c, int requestCode, Intent data) {
		Intent i = new Intent(getActivity(), c);
		i.putExtras(data);
		startActivityForResult(i, requestCode);
	}

	private void showQuickDialog(ReviewDataOption option) {
		DialogFragment dialog = option.getDialogFragment();
		dialog.setTargetFragment(FragmentReviewOptions.this, option.getDialogRequestCode());
		Bundle args = Controller.pack(getController());
		args.putBoolean(DialogAddReviewDataFragment.QUICK_SET, true);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), option.getDialogTag());
	}
	
	private void showQuickImageDialog() {
		if(mHelperReviewImage == null)
			mHelperReviewImage = HelperReviewImage.getInstance(getController());
		
        startActivityForResult(mHelperReviewImage.getImageChooserIntents(getActivity()), 
        		getOption(GVType.IMAGES).getActivityRequestCode());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ActivityResultCode resCode = ActivityResultCode.get(resultCode);
		if (requestCode == getOption(GVType.IMAGES).getActivityRequestCode()) {
			if(mHelperReviewImage.processOnActivityResult(getActivity(), resultCode, data))
				addImage();
		} else if (requestCode == getOption(GVType.LOCATIONS).getDialogRequestCode()) {
			if(resCode.equals(DialogLocationFragment.RESULT_MAP.getResultCode()))
				requestIntent(ActivityReviewLocationMap.class, LOCATION_MAP, data);
		} else if (requestCode == getOption(GVType.URLS).getDialogRequestCode()) {
			if(resCode.equals(DialogURLFragment.RESULT_BROWSE.getResultCode()))
				requestIntent(ActivityReviewURLBrowser.class, URL_BROWSE, data);
		} else if (requestCode == LOCATION_MAP) {
			if(resCode.equals(ActivityResultCode.DONE))
				addLocation(data);
		} else if (requestCode == URL_BROWSE) {
			if(resCode.equals(ActivityResultCode.DONE))
				addURL(data);
		} else {
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
		public GVCellManagerList() {
			super(null);
		}
		
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
						showQuickDialog(getOption(mDataType));
				}
				else
					requestIntent(getOption(mDataType));
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
				return vh.updateView(new GVString(mDataType.getDataString()));
			}

			public View getSingleDatumView(ViewGroup parent, int position) {
				if(position > getController().getData(mDataType).size())
					return getNoDatumView(parent);

				ViewHolder vh = getOption(mDataType).getViewHolder();
				vh.inflate(getActivity(), parent);
				return vh.updateView(getController().getData(mDataType).getItem(position));
			}
						
			public View getMultiDataView(ViewGroup parent) {
				ViewHolder vh = new VHDualStringView();
				vh.inflate(getActivity(), parent);
				return vh.updateView(new GVDualString(String.valueOf(getController().getData(mDataType).size()), mDataType.getDataString()));
			}
			
			public View getProConSummaryView(ViewGroup parent) {
				GVProConList pros = (GVProConList) getController().getData(GVType.PROS);
				GVProConList cons = (GVProConList) getController().getData(GVType.CONS);
				
				String proString = getResources().getString(R.string.text_view_pro_prefix);
				String conString = getResources().getString(R.string.text_view_con_prefix);
				
				if(pros.size() == 0)
					proString += GVType.PROS.getDataString();
				else
					proString += pros.size() == 1? pros.getItem(0).toString() : String.valueOf(pros.size()) + " " + GVType.PROS.getDataString();
				
				if(cons.size() == 0)
					conString += GVType.CONS.getDataString();
				else
					conString += cons.size() == 1? cons.getItem(0).toString() : String.valueOf(cons.size()) + " " + GVType.CONS.getDataString();
				
				ViewHolder vh = new VHDualStringView();
				vh.inflate(getActivity(), parent);
				
				return vh.updateView(new GVDualString(proString, conString));
			}
		}
	}
}
