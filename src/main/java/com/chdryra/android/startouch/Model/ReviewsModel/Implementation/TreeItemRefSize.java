/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeItemRefSize<Value extends HasReviewId> extends TreeSizeRefBasic<Value> {
    public TreeItemRefSize(TreeItemListRef<Value> dataReference) {
        super(dataReference);
    }

    @Override
    protected TreeItemListRef<Value> getReference() {
        //TODO make type safe
        return (TreeItemListRef<Value>) super.getReference();
    }

    @Override
    protected void incrementForChild(ReviewNode child) {
        getReference().getData(child.getReviewId(), new TreeDataReferenceBasic.GetDataCallback<Value>() {
            @Override
            public void onData(IdableList<Value> items) {
                addSize(items.size());
                notifyValueBinders(getSize());
            }
        });
    }

    @Override
    protected void decrementForChild(ReviewNode child) {
        getReference().getData(child.getReviewId(), new TreeDataReferenceBasic.GetDataCallback<Value>() {
            @Override
            public void onData(IdableList<Value> items) {
                removeSize(items.size());
                notifyValueBinders(getSize());
            }
        });
    }

    @Override
    protected void doDereference(final DereferenceCallback<DataSize> callback) {
        getReference().getData(new TreeDataReferenceBasic.GetDataCallback<Value>() {
            @Override
            public void onData(IdableList<Value> items) {
                setSize(items.size());
                callback.onDereferenced(new DataValue<>(getSize()));
            }
        });
    }
}
