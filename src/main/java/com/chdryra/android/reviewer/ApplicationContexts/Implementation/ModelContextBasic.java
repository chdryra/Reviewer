/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryDataBucketer;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ModelContextBasic implements ModelContext {
    private FactoryReferences mReferenceFactory;
    private FactoryReviews mReviewsFactory;
    private FactoryDataBucketer mBucketerFactory;

    protected void setReviewsFactory(FactoryReviews factoryReviews) {
        mReviewsFactory = factoryReviews;
    }

    protected void setReferenceFactory(FactoryReferences referenceFactory) {
        mReferenceFactory = referenceFactory;
    }

    protected void setBucketerFactory(FactoryDataBucketer bucketerFactory) {
        mBucketerFactory = bucketerFactory;
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mReviewsFactory;
    }

    @Override
    public FactoryReferences getReferencesFactory() {
        return mReferenceFactory;
    }

    @Override
    public FactoryDataBucketer getBucketerFactory() {
        return mBucketerFactory;
    }
}
