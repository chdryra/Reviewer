package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilder;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation
        .ReviewBuilderImpl;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilder {
    private ConverterGv mConverterGv;
    private TagsManager mTagsManager;
    private FactoryReview mFactoryReview;
    private DataValidator mDataValidator;

    public FactoryReviewBuilder(ConverterGv converterGv, TagsManager tagsManager, FactoryReview
            factoryReview, DataValidator dataValidator) {
        mConverterGv = converterGv;
        mTagsManager = tagsManager;
        mFactoryReview = factoryReview;
        mDataValidator = dataValidator;
    }

    public ReviewBuilder newBuilder() {
        return new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReview,
                new FactoryDataBuilder(mConverterGv),
                mDataValidator);
    }
}
