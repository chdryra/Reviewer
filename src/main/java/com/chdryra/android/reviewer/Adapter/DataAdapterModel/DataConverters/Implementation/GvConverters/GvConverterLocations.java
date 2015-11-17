package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterLocations extends
        GvConverterDataReview<DataLocation, GvLocationList.GvLocation, GvLocationList> {

    public GvConverterLocations() {
        super(GvLocationList.class);
    }

    @Override
    public GvLocationList.GvLocation convert(DataLocation datum) {
        GvReviewId id = newId(datum.getReviewId());
        return new GvLocationList.GvLocation(id, datum.getLatLng(), datum.getName());
    }
}
