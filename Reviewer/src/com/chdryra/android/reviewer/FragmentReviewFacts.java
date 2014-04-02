package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewFacts extends SherlockFragment {
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	
	public static final String DATUM_LABEL = "com.chdryra.android.reviewer.datum_label";
	public static final String DATUM_VALUE = "com.chdryra.android.reviewer.datum_vallue";	
	public static final String DIALOG_DATUM_TAG = "DatumDialog";

	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	private static final int DATUM_EDIT = DELETE_CONFIRM + 1;
	
	private UserReview mUserReview;
	private ReviewFacts mReviewFacts = new ReviewFacts();
	
	private ClearableEditText mDatumLabel;
	private ClearableEditText mDatumValue;
	private ListView mDataListView;
	
	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;

	private boolean mDeleteConfirmed = false;
	private boolean mIsKeyboardVisible = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mUserReview = getArguments().getParcelable(FragmentReviewOptions.REVIEW_OBJECT);
		if(mUserReview.hasFacts())
			mReviewFacts = mUserReview.getFacts();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_review_data, container, false);		
				
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDatumLabel = (ClearableEditText)v.findViewById(R.id.datum_label_edit_text);
		mDatumValue = (ClearableEditText)v.findViewById(R.id.datum_value_edit_text);
		mDataListView = (ListView)v.findViewById(R.id.data_listview);
		mDataListView.setAdapter(new ReviewDataAdaptor(mReviewFacts));
		updateUI();
		
		mDatumValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
					addDatum();
					
	            mDatumLabel.requestFocus();
	            return true;
	        }
	    });

		mDataListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				Datum datum = (Datum)parent.getItemAtPosition(pos);
				
				DialogDatumFragment dialog = new DialogDatumFragment();
				dialog.setTargetFragment(FragmentReviewFacts.this, DATUM_EDIT);
				Bundle args = new Bundle();
				args.putString(DATUM_LABEL, datum.getLabel());
				args.putString(DATUM_VALUE, datum.getValue());
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), DIALOG_DATUM_TAG);
				
				return false;
			}
		});
	
		mDeleteButton = (Button)v.findViewById(R.id.button_map_delete);
	    mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(RESULT_DELETE);
			}
		});
	    
	    mCancelButton = (Button)v.findViewById(R.id.button_map_cancel);
	    mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_CANCELED);
			}
		});
	    
	    mDoneButton = (Button)v.findViewById(R.id.button_map_done);
	    mDoneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mIsKeyboardVisible)
					addDatum();

				sendResult(Activity.RESULT_OK);
			}
		});
	    
		v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				 Rect r = new Rect();
				    //r will be populated with the coordinates of your view that area still visible.
				    v.getWindowVisibleDisplayFrame(r);

				    int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
				    if (heightDiff > 100) // if more than 100 pixels, its probably a keyboard...
				    	mIsKeyboardVisible = true;
				    else
				    	mIsKeyboardVisible = false;
			}
		});

		return v;
	}
	
	private void addDatum() {
		String label = mDatumLabel.getText().toString();
		String value = mDatumValue.getText().toString();
		if((label == null || label.length() == 0) && (value == null || value.length() == 0))
			return;
		
		if(label == null || label.length() == 0)
			Toast.makeText(getSherlockActivity(), "Please enter a label...", Toast.LENGTH_SHORT).show();
		else if(value == null || value.length() == 0)
			Toast.makeText(getSherlockActivity(), "Please enter a value...", Toast.LENGTH_SHORT).show();
		else {
			mReviewFacts.put(label, value);
			mDatumLabel.setText(null);
			mDatumValue.setText(null);
			updateUI();
		}
	}
	
	private void updateUI() {
		((ReviewDataAdaptor)mDataListView.getAdapter()).notifyDataSetChanged();
	}
	
	private void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE && mUserReview.hasFacts()) {
			if(mDeleteConfirmed)
				mUserReview.deleteFacts();
			else {
				DialogBasicFragment.showDeleteConfirmDialog(getResources().getString(R.string.data_activity_title), 
						FragmentReviewFacts.this, getFragmentManager());
				return;
			}
		}
		
		if(resultCode == Activity.RESULT_OK && mReviewFacts.size() > 0) {
			mUserReview.setFacts(mReviewFacts);
		}
			
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		switch(requestCode) {
			case DATUM_EDIT:
				switch(resultCode) {
					case Activity.RESULT_OK:
						String oldLabel = (String)data.getSerializableExtra(DialogDatumFragment.DATUM_OLD_LABEL);
						String newLabel = (String)data.getSerializableExtra(DATUM_LABEL);
						String newValue = (String)data.getSerializableExtra(DATUM_VALUE);
						mReviewFacts.remove(oldLabel);
						mReviewFacts.put(newLabel, newValue);
						break;
					case DialogDatumFragment.RESULT_DELETE:
						String toDelete = (String)data.getSerializableExtra(DialogDatumFragment.DATUM_OLD_LABEL);
						mReviewFacts.remove(toDelete);
						break;
					default:
						return;
				}
				break;
			case DELETE_CONFIRM:
				switch(resultCode) {
					case Activity.RESULT_OK:
						mDeleteConfirmed = true;
						sendResult(RESULT_DELETE);
						return;
					default:
						return;
				}
			default:
				break;
		}
		
		mUserReview.setFacts(mReviewFacts);
		updateUI();				
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sendResult(Activity.RESULT_CANCELED);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class ReviewDataAdaptor extends BaseAdapter {	
		private ReviewFacts mData;
	
		public ReviewDataAdaptor(ReviewFacts data){
		    mData = data;
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
			ViewHolder vh;
			
			if (convertView == null) {						
				LayoutInflater inflater = getSherlockActivity().getLayoutInflater();
				convertView = inflater.inflate(R.layout.datum_linear_row, parent, false);
				
				TextView datumName = (TextView)convertView.findViewById(R.id.datum_label_text_view);
				TextView datumValue = (TextView)convertView.findViewById(R.id.datum_value_text_view);
				datumValue.setGravity(Gravity.RIGHT);
				
				vh = new ViewHolder();
				vh.datumName = datumName;
				vh.datumValue = datumValue;
				
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder)convertView.getTag();
			}
				
			Datum datum = (Datum)getItem(position);
			
			vh.datumName.setText(datum.getLabel() +":");
			vh.datumValue.setText(datum.getValue());
	
			return(convertView);
		};
	};
	
	static class ViewHolder {
	    public TextView datumName;
	    public TextView datumValue;
	}
}