/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import com.chdryra.android.reviewer.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationProvider {
    private final String mProviderName;

    public LocationProvider(String providerName) {
        mProviderName = providerName;
    }

    public static LocationProvider app() {
        return new LocationProvider(Strings.APP_NAME);
    }

    public String getProviderName() {
        return mProviderName;
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
