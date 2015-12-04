package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDateList;

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
