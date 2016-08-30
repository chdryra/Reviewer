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
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataReferenceBasic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAuthorReference extends DataReferenceBasic<DataAuthor> implements
        ReviewItemReference<DataAuthor> {
    private ReviewItemReference<DataAuthorId> mId;
    private AuthorsRepository mRepo;
    private DataReference<NamedAuthor> mReference;
    private Map<ReferenceBinder<DataAuthor>, ReferenceBinder<NamedAuthor>> mBinders;

    private interface NamedReferenceCallback {
        void onNameReferenceSet();
    }

    public DataAuthorReference(ReviewItemReference<DataAuthorId> id, AuthorsRepository repo) {
        mId = id;
        mRepo = repo;
        mBinders = new HashMap<>();
    }

    public ReviewItemReference<DataAuthorId> getIdReference() {
        return mId;
    }

    @Override
    public ReviewId getReviewId() {
        return mId.getReviewId();
    }

    @Override
    public void dereference(final DereferenceCallback<DataAuthor> callback) {
        if(isValidReference()) {
            if (mReference == null) {
                setNameReference(new NamedReferenceCallback() {
                    @Override
                    public void onNameReferenceSet() {
                        dereferenceNamedAuthor(callback);
                    }
                });
            } else {
                dereferenceNamedAuthor(callback);
            }
        } else {
            callback.onDereferenced(null, CallbackMessage.error("Invalid reference"));
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<DataAuthor> binder) {
        if(isValidReference()) {
            if (!mBinders.containsKey(binder)) {
                final ReferenceBinder<NamedAuthor> authorBinder = newNamedAuthorBinder(binder);
                mBinders.put(binder, authorBinder);
                if (mReference != null) {
                    bindToNamedReference(authorBinder);
                } else {
                    setNameReference(new NamedReferenceCallback() {
                        @Override
                        public void onNameReferenceSet() {
                            bindToNamedReference(authorBinder);
                        }
                    });
                }
            }
        } else {
            binder.onInvalidated(this);
        }
    }

    private void bindToNamedReference(ReferenceBinder<NamedAuthor> authorBinder) {
        mReference.bindToValue(authorBinder);
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

    private void setNameReference(final NamedReferenceCallback callback) {
        mId.dereference(new DereferenceCallback<DataAuthorId>() {
            @Override
            public void onDereferenced(@Nullable DataAuthorId data, CallbackMessage message) {
                if (data != null && !message.isError()) {
                    mReference = mRepo.getName(data);
                    callback.onNameReferenceSet();
                } else if(data == null){
                    invalidate();
                }
            }
        });
    }

    private void dereferenceNamedAuthor(final DereferenceCallback<DataAuthor> callback) {
        if (mReference != null && mReference.isValidReference()) {
            mReference.dereference(new DereferenceCallback<NamedAuthor>() {
                @Override
                public void onDereferenced(@Nullable NamedAuthor data, CallbackMessage message) {
                    DataAuthor value = null;
                    if (data != null && !message.isError()) {
                        value = newAuthor(data);
                    } else if (data == null) {
                        invalidate();
                    }
                    callback.onDereferenced(value, message);
                }
            });
        }
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
