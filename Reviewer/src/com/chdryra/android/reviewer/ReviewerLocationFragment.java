package com.chdryra.android.reviewer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class ReviewerLocationFragment extends SherlockMapFragment implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	private final static String TAG = "ReviewerLocationFragment";
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private final static int DEFAULT_ZOOM = 15;
	
	public static final int RESULT_DELETE_LOCATION = Activity.RESULT_FIRST_USER;
	public final static String LOCATION_LATLNG = "com.chdryra.android.reviewer.location_latlng";
	public final static String LOCATION_NAME = "com.chdryra.android.reviewer.location_name";
	public final static String MAP_SNAPSHOT = "com.chdryra.android.reviewer.map_snapshot";
	
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private LatLng mPhotoLatLng;
	private LatLng mReviewLatLng;
	private LatLng mDefaultLatLng;
	private LatLng mLatLng;
	
	private LocationClient mLocationClient;
	
	private Bitmap mSnapshot;
	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);		
		setRetainInstance(true);
		mLocationClient = new LocationClient(getSherlockActivity(), this, this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);		
		
		View v = inflater.inflate(R.layout.fragment_review_location, container, false);
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mMapView = (MapView)v.findViewById(R.id.mapView);
	    mMapView.onCreate(savedInstanceState);
	    
	    mGoogleMap = ((MapView) v.findViewById(R.id.mapView)).getMap();
	    mGoogleMap.setMyLocationEnabled(true);
	    mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
			
			@Override
			public boolean onMyLocationButtonClick() {
				Location location = mLocationClient.getLastLocation();
				mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
				zoomToLatLng();
				return false;
			}
		});
	    
	    mReviewLatLng = getSherlockActivity().getIntent().getParcelableExtra(ReviewerFinishFragment.REVIEW_LATLNG);
	    mPhotoLatLng = getSherlockActivity().getIntent().getParcelableExtra(ReviewerFinishFragment.IMAGE_LATLNG);
	    if (mReviewLatLng != null)
	    	mDefaultLatLng = mReviewLatLng;
	    else if (mPhotoLatLng != null)
	    	mDefaultLatLng = mPhotoLatLng;
	    
	    mLatLng = mDefaultLatLng;
	    zoomToLatLng();	
	    	    
	    mLocationClient.connect();

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
	
	private void sendResult(int resultCode) {
		if(resultCode == Activity.RESULT_OK) {
			Review review = (Review)IntentObjectHolder.getObject(ReviewerFinishFragment.REVIEW_OBJECT);
			review.setLatLng(mLatLng, mSnapshot);
			IntentObjectHolder.addObject(ReviewerFinishFragment.REVIEW_OBJECT, review);
		}
		
		if(resultCode == RESULT_DELETE_LOCATION) {
			Review review = (Review)IntentObjectHolder.getObject(ReviewerFinishFragment.REVIEW_OBJECT);
			review.deleteLatLng();
			IntentObjectHolder.addObject(ReviewerFinishFragment.REVIEW_OBJECT, review);
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
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_revert_location:
			mLatLng = mDefaultLatLng;
			zoomToLatLng();
			break;
		case R.id.menu_item_image_location:
			mLatLng = mPhotoLatLng;
			zoomToLatLng();
			break;
		case R.id.menu_item_search_location:
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
				mLatLng = mDefaultLatLng;
				zoomToLatLng();
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
                	int width = (int)getSherlockActivity().getResources().getDimension(R.dimen.mapMaxWidth);				
            		int height = (int)getSherlockActivity().getResources().getDimension(R.dimen.mapMaxHeight);
                	mSnapshot = Bitmap.createScaledBitmap(snapshot, width, height, true);
                	sendResult(Activity.RESULT_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

	    mGoogleMap.snapshot(callback);
	}
}
