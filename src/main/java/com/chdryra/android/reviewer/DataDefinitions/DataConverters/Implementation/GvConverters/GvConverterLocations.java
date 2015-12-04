package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterLocations extends
        GvConverterDataReview<DataLocation, GvLocation, GvLocationList> {

    public GvConverterLocations() {
        super(GvLocationList.class);
    }

    @Override
    public GvLocation convert(DataLocation datum) {
        GvReviewId id = newId(datum.getReviewId());
        return new GvLocation(id, datum.getLatLng(), datum.getName());
    }
}
