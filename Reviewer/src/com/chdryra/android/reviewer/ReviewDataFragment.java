package com.chdryra.android.reviewer;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.reviewer.ReviewData.Datum;

public class ReviewDataFragment extends SherlockFragment {
	public static final int RESULT_DELETE_DATA = Activity.RESULT_FIRST_USER;
	
	public static final String DATUM_LABEL = "com.chdryra.android.reviewer.datum_label";
	public static final String DATUM_VALUE = "com.chdryra.android.reviewer.datum_vallue";	
	public static final String DIALOG_DATUM_TAG = "DatumDialog";
	
	private static final int DATUM_EDIT = 0;
	
	private Review mReview;
	
	private ClearableEditText mDatumLabel;
	private ClearableEditText mDatumValue;
	private ListView mDataListView;
	
	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;

	private boolean mIsKeyboardVisible = false;
	private ReviewData mReviewData = new ReviewData();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mReview = (Review)IntentObjectHolder.getObject(ReviewOptionsFragment.REVIEW_OBJECT);
		if( mReview.getData() != null )
			mReviewData = mReview.getData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_review_data, container, false);		
				
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDatumLabel = (ClearableEditText)v.findViewById(R.id.datum_label_edit_text);
		mDatumValue = (ClearableEditText)v.findViewById(R.id.datum_value_edit_text);
		mDataListView = (ListView)v.findViewById(R.id.data_listview);
		mDataListView.setAdapter(new ReviewDataAdaptor(mReviewData));
		updateUI();
		
		mDatumValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
					addDatum();
					
	            mDatumLabel.requestFocus();
	            return false;
	        }
	    });

		mDataListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				Datum datum = (Datum)parent.getItemAtPosition(pos);
				
				DatumDialogFragment dialog = new DatumDialogFragment();
				dialog.setTargetFragment(ReviewDataFragment.this, DATUM_EDIT);
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
				sendResult(RESULT_DELETE_DATA);
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
				if(mIsKeyboardVisible) {
					addDatum();
					RandomTextUtils.hideKeyboard(getSherlockActivity(), mDatumLabel);
					RandomTextUtils.hideKeyboard(getSherlockActivity(), mDatumValue);
				}
				else
					sendResult(Activity.RESULT_OK);
			}
		});
	    
//		v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//			
//			@Override
//			public void onGlobalLayout() {
//				 Rect r = new Rect();
//				    //r will be populated with the coordinates of your view that area still visible.
//				    v.getWindowVisibleDisplayFrame(r);
//
//				    int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
//				    if (heightDiff > 100) // if more than 100 pixels, its probably a keyboard...
//				    	mIsKeyboardVisible = true;
//				    else
//				    	mIsKeyboardVisible = false;
//			}
//		});

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
			mReviewData.addDatum(label, value);
			mDatumLabel.setText(null);
			mDatumValue.setText(null);
			updateUI();
		}
	}
	
	private void updateUI() {
		((ReviewDataAdaptor)mDataListView.getAdapter()).notifyDataSetChanged();
	}
	
	private void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE_DATA)
			mReview.deleteData();
		
		if(resultCode == Activity.RESULT_OK && mReviewData.size() > 0) {
			mReview.setData(mReviewData);
		}
		
		IntentObjectHolder.addObject(ReviewOptionsFragment.REVIEW_OBJECT, mReview);
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		
		if (resultCode == Activity.RESULT_OK && requestCode == DATUM_EDIT) {
			String oldLabel = (String)data.getSerializableExtra(DatumDialogFragment.DATUM_OLD_LABEL);
			String newLabel = (String)data.getSerializableExtra(DATUM_LABEL);
			String newValue = (String)data.getSerializableExtra(DATUM_VALUE);
			mReviewData.deleteDatum(oldLabel);
			mReviewData.addDatum(newLabel, newValue);
		}
			
		if (resultCode == DatumDialogFragment.RESULT_DELETE) {
			String toDelete = (String)data.getSerializableExtra(DatumDialogFragment.DATUM_OLD_LABEL);
			mReviewData.deleteDatum(toDelete);
		}		

		mReview.setData(mReviewData);
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
		private ReviewData mData;
	
		public ReviewDataAdaptor(ReviewData data){
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
			HashMap<String, Datum> map = mData.getDataMap();
			String[] keys = map.keySet().toArray(new String[map.size()]);
			return map.get(keys[position]);
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