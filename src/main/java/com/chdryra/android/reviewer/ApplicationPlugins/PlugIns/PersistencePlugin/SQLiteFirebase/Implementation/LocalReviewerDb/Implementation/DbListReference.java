/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbListReference<T extends ReviewDataRow<T>, R extends HasReviewId>
        extends SimpleReference<IdableList<R>>
        implements ReviewListReference<R> {
    private DataLoader<T> mLoader;
    private Converter<T, R> mConverter;
    private FactoryDbReference mReferenceFactory;

    public interface Converter<T extends ReviewDataRow<T>, R extends HasReviewId> {
        IdableList<R> convert(IdableList<T> data);
    }

    public DbListReference(DataLoader<T> loader, FactoryDbReference referenceFactory, Converter<T, R> converter) {
        super(new ListDereferencer<>(loader, converter));
        mLoader = loader;
        mReferenceFactory = referenceFactory;
        mConverter = converter;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<R> callback) {
        final ReviewId id = getReviewId();
        final IdableList<ReviewItemReference<R>> refs = new IdableDataList<>(id);
        mLoader.onLoaded(new DataLoader.LoadedListener<T>() {
            @Override
            public void onLoaded(IdableList<T> data) {
                for(T datum : data) {
                    DataLoader.RowLoader<T> itemLoader = mLoader.newRowLoader(datum.getRowId());
                    refs.add(mReferenceFactory.newItemReference(itemLoader, newItemConverter()));
                }

                callback.onItemReferences(refs);
            }
        }).execute();
    }

    @NonNull
    private DbItemReference.Converter<T, R> newItemConverter() {
        return new DbItemReference.Converter<T, R>() {
            @Override
            public R convert(T data) {
                IdableList<T> list = new IdableDataList<T>(getReviewId());
                list.add(data);
                IdableList<R> convert = mConverter.convert(list);
                return convert.getItem(0);
            }
        };
    }

    @Override
    public void bindToItems(ListItemBinder<R> binder) {

    }

    @Override
    public void unbindFromItems(ListItemBinder<R> binder) {

    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return mReferenceFactory.newSizeReference(new SimpleReference.Dereferencer<DataSize>() {
            @Override
            public ReviewId getReviewId() {
                return DbListReference.this.getReviewId();
            }

            @Override
            public void dereference(final DereferenceCallback<DataSize> callback) {
                DbListReference.this.dereference(new DereferenceCallback<IdableList<R>>() {
                    @Override
                    public void onDereferenced(@Nullable IdableList<R> data, CallbackMessage message) {
                        DataSize size = data != null ? data.getDataSize() : new DatumSize(getReviewId(), 0);
                        callback.onDereferenced(size, message);
                    }
                });
            }
        });
    }

    private static class ListDereferencer<T extends ReviewDataRow<T>, R extends HasReviewId>
            implements SimpleReference.Dereferencer<IdableList<R>> {
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
