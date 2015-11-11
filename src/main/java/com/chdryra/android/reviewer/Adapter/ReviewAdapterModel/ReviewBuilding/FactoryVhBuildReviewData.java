package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.View.GvDataModel.VhBuildReviewData;
import com.chdryra.android.reviewer.View.GvDataModel.VhDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVhBuildReviewData implements FactoryVhDataCollection{
    @Override
    public VhDataCollection newViewHolder() {
        return new VhBuildReviewData();
    }
}
