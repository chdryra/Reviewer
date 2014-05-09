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

import com.chdryra.android.myandroidwidgets.ClearableEditText;

public class FragmentReviewFacts extends FragmentReviewGrid {
	public static final String DATUM_LABEL = "com.chdryra.android.reviewer.datum_label";
	public static final String DATUM_VALUE = "com.chdryra.android.reviewer.datum_value";	
	public static final String DIALOG_DATUM_TAG = "DatumDialog";

	private static final int DATUM_EDIT = 4;
	
	private ControllerReviewNode mController;
	
	private LinkedHashMap<String, String> mFacts; 
	
	private ClearableEditText mFactLabel;
	private ClearableEditText mFactValue;
	private ListView mFactsListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		mFacts = mController.hasFacts()? mController.getFacts() :  new LinkedHashMap<String, String>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_review_facts, container, false);			
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mFactLabel = (ClearableEditText)v.findViewById(R.id.datum_label_edit_text);
		mFactValue = (ClearableEditText)v.findViewById(R.id.datum_value_edit_text);
		mFactsListView = (ListView)v.findViewById(R.id.data_listview);
		
		initUI();
		updateUI();

		return v;
	}

	@Override
	protected void initUI() {
		getSherlockActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mFactsListView.setAdapter(new ReviewFactsAdaptor(mFacts));
		mFactValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_GO)
					addFact();
					
	            mFactLabel.requestFocus();
	            return true;
	        }
	    });

		mFactsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
				ReviewFactsAdaptor adapter = (ReviewFactsAdaptor)parent.getAdapter();
				String label = (String)adapter.getKey(pos);
				String value = (String)adapter.getItem(pos);
				showFactEditDialog(label, value);
				
				return true;
			}
		});
	}
	
	private void showFactEditDialog(String label, String value) {
		DialogFactFragment dialog = new DialogFactFragment();
		dialog.setTargetFragment(FragmentReviewFacts.this, DATUM_EDIT);
		Bundle args = new Bundle();
		args.putString(DATUM_LABEL, label);
		args.putString(DATUM_VALUE, value);
		dialog.setArguments(args);
		dialog.show(getFragmentManager(), DIALOG_DATUM_TAG);	
	}
	
	private void addFact() {
		String label = mFactLabel.getText().toString();
		String value = mFactValue.getText().toString();
		if((label == null || label.length() == 0) && (value == null || value.length() == 0))
			return;
		
		if(label == null || label.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_label), Toast.LENGTH_SHORT).show();
		else if(value == null || value.length() == 0)
			Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_enter_value), Toast.LENGTH_SHORT).show();
		else {
			mFacts.put(label, value);
			mFactLabel.setText(null);
			mFactValue.setText(null);
			updateUI();
		}
	}
	
	@Override
	protected void updateUI() {
		((ReviewFactsAdaptor)mFactsListView.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void sendResult(int resultCode) {		
		if(resultCode == Activity.RESULT_OK && mFacts.size() > 0)
			mController.setFacts(mFacts);
			
		super.sendResult(resultCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case DATUM_EDIT:
				switch(resultCode) {
					case Activity.RESULT_OK:
						String oldLabel = (String)data.getSerializableExtra(DialogFactFragment.DATUM_OLD_LABEL);
						String newLabel = (String)data.getSerializableExtra(DATUM_LABEL);
						String newValue = (String)data.getSerializableExtra(DATUM_VALUE);
						mFacts.remove(oldLabel);
						mFacts.put(newLabel, newValue);
						break;
					case DialogFactFragment.RESULT_DELETE:
						String toDelete = (String)data.getSerializableExtra(DialogFactFragment.DATUM_OLD_LABEL);
						mFacts.remove(toDelete);
						break;
					default:
						return;
				}
				break;
			
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
		}
		
		updateUI();				
	}

	@Override
	protected void deleteData() {
		mController.deleteFacts();
	}

	@Override
	protected boolean hasData() {
		return mController.hasFacts();
	}

	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.activity_title_facts);
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
				convertView = inflater.inflate(R.layout.fact_linear_row, parent, false);
				
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
			
			vh.datumName.setTextColor(mFactLabel.getTextColors().getDefaultColor());
			vh.datumValue.setTextColor(mFactLabel.getTextColors().getDefaultColor());
			
			return(convertView);
		};
	};
	
	static class ViewHolder {
	    public TextView datumName;
	    public TextView datumValue;
	}
}
