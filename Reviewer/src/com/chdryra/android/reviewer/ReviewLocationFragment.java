package com.chdryra.android.reviewer;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReviewLocationFragment extends SherlockMapFragment implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	private final static String TAG = "ReviewerLocationFragment";
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private final static int DEFAULT_ZOOM = 15;
	
	public static final int RESULT_DELETE_LOCATION = Activity.RESULT_FIRST_USER;
	public final static String LOCATION_LATLNG = "com.chdryra.android.reviewer.location_latlng";
	public final static String LOCATION_NAME = "com.chdryra.android.reviewer.location_name";
	public final static String MAP_SNAPSHOT = "com.chdryra.android.reviewer.map_snapshot";
	public final static int NUMBER_DEFAULT_NAMES= 5;

	private Review mReview;
	private ImageButton mButton;
	
	private LocationClient mLocationClient;
	
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private ArrayAdapterSearchView mSearchView;
	private ClearableAutoCompleteTextView mLocationName;
	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;
	
	private LatLng mPhotoLatLng;
	private LatLng mReviewLatLng;
	private LatLng mDefaultLatLng;
	private LatLng mLatLng;
		
	private String mSearchLocationName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
		mLocationClient = new LocationClient(getSherlockActivity(), this, this);
		mReview = (Review)IntentObjectHolder.getObject(ReviewOptionsFragment.REVIEW_OBJECT);
		mButton = (ImageButton)IntentObjectHolder.getObject(ReviewOptionsFragment.LOCATION_BUTTON);
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
				Location location = mLocationClient.getLastLocation();
				setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
				return false;
			}
		});
	    	    
	    mLocationName = (ClearableAutoCompleteTextView)v.findViewById(R.id.edit_text_name_location);

	    mReviewLatLng = getSherlockActivity().getIntent().getParcelableExtra(ReviewOptionsFragment.REVIEW_LATLNG);
	    mPhotoLatLng = getSherlockActivity().getIntent().getParcelableExtra(ReviewOptionsFragment.IMAGE_LATLNG);
	    if (mReviewLatLng != null) {
	    	mDefaultLatLng = mReviewLatLng;
	    	setLatLng(mDefaultLatLng);
	    	if(mReview != null)
	    		mLocationName.setText(mReview.getLocationName());
	    	mLocationName.hideChrome();
	    }
	    else if (mPhotoLatLng != null) {
	    	mDefaultLatLng = mPhotoLatLng;
	    	setLatLng(mDefaultLatLng);
	    }

	    mDeleteButton = (Button)v.findViewById(R.id.button_map_delete);
	    mDeleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendResult(RESULT_DELETE_LOCATION);
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
				CaptureMapScreen();
			}
		});
	        
	    return v;
	}
	
	private void gotoSearchLocation() {
		mSearchLocationName = mSearchView.getText();
		new MapSearchTask().execute(mSearchLocationName);
	}
	
	private void sendResult(int resultCode) {
		if(resultCode == Activity.RESULT_OK) {
			String locationName = mLocationName.getText().toString();
			if(locationName.length() == 0 )
				locationName = null;
			
			mReview.setLatLng(mLatLng);
			mReview.setLocationName(locationName);
			IntentObjectHolder.addObject(ReviewOptionsFragment.REVIEW_OBJECT, mReview);
		}
		
		if(resultCode == RESULT_DELETE_LOCATION) {
			mReview.deleteLatLng();
			IntentObjectHolder.addObject(ReviewOptionsFragment.REVIEW_OBJECT, mReview);
		}
		
		getSherlockActivity().setResult(resultCode);		 
		getSherlockActivity().finish();	
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
	
	@Override public void onDestroy() { 
		super.onDestroy();
		mLocationClient.disconnect();
		if(mMapView != null) 
			mMapView.onDestroy(); 
	}
	
	@Override public void onSaveInstanceState(Bundle outState) { 
		if(mMapView != null) 
			mMapView.onSaveInstanceState(outState); 
		super.onSaveInstanceState(outState); 
	}
	
	@Override public void onLowMemory() { 
		if(mMapView != null) 
			mMapView.onLowMemory(); 
		super.onLowMemory(); 
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_review_location, menu);
		
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
	    
	    final MenuItem imageLocationIcon = menu.findItem(R.id.menu_item_image_location);
		final MenuItem revertLocationIcon = menu.findItem(R.id.menu_item_revert_location);
		final MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
		mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {	
					imageLocationIcon.setVisible(false);
					revertLocationIcon.setVisible(false);
				} else {
					imageLocationIcon.setVisible(true);
					revertLocationIcon.setVisible(true);	
					searchViewMenuItem.collapseActionView();
				}
			}
		});
	    	    
	    searchViewMenuItem.setActionView(mSearchView);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_revert_location:
			setLatLng(mDefaultLatLng);
			break;
		case R.id.menu_item_image_location:
			setLatLng(mPhotoLatLng);
			break;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null) {
				Intent intent = NavUtils.getParentActivityIntent(getSherlockActivity()); 
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				NavUtils.navigateUpTo(getSherlockActivity(), intent);
			}
			return true;
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
			mLocationName.setAdapter(new LocationNameAdapter(getSherlockActivity(), 
					android.R.layout.simple_list_item_1, mLatLng, NUMBER_DEFAULT_NAMES, mSearchLocationName));
		}

		if(mSearchView != null)
			mSearchView.setAdapter(new LocationNameAdapter(getSherlockActivity(), android.R.layout.simple_list_item_1, mLatLng, 0, null));
	
		zoomToLatLng();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
                switch (resultCode) {
                    case Activity.RESULT_OK :
                    	break;
                    default:
                    	break;
                }
        }
	 }
	 
	 private void zoomToLatLng() {
		 if(mLatLng == null)
			 return;
	 
		 mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, DEFAULT_ZOOM));
		 MarkerOptions markerOptions = new MarkerOptions().position(mLatLng);
		 mGoogleMap.clear();
		 mGoogleMap.addMarker(markerOptions);
		 
	 }
	 
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		 if (connectionResult.hasResolution()) {
	            try {
	                connectionResult.startResolutionForResult( getSherlockActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
	            } catch (IntentSender.SendIntentException e) {
	                e.printStackTrace();
	            }
	        } else {
	            Log.i(TAG, "Error code connection to location services: " + connectionResult.getErrorCode());
	        }		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Log.i(TAG, "LocationClient connected");
		if(mLatLng == null) {
			Location location = mLocationClient.getLastLocation();
			if(location != null) {
				mDefaultLatLng = new LatLng(location.getLatitude(), location.getLongitude());
				setLatLng(mDefaultLatLng);
			}
		}
	}

	@Override
	public void onDisconnected() {
		Log.i(TAG, "LocationClient disconnected");
	}

	public void CaptureMapScreen() 
	{
		SnapshotReadyCallback callback = new SnapshotReadyCallback() {

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                try {
                	MapSnapshotScalerTask scaler = new MapSnapshotScalerTask();
                	scaler.execute(snapshot);
                	sendResult(Activity.RESULT_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

	    mGoogleMap.snapshot(callback);
	}
	
	private class MapSnapshotScalerTask extends AsyncTask<Bitmap, Void, Bitmap> {		
		
		@Override
	    protected Bitmap doInBackground(Bitmap... params) {
			Bitmap snapshot = params[0];
			int width = (int)getSherlockActivity().getResources().getDimension(R.dimen.mapMaxWidth);				
    		int height = (int)getSherlockActivity().getResources().getDimension(R.dimen.mapMaxHeight);
        	return Bitmap.createScaledBitmap(snapshot, width, height, true);
	    }
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			mReview.setMapSnapshot(bitmap);
			if(bitmap == null)
				mButton.setImageResource(R.drawable.ic_menu_camera);
			else
				mButton.setImageBitmap(bitmap);
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
			return PlacesAPIFetcher.fetchLatLng(params[0]);
	    }
		
		@Override
		protected void onPostExecute(LatLng latlng) {
			setLatLng(latlng);
			pd.dismiss();
		}	
	}
		
}

