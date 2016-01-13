package com.chdryra.android.reviewer.PlugIns.LocationServices.Api;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationProvider {
    String mProviderName;

    public LocationProvider(String providerName) {
        mProviderName = providerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationProvider)) return false;

        LocationProvider that = (LocationProvider) o;

        return !(mProviderName != null ? !mProviderName.equals(that.mProviderName) : that
                .mProviderName != null);

    }

    @Override
    public int hashCode() {
        return mProviderName != null ? mProviderName.hashCode() : 0;
    }
}
