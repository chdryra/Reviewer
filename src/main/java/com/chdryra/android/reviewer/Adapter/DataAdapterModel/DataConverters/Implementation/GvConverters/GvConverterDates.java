package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDateList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDates extends GvConverterBasic<DataDate, GvDate, GvDateList> {

    public GvConverterDates() {
        super(GvDateList.class);
    }

    @Override
    public GvDate convert(DataDate datum, String reviewId) {
        return new GvDate(newId(reviewId), datum.getTime());
    }
}
