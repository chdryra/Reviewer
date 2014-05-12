package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.LocationClientConnector.Locatable;
import com.chdryra.android.mygenerallibrary.LocationNameAdapter;
import com.google.android.gms.maps.model.LatLng;

public class DialogLocationFragment extends DialogEditFragment implements Locatable{
	public static final int RESULT_MAP = 4;
	
	private ControllerReviewNode mController;
	private ClearableEditText mNameEditText;
	private ListView mLocationNameSuggestions;
	private LatLng mLatLng;
	private LocationClientConnector mLocationClient;
	private LocationNameAdapter mAdapter;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		mLocationClient = new LocationClientConnector(getSherlockActivity(), this);
		
		mController = Controller.unpack(getArguments());
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_location, null);
		
		mNameEditText = (ClearableEditText)v.findViewById(R.id.location_edit_text);
		if(mController.hasLocation())
			mLatLng = mController.getLocationLatLng();
		else if(mController.hasImageLatLng())
			mLatLng = mController.getImageLatLng();
		else
			mLocationClient.connect();

		if(mController.hasLocationName())
			mNameEditText.setText(mController.getLocationName());

		mNameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(mAdapter != null)
					mAdapter.getFilter().filter(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});

		mNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	        @Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
	        {
	            if(actionId == EditorInfo.IME_ACTION_DONE)
	            	sendResult(Activity.RESULT_OK);	            	     
	            
	            return true;
	        }
	    });
		
		mLocationNameSuggestions = (ListView)v.findViewById(R.id.suggestions_list_view);
		mLocationNameSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String locationName = (String)parent.getAdapter().getItem(position);
				mNameEditText.setText(locationName);
			}
		});

		setSuggestionsAdapter();

		final AlertDialog dialog = buildDialog(v);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		return dialog;
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mLocationClient.disconnect();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mLocationClient.disconnect();
	}
	
	@Override
	protected void sendResult(int resultCode) {
		if( resultCode == Activity.RESULT_OK ) {
			String locationName = mNameEditText.getText().toString();
			if(mLatLng != null && locationName.length() > 0)
				mController.setLocation(mLatLng, locationName);
		}

		super.sendResult(resultCode);
		dismiss();
	}
	
	@Override
	protected void deleteData() {
		mController.deleteLocation();
	}

	@Override
	protected boolean hasData() {
		return mController.hasLocation();
	}
	
	@Override
	protected String getDeleteConfirmationTitle() {
		return getResources().getString(R.string.dialog_location_title);
	}
	
	@Override
	protected AlertDialog buildDialog(View v, String title) {
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).
				setView(v).
				setPositiveButton(R.string.dialog_button_done_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				}).
				setNeutralButton(R.string.dialog_button_map_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_MAP);
					}
				}).
				setNegativeButton(R.string.dialog_button_delete_text, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_DELETE);
					}
				}).
				create(); 
		if(title != null)
			dialog.setTitle(title);
		
		return dialog;
	}
	
	private void setSuggestionsAdapter() {
		mAdapter = new LocationNameAdapter(getSherlockActivity(), android.R.layout.simple_list_item_1, mLatLng, 10, mController.getTitle());
		mLocationNameSuggestions.setAdapter(mAdapter);
	}

	@Override
	public void onLocated(LatLng latLng) {
	}

	@Override
	public void onLocationClientConnected(LatLng latLng) {
		mLatLng = latLng;
		setSuggestionsAdapter();
	}
}
