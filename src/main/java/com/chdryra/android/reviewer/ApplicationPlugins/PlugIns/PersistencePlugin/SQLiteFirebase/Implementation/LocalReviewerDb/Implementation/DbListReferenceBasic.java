/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.SimpleListReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbListReferenceBasic<Row extends ReviewDataRow<Row>, Value extends HasReviewId, Reference extends ReviewItemReference<Value>>
        extends SimpleListReference<Value, Reference> {
    private final Converter<Row, Value> mConverter;
    private final FactoryDbReference mReferenceFactory;
    private final DataLoader<Row> mLoader;

    public interface Converter<T extends ReviewDataRow<T>, R extends HasReviewId> {
        IdableList<R> convert(IdableList<T> data);
    }

    DbListReferenceBasic(DataLoader<Row> loader,
                         FactoryDbReference referenceFactory,
                         Converter<Row, Value> converter) {
        super(new ListDereferencer<>(loader, converter));
        mReferenceFactory = referenceFactory;
        mConverter = converter;
        mLoader = loader;
    }

    Converter<Row, Value> getConverter() {
        return mConverter;
    }

    FactoryDbReference getDbReferenceFactory() {
        return mReferenceFactory;
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return mReferenceFactory.newSizeReference(new Dereferencer<DataSize>() {
            @Override
            public ReviewId getReviewId() {
                return DbListReferenceBasic.this.getReviewId();
            }

            @Override
            public void dereference(final DereferenceCallback<DataSize> callback) {
                DbListReferenceBasic.this.dereference(new DereferenceCallback<IdableList<Value>>() {
                    @Override
                    public void onDereferenced(DataValue<IdableList<Value>> value) {
                        DataSize size = value.hasValue() ?
                                value.getData().getDataSize() : new DatumSize(getReviewId(), 0);
                        callback.onDereferenced(new DataValue<>(size));
                    }
                });
            }
        });
    }

    private static class ListDereferencer<T extends ReviewDataRow<T>, R extends HasReviewId>
            implements Dereferencer<IdableList<R>> {
        private final DataLoader<T> mLoader;
        private final Converter<T, R> mConverter;

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
                    callback.onDereferenced(new DataValue<>(mConverter.convert(data)));
                }
            }).execute();
        }
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<Value, Reference> callback) {
        final ReviewId id = getReviewId();
        final IdableList<Reference> refs = new IdableDataList<>(id);
        mLoader.onLoaded(new DataLoader.LoadedListener<Row>() {
            @Override
            public void onLoaded(IdableList<Row> data) {
                if (data.size() == 0) invalidate();
                for (Row datum : data) {
                    refs.add(newReference(mLoader.newRowLoader(datum.getRowId()), datum));
                }

                callback.onItemReferences(refs);
            }
        }).execute();
    }

    protected abstract Reference newReference(DataLoader.RowLoader<Row> loader, Row datum);
}
