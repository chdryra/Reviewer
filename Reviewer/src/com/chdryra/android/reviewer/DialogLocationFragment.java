package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogDeleteCancelDoneFragment;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.LocationClientConnector.Locatable;
import com.chdryra.android.mygenerallibrary.LocationNameAdapter;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
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
		mLocationClient = new LocationClientConnector(getActivity(), this);
		
		setMiddleButtonAction(ActionType.OTHER);
		setMiddleButtonText(getResources().getString(R.string.button_map_text));
		setDismissDialogOnMiddleClick(true);
		
		setDialogTitle(getResources().getString(R.string.dialog_name_location_title));
		setDeleteConfirmation(true);
		setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_location_title));
	}
	
	@Override
	protected View createDialogUI() {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_location, null);
		
		mNameEditText = (ClearableEditText)v.findViewById(R.id.location_edit_text);
		if(mController.hasData(GVType.LOCATIONS)) {
			GVLocation location = (GVLocation) mController.getData(GVType.LOCATIONS).getItem(0); 
			mLatLng = location.getLatLng();
			mNameEditText.setText(location.getName());
		} else if(mController.getData(GVType.IMAGES).size() == 1) {
			GVImage image = (GVImage)mController.getData(GVType.IMAGES).getItem(0);
			LatLng latLng = image.getLatLng();
			if(latLng != null)
				mLatLng = latLng;
		}
		else
			mLocationClient.connect();
		
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
		if(mLatLng != null) {
			GVLocationList location = new GVLocationList();
			location.add(mLatLng, locationName);
			mController.setData(location);
		}
	}
		
	@Override
	protected void onDeleteButtonClick() {
		mController.deleteData(GVType.LOCATIONS);
	}
	
	@Override
	protected boolean hasDataToDelete() {
		return mController.hasData(GVType.LOCATIONS);
	}
	
	private void setSuggestionsAdapter() {
		mAdapter = new LocationNameAdapter(getActivity(), android.R.layout.simple_list_item_1, mLatLng, 10, mController.getTitle());
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
