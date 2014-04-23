package com.chdryra.android.reviewer;

import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewFacts extends SherlockFragment {
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	
	public static final String DATUM_LABEL = "com.chdryra.android.reviewer.datum_label";
	public static final String DATUM_VALUE = "com.chdryra.android.reviewer.datum_value";	
	public static final String DIALOG_DATUM_TAG = "DatumDialog";

	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	private static final int DATUM_EDIT = DELETE_CONFIRM + 1;
	
	private ControllerReviewNode mController;
	
	private LinkedHashMap<String, String> mFacts; 
	
	private ClearableEditText mDatumLabel;
	private ClearableEditText mDatumValue;
	private ListView mDataListView;
	
	private boolean mDeleteConfirmed = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mFacts = mController.hasFacts()? mController.getFacts() :  new LinkedHashMap<String, String>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_review_facts, container, false);		
				
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);		
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDatumLabel = (ClearableEditText)v.findViewById(R.id.datum_label_edit_text);
		mDatumValue = (ClearableEditText)v.findViewById(R.id.datum_value_edit_text);
		mDataListView = (ListView)v.findViewById(R.id.data_listview);
		mDataListView.setAdapter(new ReviewFactsAdaptor(mFacts));
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
				ReviewFactsAdaptor adapter = (ReviewFactsAdaptor)parent.getAdapter();
				String label = (String)adapter.getKey(pos);
				String value = (String)adapter.getItem(pos);
				
				DialogDatumFragment dialog = new DialogDatumFragment();
				dialog.setTargetFragment(FragmentReviewFacts.this, DATUM_EDIT);
				Bundle args = new Bundle();
				args.putString(DATUM_LABEL, label);
				args.putString(DATUM_VALUE, value);
				dialog.setArguments(args);
				dialog.show(getFragmentManager(), DIALOG_DATUM_TAG);
				
				return true;
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
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_label), Toast.LENGTH_SHORT).show();
		else if(value == null || value.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_value), Toast.LENGTH_SHORT).show();
		else {
			mFacts.put(label, value);
			mDatumLabel.setText(null);
			mDatumValue.setText(null);
			updateUI();
		}
	}
	
	private void updateUI() {
		((ReviewFactsAdaptor)mDataListView.getAdapter()).notifyDataSetChanged();
	}
	
	private void sendResult(int resultCode) {
		if (resultCode == RESULT_DELETE && mController.hasFacts()) {
			if(mDeleteConfirmed)
				mController.deleteFacts();
			else {
				DialogBasicFragment.showDeleteConfirmDialog(getResources().getString(R.string.facts_activity_title), 
						FragmentReviewFacts.this, DELETE_CONFIRM, getFragmentManager());
				return;
			}
		}
		
		if(resultCode == Activity.RESULT_OK && mFacts.size() > 0)
			mController.setFacts(mFacts);
			
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
						mFacts.remove(oldLabel);
						mFacts.put(newLabel, newValue);
						break;
					case DialogDatumFragment.RESULT_DELETE:
						String toDelete = (String)data.getSerializableExtra(DialogDatumFragment.DATUM_OLD_LABEL);
						mFacts.remove(toDelete);
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
		
		updateUI();				
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_facts, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sendResult(Activity.RESULT_CANCELED);
			break;
		case R.id.menu_item_delete:
			sendResult(RESULT_DELETE);
			break;
		case R.id.menu_item_done:
			sendResult(Activity.RESULT_OK);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	class ReviewFactsAdaptor extends BaseAdapter {	
		private LinkedHashMap<String, String> mData;
	
		public ReviewFactsAdaptor(LinkedHashMap<String, String> data){
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
			return mData.get(getKey(position));
		}
		
		public String getKey(int position) {
			String[] keys = mData.keySet().toArray(new String[mData.size()]);
			return keys[position];
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
				
			String label = (String)getKey(position);
			String value = (String)getItem(position);
			
			vh.datumName.setText(label +":");
			vh.datumValue.setText(value);
			
			vh.datumName.setTextColor(mDatumLabel.getTextColors().getDefaultColor());
			vh.datumValue.setTextColor(mDatumLabel.getTextColors().getDefaultColor());
			
			return(convertView);
		};
	};
	
	static class ViewHolder {
	    public TextView datumName;
	    public TextView datumValue;
	}
}
