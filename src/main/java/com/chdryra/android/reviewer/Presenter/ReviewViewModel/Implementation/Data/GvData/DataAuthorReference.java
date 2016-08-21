/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.DataReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAuthorReference extends DataReferenceBasic<DataAuthor> implements
        ReviewItemReference<DataAuthor> {
    private DataAuthorId mId;
    private DataReference<NamedAuthor> mReference;
    private Map<ReferenceBinder<DataAuthor>, ReferenceBinder<NamedAuthor>> mBinders;

    public DataAuthorReference(DataAuthorId id, DataReference<NamedAuthor> reference) {
        mId = id;
        mReference = reference;
        mBinders = new HashMap<>();
    }

    @Override
    public ReviewId getReviewId() {
        return mId.getReviewId();
    }

    @Override
    public void dereference(final DereferenceCallback<DataAuthor> callback) {
        if (mReference.isValidReference()) {
            mReference.dereference(new DereferenceCallback<NamedAuthor>() {
                @Override
                public void onDereferenced(@Nullable NamedAuthor data, CallbackMessage message) {
                    DataAuthor value = null;
                    if (data != null && !message.isError()) {
                        value = newAuthor(data);
                    } else if (data == null){
                        invalidate();
                    }
                    callback.onDereferenced(value, message);
                }
            });
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<DataAuthor> binder) {
        if (!mBinders.containsKey(binder)) {
            ReferenceBinder<NamedAuthor> authorBinder = newNamedAuthorBinder(binder);
            mReference.bindToValue(authorBinder);
            mBinders.put(binder, authorBinder);
        } else {
            dereference(new DereferenceCallback<DataAuthor>() {
                @Override
                public void onDereferenced(@Nullable DataAuthor data, CallbackMessage message) {
                    if (data != null && !message.isError()) binder.onReferenceValue(data);
                }
            });
        }
    }

    @Override
    public void unbindFromValue(ReferenceBinder<DataAuthor> binder) {
        if (mBinders.containsKey(binder)) mReference.unbindFromValue(mBinders.remove(binder));
    }

    @Override
    protected void onInvalidate() {
        super.onInvalidate();
        for (Map.Entry<ReferenceBinder<DataAuthor>, ReferenceBinder<NamedAuthor>> entry :
                mBinders.entrySet()) {
            mReference.unbindFromValue(entry.getValue());
            entry.getKey().onInvalidated(this);
        }
        mBinders.clear();
        mReference = null;
    }

    @NonNull
    private DatumAuthor newAuthor(NamedAuthor data) {
        return new DatumAuthor(getReviewId(), data);
    }

    @NonNull
    private ReferenceBinder<NamedAuthor> newNamedAuthorBinder(final ReferenceBinder<DataAuthor>
                                                                      binder) {
        return new ReferenceBinder<NamedAuthor>() {
            @Override
            public void onReferenceValue(NamedAuthor value) {
                binder.onReferenceValue(newAuthor(value));
            }

            @Override
            public void onInvalidated(DataReference<NamedAuthor> reference) {
                invalidate();
            }
        };
    }
}
