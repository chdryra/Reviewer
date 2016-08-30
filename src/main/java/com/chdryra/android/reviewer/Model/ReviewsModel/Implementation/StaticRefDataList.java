/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticRefDataList<Value extends HasReviewId> extends StaticListReferenceBasic<Value, ReviewItemReference<Value>>
        implements RefDataList<Value> {

    private final FactoryMdReference mFactory;

    public StaticRefDataList(IdableList<Value> value, FactoryMdReference factory) {
        super(value);
        mFactory = factory;
    }

    @Override
    protected ReviewItemReference<Value> newStaticReference(Value item) {
        return mFactory.newWrapper(item);
    }
}
