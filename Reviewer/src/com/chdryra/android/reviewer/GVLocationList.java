package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;
import com.chdryra.android.mygenerallibrary.RandomTextUtils;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.google.android.gms.maps.model.LatLng;

public class GVLocationList extends GVList<GVLocationList.GVLocation> {
	
	public GVLocationList() {
	}

	public GVLocationList(GVLocationList locations) {
		for(GVLocation location : locations)
			add(location);
	}

	public void add(LatLng latLng, String name) {
		add(new GVLocation(latLng, name));
	}

	public void remove(LatLng latLng, String name) {
		remove(new GVLocation(latLng, name));
	}

	public void updateName(LatLng latLng, String oldName, String newName) {
		GVLocation location = getItem(indexOf(new GVLocation(latLng, oldName)));
		location.setName(newName);
	}
	
	public boolean contains(LatLng latLng, String name) {
		return contains(new GVLocation(latLng, name));
	}
	
	@Override
	public ViewHolder getViewHolder(int position) {
		return new VHLocationView();
	}

	class GVLocation implements GVData{
		private LatLng mLatLng;
		private String mName;
		
		public GVLocation(LatLng latLng, String name) {
			mLatLng = latLng;
			mName = name;
		}
		
		public LatLng getLatLng() {
			return mLatLng;
		}
		
		public String getName() {
			return mName;
		}

		public String getShortenedName() {
			return RandomTextUtils.shortened(mName, RDLocation.LOCATION_DELIMITER);
		}

		public void setName(String name) {
			mName = name;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((mLatLng == null) ? 0 : mLatLng.hashCode());
			result = prime * result + ((mName == null) ? 0 : mName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GVLocation other = (GVLocation) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (mLatLng == null) {
				if (other.mLatLng != null)
					return false;
			} else if (!mLatLng.equals(other.mLatLng))
				return false;
			if (mName == null) {
				if (other.mName != null)
					return false;
			} else if (!mName.equals(other.mName))
				return false;
			return true;
		}

		private GVLocationList getOuterType() {
			return GVLocationList.this;
		}
	}
}
