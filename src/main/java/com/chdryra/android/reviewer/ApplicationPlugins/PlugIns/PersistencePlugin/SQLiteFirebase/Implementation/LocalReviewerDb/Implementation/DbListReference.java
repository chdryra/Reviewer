/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleListReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbListReference<Row extends ReviewDataRow<Row>, ValueType extends HasReviewId>
        extends SimpleListReference<ValueType> {
    private DataLoader<Row> mLoader;
    private Converter<Row, ValueType> mConverter;
    private FactoryDbReference mReferenceFactory;

    public interface Converter<T extends ReviewDataRow<T>, R extends HasReviewId> {
        IdableList<R> convert(IdableList<T> data);
    }

    public DbListReference(DataLoader<Row> loader, FactoryDbReference referenceFactory,
                           Converter<Row, ValueType> converter) {
        super(new ListDereferencer<>(loader, converter));
        mLoader = loader;
        mReferenceFactory = referenceFactory;
        mConverter = converter;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<ValueType> callback) {
        final ReviewId id = getReviewId();
        final IdableList<ReviewItemReference<ValueType>> refs = new IdableDataList<>(id);
        mLoader.onLoaded(new DataLoader.LoadedListener<Row>() {
            @Override
            public void onLoaded(IdableList<Row> data) {
                if (data.size() == 0) invalidate();
                for (Row datum : data) {
                    DataLoader.RowLoader<Row> itemLoader = mLoader.newRowLoader(datum.getRowId());
                    refs.add(mReferenceFactory.newItemReference(itemLoader, newItemConverter()));
                }

                callback.onItemReferences(refs);
            }
        }).execute();
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return mReferenceFactory.newSizeReference(new SimpleItemReference.Dereferencer<DataSize>() {
            @Override
            public ReviewId getReviewId() {
                return DbListReference.this.getReviewId();
            }

            @Override
            public void dereference(final DereferenceCallback<DataSize> callback) {
                DbListReference.this.dereference(new DereferenceCallback<IdableList<ValueType>>() {
                    @Override
                    public void onDereferenced(@Nullable IdableList<ValueType> data,
                                               CallbackMessage message) {
                        DataSize size = data != null ? data.getDataSize() : new DatumSize
                                (getReviewId(), 0);
                        callback.onDereferenced(size, message);
                    }
                });
            }
        });
    }

    @NonNull
    private DbItemDereferencer.Converter<Row, ValueType> newItemConverter() {
        return new DbItemDereferencer.Converter<Row, ValueType>() {
            @Override
            public ValueType convert(Row data) {
                IdableList<Row> list = new IdableDataList<>(getReviewId());
                list.add(data);
                IdableList<ValueType> convert = mConverter.convert(list);
                return convert.getItem(0);
            }
        };
    }

    private static class ListDereferencer<T extends ReviewDataRow<T>, R extends HasReviewId>
            implements SimpleItemReference.Dereferencer<IdableList<R>> {
        private DataLoader<T> mLoader;
        private Converter<T, R> mConverter;

        public ListDereferencer(DataLoader<T> loader, Converter<T, R> converter) {
            mLoader = loader;
            mConverter = converter;
        }

        @Override
        public ReviewId getReviewId() {
            return mLoader.getReviewId();
        }

        @Override
        public void dereference(final DereferenceCallback<IdableList<R>> callback) {
            mLoader.onLoaded(new DataLoader.LoadedListener<T>() {
                @Override
                public void onLoaded(IdableList<T> data) {
                    callback.onDereferenced(mConverter.convert(data), CallbackMessage.ok());
                }
            }).execute();
        }
    }
}
