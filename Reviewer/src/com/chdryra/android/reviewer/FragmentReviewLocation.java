package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.LocationNameAdapter;
import com.chdryra.android.mygenerallibrary.SherlockMapFragment;
import com.chdryra.android.mygenerallibrary.ArrayAdapterSearchView;
import com.chdryra.android.mygenerallibrary.Locatable;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.remoteapifetchers.FetcherPlacesAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentReviewLocation extends SherlockMapFragment implements Locatable {

	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	
	private static final int DEFAULT_ZOOM = 15;
	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	private static final int NUMBER_DEFAULT_NAMES= 5;

	private ControllerReviewNode mController;
	
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private ArrayAdapterSearchView mSearchView;
	private ClearableAutoCompleteTextView mLocationName;
	private ImageButton mPhotoLocationButton;
	private ImageButton mRevertButton;
	
	private LatLng mLatLng;
	private LatLng mPhotoLatLng;
	private LatLng mRevertLatLng;

	private LocationClientConnector mLocationClient;
	private String mSearchLocationName;
	
	private float mRevertMapSnapshotZoom;
	private boolean mDeleteConfirmed = false;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = Controller.unpack(getActivity().getIntent().getExtras());
		mRevertMapSnapshotZoom =  mController.hasMapSnapshot()? mController.getMapSnapshotZoom() : DEFAULT_ZOOM;
	    mPhotoLatLng = mController.getImageLatLng();
	    mLocationClient = new LocationClientConnector(getSherlockActivity(), this);
	    setRetainInstance(true);
	    setHasOptionsMenu(true);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);		
		
		View v = inflater.inflate(R.layout.fragment_review_location, container, false);
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mLocationClient.connect();
		
		mMapView = (MapView)v.findViewById(R.id.mapView);
	    mMapView.onCreate(savedInstanceState);
	    
	    mGoogleMap = ((MapView) v.findViewById(R.id.mapView)).getMap();
	    mGoogleMap.setMyLocationEnabled(true);
	    mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
			@Override
			public boolean onMyLocationButtonClick() {
				mLocationClient.locate();
				return false;
			}
		});
	    mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				marker.hideInfoWindow();
			}
		});
	    mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker marker) {}
			
			@Override
			public void onMarkerDragEnd(Marker marker) {
				setLatLng(marker.getPosition());
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {}
		});
	    
	    //mGoogleMap.setInfoWindowAdapter(new InfoWindowAdapterRated());
	    
	    mLocationName = (ClearableAutoCompleteTextView)v.findViewById(R.id.edit_text_name_location);
	    mLocationName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				updateMapMarker();
			}
		});
	    
	    if (mController.hasLocation()) {
	    	mRevertLatLng = mController.getLocationLatLng();
	    	setLatLng(mRevertLatLng);
	    	zoomToLatLng(mRevertMapSnapshotZoom);
	    	if(mController.hasLocationName())
	    		mLocationName.setText(mController.getLocationName());
	    	mLocationName.hideChrome();
	    }
	    else if (mPhotoLatLng != null) {
	    	mRevertLatLng = mPhotoLatLng;
	    	setLatLng(mRevertLatLng);
	    } 
	    
	    mPhotoLocationButton = (ImageButton)v.findViewById(R.id.photo_location_image_button);
	    mPhotoLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mPhotoLatLng == null)
					Toast.makeText(getSherlockActivity(), getResources().getString(R.string.toast_no_image_gps_tag), Toast.LENGTH_SHORT).show();
				else
					setLatLng(mPhotoLatLng);
			}
		});
	    
	    mRevertButton = (ImageButton)v.findViewById(R.id.revert_location_image_button);
	    mRevertButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mRevertLatLng == null)
					return;
							
				mSearchLocationName = null;
				setLatLng(mRevertLatLng);
				zoomToLatLng(mRevertMapSnapshotZoom);
				mLocationName.setText(mController.getLocationName());
				mLocationName.hideChrome();
			}
		});

	    return v;
	}
	
	private void gotoSearchLocation() {
		mSearchLocationName = mSearchView.getText();
		new MapSearchTask().execute(mSearchLocationName);
	}
	
	private void sendResult(int resultCode) {
		if(resultCode == RESULT_DELETE && mController.hasLocation()) {
			if(mDeleteConfirmed) {
				mController.deleteLocation();
			} else {
				DialogBasicFragment.showDeleteConfirmDialog(getResources().getString(R.string.location_activity_title), 
						FragmentReviewLocation.this, DELETE_CONFIRM, getFragmentManager());
				return;
			}
		}
		
		if(resultCode == Activity.RESULT_OK) {
			mController.setLocationLatLng(mLatLng);
			mController.setLocationName(mLocationName.getText().toString());
		}
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_search_delete_done, menu);
		
		mSearchView = new ArrayAdapterSearchView(getSherlockActivity().getSupportActionBar().getThemedContext());
		mSearchView.setQueryHint(getResources().getString(R.string.search_view_location_hint));
		mSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            mSearchView.setText(parent.getItemAtPosition(position).toString());
	            mSearchView.clearFocus();
	            gotoSearchLocation();
	        }
	    });
	    
	    mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {			
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		            if(event == null) {
		            	if(actionId == EditorInfo.IME_ACTION_SEARCH) {
		            		mSearchView.clearFocus();
		            		gotoSearchLocation();
		            	}
		            } 		            
		            return false;
				}
		});

	    mSearchView.setAdapter(new LocationNameAdapter(getSherlockActivity(), android.R.layout.simple_list_item_1, mLatLng, 0, null));
	    
	    final MenuItem deleteIcon = menu.findItem(R.id.menu_item_delete);
		final MenuItem doneIcon = menu.findItem(R.id.menu_item_done);
		final MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
		
		mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {	
					deleteIcon.setVisible(false);
					doneIcon.setVisible(false);
				} else {
					deleteIcon.setVisible(true);
					doneIcon.setVisible(true);	
					searchViewMenuItem.collapseActionView();
				}
			}
		});
	    	    
	    searchViewMenuItem.setActionView(mSearchView);
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
				captureMapSnapshotAndSetLocationAndSendOK();
				break;
				
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void setLatLng(LatLng latlang) {
		if(latlang == null)
			return;
		
		mLatLng = latlang;
		
		if(mLocationName != null) {
			mLocationName.setText(null);
			String primaryDefaultSuggestion = mSearchLocationName != null? mSearchLocationName : mController.getTitle();
			mLocationName.setAdapter(new LocationNameAdapter(getSherlockActivity(), 
					android.R.layout.simple_list_item_1, mLatLng, NUMBER_DEFAULT_NAMES, primaryDefaultSuggestion));
		}

		if(mSearchView != null)
			mSearchView.setAdapter(new LocationNameAdapter(getSherlockActivity(), android.R.layout.simple_list_item_1, mLatLng, 0, null));
	
		zoomToLatLng();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LocationClientConnector.CONNECTION_FAILURE_RESOLUTION_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK :
                    	break;
                    default:
                    	break;
                }
            case DELETE_CONFIRM:
				if(resultCode == Activity.RESULT_OK) {
					mDeleteConfirmed = true;
					sendResult(RESULT_DELETE);
				}
				break;
			default:
				break;
        }
	 }
	 
	 private void zoomToLatLng() {
		 zoomToLatLng(DEFAULT_ZOOM);
	 }

	 private void zoomToLatLng(float zoomLevel) {
		 if(mLatLng == null)
			 return;
	 
		 mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, zoomLevel));
		 updateMapMarker();
	 }
	 
	 private Marker updateMapMarker() {
		 MarkerOptions markerOptions = new MarkerOptions().position(mLatLng);
		 markerOptions.title(mLocationName.getText().toString());
		 markerOptions.draggable(true);
		 mGoogleMap.clear();
		 Marker marker = mGoogleMap.addMarker(markerOptions);
		 marker.showInfoWindow();
		 
		 return marker;
	 }

	public void captureMapSnapshotAndSetLocationAndSendOK() 
	{
		Bitmap noMarker = BitmapFactory.decodeResource(getResources(), R.drawable.micro_marker);;
		Marker marker = updateMapMarker();
		marker.setIcon(BitmapDescriptorFactory.fromBitmap(noMarker));
		marker.showInfoWindow();
		mGoogleMap.setMyLocationEnabled(false);
		mGoogleMap.snapshot( new SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                try {
                	MapSnapshotScalerTask scaler = new MapSnapshotScalerTask(mLatLng, 
                			mLocationName.getText().toString(), mGoogleMap.getCameraPosition().zoom);
                	scaler.execute(snapshot);
                	sendResult(Activity.RESULT_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		
	}
	
	private class MapSnapshotScalerTask extends AsyncTask<Bitmap, Void, Bitmap> {		
		private float mZoom;
		
		public MapSnapshotScalerTask(LatLng latLng, String locationName, float zoom) {
			mZoom = zoom;
			
		}
		
		@Override
	    protected Bitmap doInBackground(Bitmap... params) {
			Bitmap snapshot = params[0];
			int width = (int)getSherlockActivity().getResources().getDimension(R.dimen.mapMaxWidth);				
    		int height = (int)getSherlockActivity().getResources().getDimension(R.dimen.mapMaxHeight);
        	return Bitmap.createScaledBitmap(snapshot, width, height, true);
	    }
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			mController.setMapSnapshot(bitmap, mZoom);
		}	
	}
	
	private class MapSearchTask extends AsyncTask<String, Void, LatLng> {		

		private ProgressDialog pd;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(getSherlockActivity());
			pd.setTitle(getResources().getString(R.string.progress_bar_search_location_title));
			pd.setMessage(getResources().getString(R.string.progress_bar_search_location_message));
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
		@Override
	    protected LatLng doInBackground(String... params) {
			return FetcherPlacesAPI.fetchLatLng(params[0]);
	    }
		
		@Override
		protected void onPostExecute(LatLng latlng) {
			setLatLng(latlng);
			pd.dismiss();
		}	
	}

	@Override
	public void setLocationLatLng(LatLng latLng) {
		if(mRevertLatLng == null)
			mRevertLatLng = latLng;
		setLatLng(latLng);
	}


	@Override
	public void onStart() {
		super.onStart();
		mLocationClient.connect();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mLocationClient.disconnect();
	}
	
	@Override 
	public void onResume() { 
		super.onResume(); 
		if(mMapView != null) 
			mMapView.onResume(); 	
	} 
	
	@Override 
	public void onPause() { 
		super.onPause(); 
		if(mMapView != null) 
			mMapView.onPause(); 
	}
	
	@Override 
	public void onDestroy() { 
		super.onDestroy();
		mLocationClient.disconnect();
		if(mMapView != null) 
			mMapView.onDestroy(); 
	}
	
	@Override 
	public void onSaveInstanceState(Bundle outState) { 
		if(mMapView != null) 
			mMapView.onSaveInstanceState(outState); 
		super.onSaveInstanceState(outState); 
	}
	
	@Override 
	public void onLowMemory() { 
		if(mMapView != null) 
			mMapView.onLowMemory(); 
		super.onLowMemory(); 
	}

//	import android.widget.RatingBar;
//	import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
//	private class InfoWindowAdapterRated implements InfoWindowAdapter {
//
//		@Override
//		public View getInfoContents(Marker arg0) {
//			View v = getSherlockActivity().getLayoutInflater().inflate(R.layout.info_window_adapter_rated, null);
//			TextView titleTextView = (TextView)v.findViewById(R.id.info_window_title);
//			RatingBar ratingBar = (RatingBar)v.findViewById(R.id.info_window_rating_bar);
//			
//			String title = mLocationName.getText().toString();
//			if(title != null && title.length() > 0) {
//				titleTextView.setText(title);
//				titleTextView.setVisibility(View.VISIBLE);
//			} else
//				titleTextView.setVisibility(View.GONE);
//			
//			ratingBar.setRating(mController.getRating());
//			
//			return v;
//		}
//
//		@Override
//		public View getInfoWindow(Marker arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
}
