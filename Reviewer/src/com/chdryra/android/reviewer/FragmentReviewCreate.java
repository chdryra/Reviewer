package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Collections;

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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewCreate  extends SherlockFragment {

	public final static String TAG_EDIT_STRING = "com.chdryra.android.reviewer.tag_edit_string";
	
	private final static String DIALOG_TAG_ADD_TAG = "TagAddDialog";
	private final static String DIALOG_TAG_EDIT_TAG = "TagEditDialog";
	
	public final static int TAG_ADD = 10;
	public final static int TAG_EDIT = 11;
	
	private ControllerReviewNode mController;
	
	private ClearableEditText mSubjectEditText;
	private RatingBar mTotalRatingBar;
	private GridView mTagsGridView;
	
	int mMaxButtonWidth;
	int mMaxButtonHeight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		if(mController == null)
			mController = Controller.addNewReviewInProgress();
		
		setHasOptionsMenu(true);		
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_create, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mSubjectEditText = (ClearableEditText)v.findViewById(R.id.review_subject_edit_text);
		mTotalRatingBar = (RatingBar)v.findViewById(R.id.total_rating_bar);
		mTagsGridView = (GridView)v.findViewById(R.id.tags_gridview);
		
		//***Get display metrics for reviewTag display size***//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		mMaxButtonWidth = Math.min(displaymetrics.widthPixels, displaymetrics.heightPixels) / 2;				
		mMaxButtonHeight = mMaxButtonWidth / 2;
		
		initUI();
		updateUI();
		
		return v;
	}
	
	private void initUI() {
		initSubjectUI();
		initRatingBarUI();
		initTagsUI();
	}
	
	private void initSubjectUI() {
		mSubjectEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length() > 0)
					mController.setTitle(s.toString());
			}
		});
		
	}
	
	private void initRatingBarUI() {
		mTotalRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					mController.setRating(rating);
			}
		});
	}

	private void initTagsUI(){
		ArrayList<String> tags = mController.getTags();
		tags.add("add tag");
		mTagsGridView.setAdapter(new ReviewTagAdaptor(getActivity(), 
				tags, mMaxButtonWidth, mMaxButtonHeight, mSubjectEditText.getTextColors().getDefaultColor()));
		mTagsGridView.setColumnWidth(mMaxButtonWidth);
		mTagsGridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            if(position == 0)
	            	showTagDialog();
	            else
	            	showTagDialog((String)parent.getItemAtPosition(position));
	        }
	    });
};
	
	private void updateUI() {
		updateSubjectTextUI();
		updateRatingBarUI();
		updateTagsUI();
	}

	private void updateSubjectTextUI() {
		mSubjectEditText.setText(mController.getTitle());
	}
	
	private void updateRatingBarUI() {
		mTotalRatingBar.setRating(mController.getRating());
	}
	
	private void updateTagsUI() {
		ArrayList<String> tags = mController.getTags();
		Collections.sort(tags);
		tags.add(0, getResources().getString(R.string.text_view_tag));
		((ReviewTagAdaptor)mTagsGridView.getAdapter()).setData(tags);
	}
	
	private void showTagDialog() {
		showDialog(new DialogTagAddFragment(), TAG_ADD, DIALOG_TAG_ADD_TAG, Controller.pack(mController));
	}
	
	private void showTagDialog(String tag) {
		Bundle args = Controller.pack(mController);
		args.putString(TAG_EDIT_STRING, tag);
		showDialog(new DialogTagEditFragment(), TAG_EDIT, DIALOG_TAG_EDIT_TAG, args);
	}
	
	private void showDialog(SherlockDialogFragment dialog, int requestCode, String tag, Bundle args) {
		dialog.setTargetFragment(FragmentReviewCreate.this, requestCode);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), tag);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_delete_done, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_done:
			Intent i = new Intent(getActivity(), ActivityReviewEdit.class);
			Controller.pack(mController, i);
			startActivity(i);
			return true;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				Intent j = NavUtils.getParentActivityIntent(getSherlockActivity());
				NavUtils.navigateUpTo(getActivity(), j);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED)
			return;
		
		updateTagsUI();
	}
	
	class ReviewTagAdaptor extends BaseAdapter {	
		private Activity mActivity;
		private ArrayList<String> mData;
		private int mCellWidth;
		private int mCellHeight;
		private int mTextColour;
		
		public ReviewTagAdaptor(Activity activity, ArrayList<String> tags, int cellWidth, int cellHeight, int textColour){
		    mActivity = activity;
			mData = tags;
			mCellWidth = cellWidth;
			mCellHeight = cellHeight;
		    mTextColour = textColour;
		}
		
		public void setData(ArrayList<String> tags) {
			mData = tags;
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
			return mData.get(position);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			
			if (convertView == null) {						
				LayoutInflater inflater = mActivity.getLayoutInflater();
				convertView = inflater.inflate(R.layout.tag_view, parent, false);
				convertView.getLayoutParams().height = mCellHeight;
				convertView.getLayoutParams().width = mCellWidth;
				
				TextView tag = (TextView)convertView.findViewById(R.id.tag_text_view);
				
				vh = new ViewHolder();
				vh.reviewTag = tag;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			String tag = (String)getItem(position);
			
			vh.reviewTag.setText(tag);			
			vh.reviewTag.setTextColor(mTextColour);
			
			return(convertView);
		};
		
	};
	
	static class ViewHolder {
	    public TextView reviewTag;
	}

}
