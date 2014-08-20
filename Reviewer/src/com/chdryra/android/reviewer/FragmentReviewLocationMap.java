package com.chdryra.android.reviewer;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.FragmentDeleteDone;
import com.chdryra.android.mygenerallibrary.LocationClientConnector;
import com.chdryra.android.mygenerallibrary.LocationNameAdapter;
import com.chdryra.android.remoteapifetchers.FetcherPlacesAPI;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentReviewLocationMap extends FragmentDeleteDone implements LocationClientConnector.Locatable {
	private final static String TAG = "FragmentReviewLocationMap";
	
	public final static String SUBJECT = "com.chdryra.android.reviewer.subject";
	public final static String LATLNG = "com.chdryra.android.reviewer.latlng";
	public final static String NAME = "com.chdryra.android.reviewer.location_name";
	public final static String LATLNG_OLD = "com.chdryra.android.reviewer.latlng_old";
	public final static String NAME_OLD = "com.chdryra.android.reviewer.location_name_old";
	
	private static final int DEFAULT_ZOOM = 15;
	private static final int NUMBER_DEFAULT_NAMES= 5;

	private GoogleMap mGoogleMap;
	private MapView mMapView;

	private SearchView mSearchView;
	private MenuItem mSearchViewMenuItem;
	private LocationNameAdapter mSuggestionsAdapter;
	
	private ClearableAutoCompleteTextView mLocationName;
	private ImageButton mRevertButton;
	
	private LatLng mLatLng;
	private LatLng mRevertLatLng;
	private String mRevertName;
	private String mReviewSubject;
	
	private LocationClientConnector mLocationClient;
	private String mSearchLocationName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mRevertLatLng = (LatLng)getActivity().getIntent().getParcelableExtra(LATLNG);
	    mRevertName = (String)getActivity().getIntent().getSerializableExtra(NAME);
	    mReviewSubject = (String)getActivity().getIntent().getSerializableExtra(SUBJECT);
	    
		mLocationClient = new LocationClientConnector(getActivity(), this);
	    
	    //Not sure why I have to do this. Was working without this at some point...
	    try {
	        MapsInitializer.initialize(getActivity());
	    } catch (GooglePlayServicesNotAvailableException e) {
	        Log.e("Test", "Have GoogleMap but then error", e);
	    }
	    
	    setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_location_title));
	    setDismissOnDelete(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLocationClient.connect();
		
		View v = inflater.inflate(R.layout.fragment_review_location_map, container, false);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

		mMapView = (MapView)v.findViewById(R.id.mapView);
	    mMapView.onCreate(savedInstanceState);	    
	    mGoogleMap = ((MapView) v.findViewById(R.id.mapView)).getMap();
	    mLocationName = (ClearableAutoCompleteTextView)v.findViewById(R.id.edit_text_name_location);
	    mRevertButton = (ImageButton)v.findViewById(R.id.revert_location_image_button);
	    
	    initUI();
	    	    
	    return v;
	}

	private void initUI() {
		initLocationNameUI();
		initGoogleMapUI();
		initRevertButtonUI();
	}

	private void initGoogleMapUI() {
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
	}

	private void initLocationNameUI() {
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
	}
	
	private void initRevertButtonUI() {
		if(mRevertLatLng != null) {
		    mRevertButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mRevertLatLng == null)
						return;
								
					mSearchLocationName = null;
					setLatLng(mRevertLatLng);
					mLocationName.setText(mRevertName);
					mLocationName.hideChrome();
				}
			});
	
		    mRevertButton.performClick();
		} else
			mRevertButton.setVisibility(View.GONE);
		
	}
	
	public void handleSearch() {
	    Intent intent = getActivity().getIntent(); 
		String query = null;
	    if (Intent.ACTION_SEARCH.equals(intent.getAction()))
	    	query = intent.getStringExtra(SearchManager.QUERY);
	    else if(Intent.ACTION_PICK.equals(intent.getAction()))
	    	query = intent.getData().getLastPathSegment();
	    else
	    	return;
	    
	    gotoSearchLocation(query);
	}

	private void gotoSearchLocation(String query) {
		mSearchLocationName = query;
		new MapSearchTask().execute(mSearchLocationName);
		mSearchView.setQuery(null, false);
		mSearchViewMenuItem.collapseActionView();
	}

	@Override
	protected void onDoneSelected() {
		if(mLocationName.length() == 0) {
			Toast.makeText(getActivity(), R.string.toast_enter_location, Toast.LENGTH_SHORT).show();
			setDismissOnDone(false);
			return;
		} else
			setDismissOnDone(true);

		Intent i = getNewReturnData();
		i.putExtra(LATLNG, mLatLng);
		i.putExtra(LATLNG_OLD, mRevertLatLng);
		i.putExtra(NAME, mLocationName.getText().toString());
		i.putExtra(NAME_OLD, mRevertName);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_search_delete_done, menu);

	    final MenuItem deleteIcon = menu.findItem(R.id.menu_item_delete);
		final MenuItem doneIcon = menu.findItem(R.id.menu_item_done);
		mSearchViewMenuItem = menu.findItem(R.id.menu_item_search);

		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		
		mSearchView = (SearchView) mSearchViewMenuItem.getActionView();
		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));		
		mSearchView.setIconifiedByDefault(false); 
		mSearchView.setQueryHint(getResources().getString(R.string.search_view_location_hint));
		mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {	
					deleteIcon.setVisible(false);
					doneIcon.setVisible(false);
				} else {
					deleteIcon.setVisible(true);
					doneIcon.setVisible(true);	
					mSearchViewMenuItem.collapseActionView();
				}
			}
		});

		mSuggestionsAdapter = new LocationNameAdapter(getActivity(), android.R.layout.simple_list_item_1, mLatLng, 0, null);
		mSuggestionsAdapter.registerDataSetObserver(new LocationSuggestionsObserver());
		
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				if(newText.length() > 0)
					findSuggestions(newText);
				else
					invalidateSuggestions();
				
			    return false;
			}
		});
	}
	
	private void findSuggestions(String query) {
		mSuggestionsAdapter.findSuggestions(query);
	}
	
	private void invalidateSuggestions() {
		mSearchView.setSuggestionsAdapter(null);
	}
	
	private CursorAdapter getSuggestionsCursorAdapter() {
		//For some inexplicable reason SearchView only accepts CursorAdapters for suggestions.
		//Place name suggestions are fetched using the ArrayAdapter LocationNameAdapter.
		//This is a (clunky) way of taking suggestions from LocationNameAdapter and putting in a CurserAdapter.
	    String[] columnNames = {"_id",SearchManager.SUGGEST_COLUMN_INTENT_DATA};
	    String[] suggestion_row = new String[columnNames.length];
	    MatrixCursor suggestions_cursor = new MatrixCursor(columnNames);
	    for(int i = 0; i < mSuggestionsAdapter.getCount(); ++i){
	    	suggestion_row[0] = String.valueOf(mSuggestionsAdapter.getItemId(i));
	    	suggestion_row[1] = mSuggestionsAdapter.getItem(i);
	    	suggestions_cursor.addRow(suggestion_row);
	    	Log.i(TAG, suggestion_row[0] + ", " + suggestion_row[1]);
	    }              
	    
	    String[] from = {SearchManager.SUGGEST_COLUMN_INTENT_DATA};
	    int[] to = {android.R.id.text1};
	    return new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, suggestions_cursor, from, to, 0);
	}
	
	private void setLatLng(LatLng latlang) {
		if(latlang == null)
			return;
		
		mLatLng = latlang;
		
		if(mLocationName != null) {
			mLocationName.setText(null);
			String primaryDefaultSuggestion = mSearchLocationName != null? mSearchLocationName : mReviewSubject;
			mLocationName.setAdapter(new LocationNameAdapter(getActivity(), 
					android.R.layout.simple_list_item_1, mLatLng, NUMBER_DEFAULT_NAMES, primaryDefaultSuggestion));
		}

		zoomToLatLng();
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

	 private class MapSearchTask extends AsyncTask<String, Void, LatLng> {		
		private ProgressDialog pd;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
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
	public void onLocated(LatLng latLng) {
		setLatLng(latLng);
	}

	@Override
	public void onLocationClientConnected(LatLng latLng) {
		if(mLatLng == null)
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

	@Override
	protected void onDeleteSelected() {
		Intent i = getNewReturnData();
		i.putExtra(LATLNG_OLD, mRevertLatLng);
		i.putExtra(NAME_OLD, mRevertName);
	}

	@Override
	protected boolean hasDataToDelete() {
		return mRevertLatLng != null;
	}

	private class LocationSuggestionsObserver extends DataSetObserver {
	    public void onChanged() {
	    	mSearchView.setSuggestionsAdapter(getSuggestionsCursorAdapter());
	    }

	    public void onInvalidated() {
	    	invalidateSuggestions();
	    }

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
