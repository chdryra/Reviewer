package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterLocations extends MdConverterDataReview<DataLocation, MdLocation> {
    @Override
    public MdLocation convert(DataLocation datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdLocation(id, datum.getLatLng(), datum.getName());
    }
}
