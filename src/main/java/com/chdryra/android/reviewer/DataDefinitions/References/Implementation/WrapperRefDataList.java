/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperRefDataList<Value extends HasReviewId> extends StaticListReferenceBasic<Value, ReviewItemReference<Value>>
        implements RefDataList<Value> {

    private final FactoryReference mFactory;

    public WrapperRefDataList(IdableList<Value> value, FactoryReference factory) {
        super(value);
        mFactory = factory;
    }

    @Override
    protected ReviewItemReference<Value> newStaticReference(Value item) {
        return mFactory.newWrapper(item);
    }
}
