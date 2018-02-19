/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;


import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleItemReference;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbItemDereferencer<Row extends ReviewDataRow<Row>, Value extends HasReviewId> implements SimpleItemReference.Dereferencer<Value> {
    private final DataLoader<Row> mLoader;
    private final Converter<Row, Value> mConverter;

    public interface Converter<T, R extends HasReviewId> {
        R convert(T data);
    }

    public DbItemDereferencer(DataLoader<Row> loader, Converter<Row, Value> converter) {
        mLoader = loader;
        mConverter = converter;
    }

    @Override
    public ReviewId getReviewId() {
        return mLoader.getReviewId();
    }

    @Override
    public void dereference(final DataReference.DereferenceCallback<Value> callback) {
        mLoader.onLoaded(new DataLoader.LoadedListener<Row>() {
            @Override
            public void onLoaded(IdableList<Row> data) {
                if (data.size() > 0) {
                    callback.onDereferenced(new DataValue<>(mConverter.convert(data.get(0))));
                } else {
                    callback.onDereferenced(new DataValue<Value>(CallbackMessage.error("No item in database for this reference")));
                }
            }
        }).execute();
    }
}
