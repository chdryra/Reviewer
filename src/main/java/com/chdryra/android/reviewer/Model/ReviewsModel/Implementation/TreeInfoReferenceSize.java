/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeInfoReferenceSize<T extends HasReviewId> extends TreeSizeReferenceBasic<T> {
    public TreeInfoReferenceSize(TreeInfoReference<T> dataReference) {
        super(dataReference);
    }

    @Override
    protected TreeInfoReference<T> getReference() {
        return (TreeInfoReference<T>) super.getReference();
    }

    @Override
    protected void incrementForChild(ReviewNode child) {
        getReference().getData(child.getReviewId(), new TreeDataReferenceBasic.GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                addSize(items.size());
                notifyValueBinders(getSize());
            }
        });
    }

    @Override
    protected void decrementForChild(ReviewNode child) {
        getReference().getData(child.getReviewId(), new TreeDataReferenceBasic.GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                removeSize(items.size());
                notifyValueBinders(getSize());
            }
        });
    }

    @Override
    protected void doDereference(final DereferenceCallback<DataSize> callback) {
        getReference().getData(new TreeDataReferenceBasic.GetDataCallback<T>() {
            @Override
            public void onData(IdableList<T> items) {
                setSize(items.size());
                callback.onDereferenced(getSize(), CallbackMessage.ok());
            }
        });
    }
}
