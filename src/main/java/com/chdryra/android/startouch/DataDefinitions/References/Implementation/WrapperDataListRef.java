/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperDataListRef<Value extends HasReviewId> extends StaticListReferenceBasic<Value, ReviewItemReference<Value>>
        implements DataListRef<Value> {

    private final FactoryReferences mFactory;

    public WrapperDataListRef(IdableList<Value> value, FactoryReferences factory) {
        super(value);
        mFactory = factory;
    }

    @Override
    protected ReviewItemReference<Value> newStaticReference(Value item) {
        return mFactory.newWrapper(item);
    }
}
