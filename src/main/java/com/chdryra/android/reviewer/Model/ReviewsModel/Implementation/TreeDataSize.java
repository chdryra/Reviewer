/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataSize<T extends HasReviewId> extends DataReferenceBasic<DataSize>
        implements
        ReviewItemReference<DataSize>, DataReference.InvalidationListener, ReviewNode.NodeObserver{
    private static final CallbackMessage OK = CallbackMessage.ok();
    private TreeDataReferenceBasic<T> mDataReference;
    private Collection<ReferenceBinder<DataSize>> mValueBinders;

    public TreeDataSize(TreeDataReferenceBasic<T> dataReference) {
        mDataReference = dataReference;
        mDataReference.registerListener(this);
        mValueBinders = new ArrayList<>();
    }

    @Override
    public void onReferenceInvalidated(DataReference<?> reference) {
        invalidate();
    }

    @Override
    public ReviewId getReviewId() {
        return mDataReference.getReviewId();
    }

    @Override
    public void dereference(final DereferenceCallback<DataSize> callback) {
        mDataReference.doTraversal(new TreeDataReferenceBasic.PostTraversalMethod<T>() {
            @Override
            public void execute(IdableList<T> items) {
                callback.onDereferenced(new DatumSize(getReviewId(), items.size()), OK);
            }
        });
    }

    @Override
    public void bindToValue(final ReferenceBinder<DataSize> binder) {
        if (!mValueBinders.contains(binder)) mValueBinders.add(binder);
        dereference(new DereferenceCallback<DataSize>() {
            @Override
            public void onDereferenced(@Nullable DataSize data, CallbackMessage message) {
                if(data != null) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<DataSize> binder) {
        if (mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public void onChildAdded(ReviewNode child) {

    }

    @Override
    public void onChildRemoved(ReviewNode child) {

    }

    @Override
    public void onNodeChanged() {

    }
}
