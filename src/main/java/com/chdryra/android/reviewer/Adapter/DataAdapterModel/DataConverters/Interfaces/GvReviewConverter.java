package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces;

import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvReviewConverter<T1 extends GvData, T2 extends GvDataListImpl<T1>> extends DataConverter<Review, T1, T2>{
}
