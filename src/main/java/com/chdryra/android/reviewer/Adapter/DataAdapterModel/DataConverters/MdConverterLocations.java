package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterLocations extends
        MdConverterBasic<DataLocation, MdLocationList.MdLocation, MdLocationList> {

    public MdConverterLocations() {
        super(MdLocationList.class);
    }

    @Override
    public MdLocationList.MdLocation convert(DataLocation datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdLocationList.MdLocation(id, datum.getLatLng(), datum.getName());
    }
}
