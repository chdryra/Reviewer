package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation.ReviewBuilderImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;

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

    public FactoryReviewBuilder(ConverterGv converterGv,
                                TagsManager tagsManager,
                                FactoryReviews factoryReviews,
                                FactoryDataBuilder dataBuilderfactory,
                                DataValidator dataValidator) {
        mConverterGv = converterGv;
        mTagsManager = tagsManager;
        mFactoryReviews = factoryReviews;
        mDataValidator = dataValidator;
        mDataBuilderFactory = dataBuilderfactory;
    }

    public ReviewBuilder newBuilder() {
        return new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReviews,
                mDataBuilderFactory,
                mDataValidator);
    }
}
