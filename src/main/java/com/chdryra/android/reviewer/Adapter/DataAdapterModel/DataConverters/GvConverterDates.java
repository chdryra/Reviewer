package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDates extends GvConverterBasic<DataDate, GvDateList.GvDate, GvDateList> {

    public GvConverterDates() {
        super(GvDateList.class);
    }

    @Override
    public GvDateList.GvDate convert(DataDate datum, String reviewId) {
        return new GvDateList.GvDate(newId(reviewId), datum.getTime());
    }
}
