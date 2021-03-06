/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbDataListRef<Row extends ReviewDataRow<Row>, Value extends HasReviewId>
        extends DbListReferenceBasic<Row, Value, ReviewItemReference<Value>> implements
        DataListRef<Value> {
    private final FactoryDbReference mReferenceFactory;

    public DbDataListRef(DataLoader<Row> loader,
                         FactoryDbReference referenceFactory,
                         Converter<Row, Value> converter) {
        super(loader, referenceFactory, converter);
        mReferenceFactory = referenceFactory;
    }

    @Override
    protected ReviewItemReference<Value> newReference(DataLoader.RowLoader<Row> loader, Row datum) {
        return mReferenceFactory.newItemReference(loader, newItemConverter());
    }

    @NonNull
    private DbItemDereferencer.Converter<Row, Value> newItemConverter() {
        return new DbItemDereferencer.Converter<Row, Value>() {
            @Override
            public Value convert(Row data) {
                IdableList<Row> list = new IdableDataList<>(getReviewId());
                list.add(data);
                IdableList<Value> convert = getConverter().convert(list);
                return convert.get(0);
            }
        };
    }
}
