package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public abstract class FragmentReviewGrid<T extends GVData> extends FragmentDeleteDone{
	public enum CellDimension{FULL, HALF, QUARTER}; 

	private ControllerReviewNode mController;
	
	private LinearLayout mLayout;
	private TextView mSubjectView;
	private RatingBar mTotalRatingBar;
	private Button mBannerButton;
	private GridView mGridView;

	int mMaxGridCellWidth;
	int mMaxGridCellHeight;
	
	int mCellWidthDivider = 1;
	int mCellHeightDivider = 1;

	private boolean mIsEditable = false;
	private String mBannerButtonText;

	private GVReviewDataList<T> mGridData;
	private boolean mReviewInProgress = false;
	private boolean mCancelUpdateUIOnActivityResult = false;
	private Class<? extends Activity> mOnDoneActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		
		if(mController == null) {
			setController(Controller.addNewReviewInProgress());
			mReviewInProgress = true;
		}
		
		setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
		setBannerButtonText(getResources().getString(R.string.button_add_text));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.fragment_review_grid, container, false);			
		
		mLayout = (LinearLayout)v.findViewById(R.id.review_grid_linearlayout);
		mSubjectView = (ClearableEditText)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.review_rating_bar);
		mBannerButton = (Button)v.findViewById(R.id.banner_button);
		mGridView = (GridView)v.findViewById(R.id.gridview_data);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);		
		mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels);				
		mMaxGridCellHeight = mMaxGridCellWidth;
		
		initUI();
		updateUI();
		
		return v;		
	}
	
	protected void initUI() {
		initSubjectUI();
		initRatingBarUI();
		initBannerDataUI();
		initDataGridUI();
	}
	
	protected void initSubjectUI() {
		if(isEditable()) {
			getSubjectView().setFocusable(true);
			((ClearableEditText)getSubjectView()).makeClearable(true);
			getSubjectView().addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
				
				@Override
				public void afterTextChanged(Editable s) {
					if(s.toString().length() > 0)
						getController().setTitle(s.toString());
				}
			});
		} else {
			getSubjectView().setFocusable(false);
			((ClearableEditText)getSubjectView()).makeClearable(false);
		}
	}
	
	protected void initRatingBarUI() {
		if(isEditable()) {
			getTotalRatingBar().setIsIndicator(false);
			getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
						getController().setRating(rating);
				}
			});
		} else
			getTotalRatingBar().setIsIndicator(true);
	}

	protected void initBannerDataUI() {
		getBannerButton().setText(getBannerButtonText());
		getBannerButton().setTextColor(getSubjectView().getTextColors().getDefaultColor());
		getBannerButton().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBannerButtonClick();
			}
		});
	}
	
	protected void initDataGridUI(){
		getGridView().setAdapter(getGridViewCellAdapter());
		getGridView().setColumnWidth(getGridCellWidth());
		getGridView().setNumColumns(getNumberColumns());
		getGridView().setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            onGridItemClick(parent, v, position, id);
	        }
	    });
		getGridView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	            onGridItemLongClick(parent, v, position, id);
	            return true;
	        }
	    });
	};

	protected void onBannerButtonClick() {
		
	}
	
	protected void setBannerButtonText(String buttonText) {
		mBannerButtonText = buttonText;
	}
	
	protected String getBannerButtonText() {
		return mBannerButtonText;
	}
	
	protected void setIsEditable(boolean isEditable) {
		mIsEditable = isEditable;
	}
	
	protected boolean isEditable() {
		return mIsEditable;
	}
	
	protected void setGridViewData(GVReviewDataList<T> gridData) {
		mGridData = gridData;
	}
	
	protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
		
	}
	
	protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
		getGridView().performItemClick(v, position, id);
	}
	
	protected void updateUI() {
		updateSubjectTextUI();
		updateRatingBarUI();
		updateBannerButtonUI();
		updateGridDataUI();
	}

	protected void updateSubjectTextUI() {
		if(getController() != null)
			getSubjectView().setText(getController().getTitle());
	}
	
	protected void updateRatingBarUI() {
		if(getController() != null)
			getTotalRatingBar().setRating(getController().getRating());
	}

	protected void updateBannerButtonUI() {
		
	}
	
	protected void updateGridDataUI() {
		((GridViewCellAdapter)getGridView().getAdapter()).notifyDataSetChanged();
	}

	protected void setGridCellDimension(CellDimension width, CellDimension height) {
		mCellWidthDivider = 1;
		mCellHeightDivider = 1;
		
		if(width == CellDimension.HALF)
			mCellWidthDivider = 2;
		else if(width == CellDimension.QUARTER)
			mCellWidthDivider = 4;
		
		if(height == CellDimension.HALF)
			mCellHeightDivider = 2;
		else if(height == CellDimension.QUARTER)
			mCellHeightDivider = 4;
	}
	
	protected int getGridCellWidth() {
		return mMaxGridCellWidth / mCellWidthDivider;
	}

	protected int getGridCellHeight() {
		return mMaxGridCellHeight / mCellHeightDivider;
	}
	
	protected int getNumberColumns() {
		return mCellWidthDivider;
	}
	
	protected LinearLayout getLayout() {
		return mLayout;
	}
	
	protected TextView getSubjectView() {
		return mSubjectView;
	}
	
	protected String getSubjectText() {
		return getSubjectView().getText().toString();
	}
	
	protected float getRating() {
		return getTotalRatingBar().getRating();
	}
	
	protected RatingBar getTotalRatingBar() {
		return mTotalRatingBar;
	}
	
	protected Button getBannerButton() {
		return mBannerButton;
	}
	
	protected GridView getGridView() {
		return mGridView;
	}
	
	protected GVReviewDataList<T> getGridData() {
		return mGridData;
	}

	protected GridViewCellAdapter getGridViewCellAdapter() {
		return new GridViewCellAdapter(getActivity(), mGridData, getGridCellWidth(), getGridCellHeight());
	}
	
	protected ControllerReviewNode getController() {
		return mController;
	}
	
	protected void setController(ControllerReviewNode controller) {
		mController = controller;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = super.onOptionsItemSelected(item);
		return ret;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(!mCancelUpdateUIOnActivityResult)
			updateUI();
		mCancelUpdateUIOnActivityResult = false;
	}
	
	public void cancelUpdateUIOnActivityResult() {
		mCancelUpdateUIOnActivityResult = true;
	}
		
	@Override
	protected void onDoneSelected() {
		getController().setData(mGridData);
		
		if(mOnDoneActivity != null) {
			Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
			Controller.pack(getController(), i);
			startActivity(i);
		}
	}

	protected void setOnDoneActivity(Class<? extends Activity> onDoneActivity) {
		mOnDoneActivity = onDoneActivity;
	}
	
	@Override
	protected void onDeleteSelected() {
		mGridData.removeAll();
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mGridData.size() > 0;
	}
	
	@Override
	protected void onUpSelected() {
		if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
			Intent i = NavUtils.getParentActivityIntent(getSherlockActivity());
			if(!mReviewInProgress)
				Controller.pack(getController(), i);
			NavUtils.navigateUpTo(getActivity(), i);
		}
	}
}
