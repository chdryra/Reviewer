package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterLocations extends
        MdConverterDataReview<DataLocation, MdLocationList.MdLocation, MdLocationList> {

    public MdConverterLocations() {
        super(MdLocationList.class);
    }

    @Override
    public MdLocationList.MdLocation convert(DataLocation datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdLocationList.MdLocation(id, datum.getLatLng(), datum.getName());
    }
}
