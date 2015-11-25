package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation
        .GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation
        .ReviewBuilderImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilder;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.Factories.FactoryGvData;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilder {
    private ConverterGv mConverterGv;
    private TagsManager mTagsManager;
    private FactoryReviews mFactoryReviews;
    private DataValidator mDataValidator;
    private FactoryDataBuilder mDataBuilderFactory;
    private FactoryGvData mDatafactory;

    public FactoryReviewBuilder(ConverterGv converterGv,
                                TagsManager tagsManager,
                                FactoryReviews factoryReviews,
                                FactoryDataBuilder dataBuilderfactory,
                                FactoryGvData dataFactory,
                                DataValidator dataValidator) {
        mConverterGv = converterGv;
        mTagsManager = tagsManager;
        mFactoryReviews = factoryReviews;
        mDataValidator = dataValidator;
        mDataBuilderFactory = dataBuilderfactory;
        mDatafactory = dataFactory;
    }

    public ReviewBuilder newBuilder() {
        return new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReviews,
                mDataBuilderFactory,
                mDatafactory,
                mDataValidator);
    }
}
