package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.LocationClientConnector.Locatable;
import com.chdryra.android.mygenerallibrary.LocationNameAdapter;
import com.google.android.gms.maps.model.LatLng;

public class DialogLocationFragment extends DialogDeleteCancelDoneFragment implements Locatable{
	public static final ActivityResultCode RESULT_MAP = ActivityResultCode.OTHER;
	
	private ControllerReviewNode mController;
	private ClearableEditText mNameEditText;
	private ListView mLocationNameSuggestions;
	private LatLng mLatLng;
	private LocationClientConnector mLocationClient;
	private LocationNameAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getArguments());
		mLocationClient = new LocationClientConnector(getSherlockActivity(), this);
		
		setMiddleButtonAction(ActionType.OTHER);
		setMiddleButtonText(getResources().getString(R.string.button_map_text));
		setDismissDialogOnMiddleClick(true);
		
		setDialogTitle(getResources().getString(R.string.dialog_location_title));
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_location_title));
	}
	
	@Override
	protected View createDialogUI() {
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

		setKeyboardIMEDoDone(mNameEditText);
		
		mLocationNameSuggestions = (ListView)v.findViewById(R.id.suggestions_list_view);
		mLocationNameSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String locationName = (String)parent.getAdapter().getItem(position);
				mNameEditText.setText(locationName);
			}
		});

		setSuggestionsAdapter();

		return v;
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
	protected void onDoneButtonClick() {
		String locationName = mNameEditText.getText().toString();
		if(mLatLng != null && locationName.length() > 0)
			mController.setLocation(mLatLng, locationName);
		super.onDoneButtonClick();
	}
		
	@Override
	protected void onDeleteButtonClick() {
		mController.deleteLocation();
		super.onDeleteButtonClick();
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mController.hasLocation();
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
