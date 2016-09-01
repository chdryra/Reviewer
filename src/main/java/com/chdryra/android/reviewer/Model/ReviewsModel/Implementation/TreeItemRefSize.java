/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeItemRefSize<Value extends HasReviewId> extends TreeSizeRefBasic<Value> {
    public TreeItemRefSize(TreeRefItemList<Value> dataReference) {
        super(dataReference);
    }

    @Override
    protected TreeRefItemList<Value> getReference() {
        return (TreeRefItemList<Value>) super.getReference();
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
