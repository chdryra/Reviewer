package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDateList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDateReviews extends GvConverterDataReview<DataDateReview, GvDate, GvDateList> {

    public GvConverterDateReviews() {
        super(GvDateList.class);
    }

    @Override
    public GvDate convert(DataDateReview datum) {
        return new GvDate(newId(datum.getReviewId()), datum.getTime());
    }
}
