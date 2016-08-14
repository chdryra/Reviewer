/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDataRow;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.DataReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbItemReference<T extends ReviewDataRow<T>, R extends HasReviewId>
        extends DataReferenceBasic<R>
        implements ReviewItemReference<R> {
    private DataLoader<T> mLoader;
    private ArrayList<ReferenceBinder<R>> mBinders;
    private Converter<T, R> mConverter;

    public interface Converter<T, R extends HasReviewId> {
        R convert(T data);
    }

    public DbItemReference(DataLoader.RowLoader<T> loader, Converter<T, R> converter) {
        mLoader = loader;
        mConverter = converter;
        mBinders = new ArrayList<>();
    }

    @Override
    public ReviewId getReviewId() {
        return mLoader.getReviewId();
    }

    @Override
    public void dereference(final DereferenceCallback<R> callback) {
        mLoader.onLoaded(new DataLoader.LoadedListener<T>() {
            @Override
            public void onLoaded(IdableList<T> data) {
                if(data.size() > 0) {
                    callback.onDereferenced(mConverter.convert(data.getItem(0)), CallbackMessage.ok());
                } else {
                    callback.onDereferenced(null, CallbackMessage.error("No item in database for this reference"));
                }
            }
        }).execute();
    }

    @Override
    public void bindToValue(final ReferenceBinder<R> binder) {
        if (!mBinders.contains(binder)) mBinders.add(binder);
        dereference(new DereferenceCallback<R>() {
            @Override
            public void onDereferenced(@Nullable R data, CallbackMessage message) {
                if (data != null && !message.isError()) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<R> binder) {
        if (mBinders.contains(binder)) mBinders.remove(binder);
    }
}
