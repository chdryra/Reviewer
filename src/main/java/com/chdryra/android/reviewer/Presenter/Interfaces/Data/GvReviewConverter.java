package com.chdryra.android.reviewer.Presenter.Interfaces.Data;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvReviewConverter<T1 extends GvData, T2 extends GvDataListImpl<T1>> extends DataConverter<Review, T1, T2> {
}
