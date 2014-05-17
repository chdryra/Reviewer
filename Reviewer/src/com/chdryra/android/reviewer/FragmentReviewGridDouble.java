package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;

public abstract class FragmentReviewGridDouble extends FragmentDeleteDone{

public enum CellDimension{FULL, HALF, QUARTER}; 

	private ControllerReviewNode mController;
	
	private TextView mSubjectView;
	private RatingBar mTotalRatingBar;
	
	private Button mBannerButtonLeft;
	private Button mBannerButtonRight;
	private GridView mGridViewLeft;
	private GridView mGridViewRight;
	private String mBannerButtonTextLeft;
	private String mBannerButtonTextRight;
	
	int mMaxGridCellWidth;
	int mMaxGridCellHeight;
	
	int mCellWidthDivider = 1;
	int mCellHeightDivider = 1;

	private boolean mIsEditable = false;
	
	
	protected abstract GridViewCellAdapter getGridViewCellAdapterLeft();
	protected abstract GridViewCellAdapter getGridViewCellAdapterRight();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		setGridCellDimension(CellDimension.FULL, CellDimension.HALF);
		setBannerButtonTextLeft(getResources().getString(R.string.button_add_text));
		setBannerButtonTextRight(getResources().getString(R.string.button_add_text));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.fragment_review_grid_double, container, false);			

		mSubjectView = (TextView)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		
		View vl = v.findViewById(R.id.banner_gridview_left);
		View vr = v.findViewById(R.id.banner_gridview_right);
		mBannerButtonLeft = (Button)vl.findViewById(R.id.banner_button);
		mGridViewLeft = (GridView)vl.findViewById(R.id.data_gridview);
		mBannerButtonRight = (Button)vr.findViewById(R.id.banner_button);
		mGridViewRight = (GridView)vr.findViewById(R.id.data_gridview);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);		
		mMaxGridCellWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels)/2;				
		mMaxGridCellHeight = mMaxGridCellWidth;
		
		initUI();
		updateUI();
		
		return v;		
	}
	
	protected void initUI() {
		initSubjectUI();
		initRatingBarUI();
		initBannerDataUILeft();
		initDataGridUILeft();
		initBannerDataUIRight();
		initDataGridUIRight();
	}
	
	protected void initSubjectUI() {
		if(isEditable()) {
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
		}
	}
	
	protected void initRatingBarUI() {
		if(isEditable()) {
			getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
						getController().setRating(rating);
				}
			});
		}
	}

	protected void initBannerDataUILeft() {
		getBannerButtonLeft().setText(getBannerButtonTextLeft());
		getBannerButtonLeft().setTextColor(getSubjectView().getTextColors().getDefaultColor());
		getBannerButtonLeft().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBannerButtonClickLeft();
			}
		});
	}
	
	protected void initBannerDataUIRight() {
		getBannerButtonRight().setText(getBannerButtonTextRight());
		getBannerButtonRight().setTextColor(getSubjectView().getTextColors().getDefaultColor());
		getBannerButtonRight().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBannerButtonClickRight();
			}
		});
	}
	
	protected void initDataGridUILeft(){
		getGridViewLeft().setAdapter(getGridViewCellAdapterLeft());
		getGridViewLeft().setColumnWidth(getGridCellWidth());
		getGridViewLeft().setNumColumns(getNumberColumns());
		getGridViewLeft().setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            onGridItemClickLeft(parent, v, position, id);
	        }
	    });
		getGridViewLeft().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	            getGridViewLeft().performItemClick(v, position, id);
	            return true;
	        }
	    });
	};

	protected void initDataGridUIRight(){
		getGridViewRight().setAdapter(getGridViewCellAdapterRight());
		getGridViewRight().setColumnWidth(getGridCellWidth());
		getGridViewRight().setNumColumns(getNumberColumns());
		getGridViewRight().setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            onGridItemClickRight(parent, v, position, id);
	        }
	    });
		getGridViewRight().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
	            getGridViewRight().performItemClick(v, position, id);
	            return true;
	        }
	    });
	};

	protected void onBannerButtonClickLeft() {
		
	}
	
	protected void onBannerButtonClickRight() {
		
	}
	
	protected void setBannerButtonTextLeft(String buttonText) {
		mBannerButtonTextLeft = buttonText;
	}
	
	protected void setBannerButtonTextRight(String buttonText) {
		mBannerButtonTextRight = buttonText;
	}
	
	protected String getBannerButtonTextLeft() {
		return mBannerButtonTextLeft;
	}
	
	protected String getBannerButtonTextRight() {
		return mBannerButtonTextRight;
	}
	
	protected void setIsEditable(boolean isEditable) {
		mIsEditable = isEditable;
	}
	
	protected boolean isEditable() {
		return mIsEditable;
	}
	
	protected void onGridItemClickLeft(AdapterView<?> parent, View v, int position, long id) {
		
	}
	
	protected void onGridItemClickRight(AdapterView<?> parent, View v, int position, long id) {
		
	}
	
	protected void updateUI() {
		updateSubjectTextUI();
		updateRatingBarUI();
		updateBannerButtonUILeft();
		updateGridDataUILeft();
		updateBannerButtonUIRight();
		updateGridDataUIRight();
	}

	protected void updateSubjectTextUI() {
		if(getController() != null)
			getSubjectView().setText(getController().getTitle());
	}
	
	protected void updateRatingBarUI() {
		if(getController() != null)
			getTotalRatingBar().setRating(getController().getRating());
	}

	protected void updateBannerButtonUILeft() {
		
	}
	
	protected void updateBannerButtonUIRight() {
		
	}
	
	protected void updateGridDataUILeft() {
		((GridViewCellAdapter)getGridViewLeft().getAdapter()).notifyDataSetChanged();
	}

	protected void updateGridDataUIRight() {
		((GridViewCellAdapter)getGridViewRight().getAdapter()).notifyDataSetChanged();
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
	
	protected TextView getSubjectView() {
		return mSubjectView;
	}
	
	protected RatingBar getTotalRatingBar() {
		return mTotalRatingBar;
	}
	
	protected Button getBannerButtonLeft() {
		return mBannerButtonLeft;
	}
	
	protected Button getBannerButtonRight() {
		return mBannerButtonRight;
	}
	
	protected GridView getGridViewLeft() {
		return mGridViewLeft;
	}

	protected GridView getGridViewRight() {
		return mGridViewRight;
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
		updateUI();
		return ret;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateUI();
	}
}
