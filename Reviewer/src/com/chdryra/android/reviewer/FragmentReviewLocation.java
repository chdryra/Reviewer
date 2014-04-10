package com.chdryra.android.reviewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.CursorAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.chdryra.android.mygenerallibrary.IntentObjectHolder;
import com.chdryra.android.mygenerallibrary.SherlockMapFragment;
import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.remoteapifetchers.FetcherPlacesAPI;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentReviewLocation extends SherlockMapFragment implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	private final static String TAG = "ReviewerLocationFragment";
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private final static int DEFAULT_ZOOM = 15;

	private static final int DELETE_CONFIRM = DialogBasicFragment.DELETE_CONFIRM;
	public static final int RESULT_DELETE = Activity.RESULT_FIRST_USER;
	
	public final static int NUMBER_DEFAULT_NAMES= 5;

	private Controller mController = Controller.getInstance();
	private RDId mReviewID;
	
	private ImageButton mButton;
	
	private LocationClient mLocationClient;
	
	private GoogleMap mGoogleMap;
	private MapView mMapView;
	private ArrayAdapterSearchView mSearchView;
	private ClearableAutoCompleteTextView mLocationName;

	private Button mDeleteButton;
	private Button mCancelButton;
	private Button mDoneButton;
	
	private String mSearchLocationName;
	
	private LatLng mLatLng;
	private LatLng mPhotoLatLng;
	private LatLng mRevertLatLng;

	private float mRevertMapSnapshotZoom;
	
	private boolean mDeleteConfirmed = false;
	private boolean mImageLocationIconIsVisible = true;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(getSherlockActivity(), this, this);
		mReviewID = (RDId)getArguments().getParcelable(FragmentReviewOptions.REVIEW_ID);
		mButton = (ImageButton)IntentObjectHolder.getObject(FragmentReviewOptions.LOCATION_BUTTON);
		mRevertMapSnapshotZoom =  mController.hasMapSnapshot(mReviewID)? mController.getMapSnapshotZoom(mReviewID) : DEFAULT_ZOOM;
	    mPhotoLatLng = mController.getImageLatLng(mReviewID);
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
				Location location = mLocationClient.getLastLocation();
				setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
				return false;
			}
		});
	    	    
	    mLocationName = (ClearableAutoCompleteTextView)v.findViewById(R.id.edit_text_name_location);

	    if (mController.hasLocation(mReviewID)) {
	    	mRevertLatLng = mController.getLocationLatLng(mReviewID);
	    	setLatLng(mRevertLatLng);
	    	zoomToLatLng(mRevertMapSnapshotZoom);
	    	if(mController.hasLocationName(mReviewID))
	    		mLocationName.setText(mController.getLocationName(mReviewID));
	    	mLocationName.hideChrome();
	    }
	    else if (mPhotoLatLng != null) {
	    	mRevertLatLng = mPhotoLatLng;
	    	setLatLng(mRevertLatLng);
	    }

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
				captureMapSnapshotAndSetLocationAndSendOK();
			}
		});
	        
	    return v;
	}
	
	private void gotoSearchLocation() {
		mSearchLocationName = mSearchView.getText();
		new MapSearchTask().execute(mSearchLocationName);
	}
	
	private void sendResult(int resultCode) {
		if(resultCode == RESULT_DELETE && mController.hasLocation(mReviewID)) {
			if(mDeleteConfirmed) {
				mController.deleteLocation(mReviewID);
			} else {
				DialogBasicFragment.showDeleteConfirmDialog(getResources().getString(R.string.location_activity_title), 
						FragmentReviewLocation.this, DELETE_CONFIRM, getFragmentManager());
				return;
			}
		}
		
		if(resultCode == Activity.RESULT_OK) {
			mController.setLocationLatLng(mReviewID, mLatLng);
			mController.setLocationName(mReviewID, mLocationName.getText().toString());
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
		if(mPhotoLatLng == null)
			mImageLocationIconIsVisible = false;
	    imageLocationIcon.setVisible(mImageLocationIconIsVisible);
		
		mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {	
					imageLocationIcon.setVisible(false);
					revertLocationIcon.setVisible(false);
				} else {
					imageLocationIcon.setVisible(mImageLocationIconIsVisible);
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
			mSearchLocationName = null;
			setLatLng(mRevertLatLng);
			zoomToLatLng(mRevertMapSnapshotZoom);
			mLocationName.setText(mController.getLocationName(mReviewID));
			mLocationName.hideChrome();
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
			String primaryDefaultSuggestion = mSearchLocationName != null? mSearchLocationName : mController.getTitle(mReviewID);
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
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
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
				mRevertLatLng = new LatLng(location.getLatitude(), location.getLongitude());
				setLatLng(mRevertLatLng);
			}
		}
	}

	@Override
	public void onDisconnected() {
		Log.i(TAG, "LocationClient disconnected");
	}

	public void captureMapSnapshotAndSetLocationAndSendOK() 
	{
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
			mController.setMapSnapshot(mReviewID, bitmap, mZoom);
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
			return FetcherPlacesAPI.fetchLatLng(params[0]);
	    }
		
		@Override
		protected void onPostExecute(LatLng latlng) {
			setLatLng(latlng);
			pd.dismiss();
		}	
	}

	private class ArrayAdapterSearchView extends SearchView {
		private SearchView.SearchAutoComplete mSearchAutoComplete;

		public ArrayAdapterSearchView(Context context) {
		    super(context);
		    initialize();
		}

		public ArrayAdapterSearchView(Context context, AttributeSet attrs) {
		    super(context, attrs);
		    initialize();
		}

		public void initialize() {
		    mSearchAutoComplete = (SearchAutoComplete) findViewById(R.id.abs__search_src_text);
		    setAdapter(null);
		    setOnItemClickListener(null);
		}

		@Override
		public void setSuggestionsAdapter(CursorAdapter adapter) {
		}

		public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		    mSearchAutoComplete.setOnItemClickListener(listener);
		}

		public void setAdapter(ArrayAdapter<?> adapter) {
		    mSearchAutoComplete.setAdapter(adapter);
		}
		
		public void setText(String text) { 
			mSearchAutoComplete.setText(text); 
		}

		public String getText() {
			return mSearchAutoComplete.getText().toString();
		}

		public void setOnEditorActionListener(
				OnEditorActionListener onEditorActionListener) {
			mSearchAutoComplete.setOnEditorActionListener(onEditorActionListener);
			
		} 
	}

	private class LocationNameAdapter extends ArrayAdapter<String> implements Filterable {
		private static final String TAG = "LocationNameAdapter";
		private static final int RADIUS = 250;
		
	    private ArrayList<String> mLocationSuggestions = null;
		private ArrayList<String> mLocationDefaultSuggestions = null;
		private String mPrimaryDefaultSuggestion;
		private LatLng mLatLng;
		
		public LocationNameAdapter(Context context, int textViewResourceId, LatLng latlng, int numberDefaultSuggestions, String primaryDefaultSuggestion) {
			super(context, textViewResourceId);
			mLatLng = latlng;
			if(numberDefaultSuggestions > 0) {
				if(primaryDefaultSuggestion != null) {
					mPrimaryDefaultSuggestion = primaryDefaultSuggestion;
					numberDefaultSuggestions--;
				}
				
				if(mLatLng != null) {				
					GetAddressTask task = new GetAddressTask(context, mLatLng);
					task.execute(numberDefaultSuggestions);
				}	
			}		
		}
		
		@Override
	    public int getCount() {
			if(mLocationSuggestions != null)
				return mLocationSuggestions.size();
			else
				return 0;
	    }

	    @Override
	    public String getItem(int index) {
	        return mLocationSuggestions.get(index);
	    }

	    @Override
	    public Filter getFilter() {
	        Filter filter = new Filter() {
	            
	        	@Override
	            protected FilterResults performFiltering(CharSequence constraint) {
	                FilterResults filterResults = new FilterResults();
	                
	                if (constraint != null)
	                	mLocationSuggestions = FetcherPlacesAPI.fetchAutoCompleteSuggestions(constraint.toString(), mLatLng, RADIUS);
	                else if(mLocationDefaultSuggestions != null)
	                    mLocationSuggestions = mLocationDefaultSuggestions;
	           
	                filterResults.values = mLocationSuggestions;
	                filterResults.count = mLocationSuggestions.size();
	                
	                return filterResults;
	            }

	            @Override
	            protected void publishResults(CharSequence constraint, FilterResults results) {
	                if (results != null && results.count > 0) {
	                    notifyDataSetChanged();
	                }
	                else {
	                    notifyDataSetInvalidated();
	                }
	            }};
	            
	        return filter;
	    }

		    
	    private class GetAddressTask extends AsyncTask<Integer, Void, ArrayList<String>> {
			  
			  Context mContext;
			  LatLng mLatLng;
			  
			  public GetAddressTask(Context context, LatLng latlng) {
				  super();
				  mContext = context;
				  mLatLng = latlng;
			  }
	  
			  @Override
			  protected ArrayList<String> doInBackground(Integer... params) {
				  Integer numberToGet = params[0];
				  
				  ArrayList<String> namesFromGoogle = FetcherPlacesAPI.fetchNearestNames(mLatLng, numberToGet);
				  
				  if(namesFromGoogle.size() > 0)
					  return namesFromGoogle;
				  else {
					  //Try using Geocoder
					  Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
				
					  List<Address> addresses = null;
					  try {
					      addresses = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, numberToGet);
					  } catch (IOException e1) {
						  Log.e(TAG, "IO Exception trying to get address");
						  e1.printStackTrace();
					  } catch (IllegalArgumentException e2) {
						  Log.e(TAG, "Illegal Argument Exception trying to get address");
						  e2.printStackTrace();
					  }
					  
					  if(addresses != null && addresses.size() > 0) {
						  ArrayList<String> addressesList = new ArrayList<String>();
						  for(int i = 0; i<addressesList.size(); ++i)
							  addressesList.add(formatAddress(addresses.get(i)));
						  return addressesList;
					  }				  
					  else
						  return null;
					  }
			  }
		  
			  private String formatAddress(Address address) {
				  String addressText = String.format(
			              "%s%s%s",
			              // If there's a street address, add it
			              address.getMaxAddressLineIndex() > 0 ?
			                      address.getAddressLine(0) : "",
			              // Locality is usually a city
			              address.getLocality() != null ?
			            		  ", " + address.getLocality(): "",
			              address.getCountryName() != null ?
			            		  ", " + address.getCountryName() : "");
		
				  return addressText;
			 }
		  
		@Override
		protected void onPostExecute(ArrayList<String> addresses) {
			super.onPostExecute(addresses);
			if(addresses != null) {
				mLocationDefaultSuggestions = addresses;
				if(mPrimaryDefaultSuggestion != null)
					mLocationDefaultSuggestions.add(0, mPrimaryDefaultSuggestion);
				mLocationSuggestions = new ArrayList<String>(mLocationDefaultSuggestions);
			}
		}
	}
}
}
