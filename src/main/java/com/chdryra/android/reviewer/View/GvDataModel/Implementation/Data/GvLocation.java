package com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders.VhLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.google.android.gms.maps.model.LatLng;

import java.util.StringTokenizer;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdLocationList.MdLocation}
 * {@link ViewHolder}: {@link VhLocation}
 */
public class GvLocation extends GvDataBasic<GvLocation> implements DataLocation {
    private static final String DELIMITERS = ",|";
    public static final GvDataType<GvLocation> TYPE = new GvDataType<>(GvLocation.class,
            "location");
    public static final Creator<GvLocation> CREATOR = new Creator<GvLocation>() {
        @Override
        public GvLocation createFromParcel(Parcel in) {
            return new GvLocation(in);
        }

        @Override
        public GvLocation[] newArray(int size) {
            return new GvLocation[size];
        }
    };
    private final LatLng mLatLng;
    private final String mName;

    //Constructors
    public GvLocation() {
        this(null, null, null);
    }

    public GvLocation(LatLng latLng, String name) {
        this(null, latLng, name);
    }

    public GvLocation(GvReviewId id, LatLng latLng, String name) {
        super(GvLocation.TYPE, id);
        mLatLng = latLng;
        mName = name;
    }

    public GvLocation(GvLocation location) {
        this(location.getGvReviewId(), location.getLatLng(), location.getName());
    }

    GvLocation(Parcel in) {
        super(in);
        mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        mName = in.readString();
    }

    //public methods
    public String getShortenedName() {
        String shortened = mName;
        if (mName != null) {
            StringTokenizer tokens = new StringTokenizer(mName, DELIMITERS);
            shortened = tokens.nextToken();
        }

        return shortened != null ? shortened.trim() : mName;
    }

    //Overridden
    @Override
    public ViewHolder getViewHolder() {
        return new VhLocation(false);
    }

    @Override
    public boolean isValidForDisplay() {
        return mLatLng != null && mName != null && mName.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public String getStringSummary() {
        return "@" + getShortenedName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(mLatLng, i);
        parcel.writeString(mName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvLocation)) return false;
        if (!super.equals(o)) return false;

        GvLocation that = (GvLocation) o;

        if (mLatLng != null ? !mLatLng.equals(that.mLatLng) : that.mLatLng != null)
            return false;
        return !(mName != null ? !mName.equals(that.mName) : that.mName != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mLatLng != null ? mLatLng.hashCode() : 0);
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        return result;
    }

    @Override
    public LatLng getLatLng() {
        return mLatLng;
    }

    @Override
    public String getName() {
        return mName;
    }
}
