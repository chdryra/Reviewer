package com.chdryra.android.reviewer.LocationServices.Interfaces;


import com.chdryra.android.reviewer.LocationServices.Implementation.LocationId;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LocationDetailsProvider {
    LocationDetails fetchDetails(LocationId id);
}
