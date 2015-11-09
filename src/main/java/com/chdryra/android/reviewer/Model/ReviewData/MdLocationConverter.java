package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataLocation;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdLocationConverter extends
        MdConverterBasic<DataLocation, MdLocationList.MdLocation, MdLocationList> {

    public MdLocationConverter() {
        super(MdLocationList.class);
    }

    @Override
    public MdLocationList.MdLocation convert(DataLocation datum) {
        ReviewId id = ReviewId.fromString(datum.getReviewId());
        return new MdLocationList.MdLocation(datum.getLatLng(), datum.getName(), id);
    }
}
