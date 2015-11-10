package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataDateReview;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDates extends GvConverterBasic<DataDateReview, GvDateList.GvDate, GvDateList> {

    public GvConverterDates() {
        super(GvDateList.class);
    }

    @Override
    public GvDateList.GvDate convert(DataDateReview datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvDateList.GvDate(id, datum.getTime());
    }
}
