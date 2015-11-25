package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryVhDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders.VhBuildReviewData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders.VhDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVhBuildReviewData implements FactoryVhDataCollection {
    @Override
    public VhDataCollection newViewHolder() {
        return new VhBuildReviewData();
    }
}
