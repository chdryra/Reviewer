package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterLocation extends
        GvConverterBasic<DataLocation, GvLocationList.GvLocation, GvLocationList> {

    public GvConverterLocation() {
        super(GvLocationList.class);
    }

    @Override
    public GvLocationList.GvLocation convert(DataLocation datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvLocationList.GvLocation(id, datum.getLatLng(), datum.getName());
    }
}
