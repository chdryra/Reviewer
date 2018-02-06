/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.ItemsDereferencer;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 05/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeListRefSize<Value extends HasReviewId> extends TreeSizeRefBasic<Value> {

    public TreeListRefSize(TreeListReferences<Value, ?, ?> dataReference) {
        super(dataReference);
    }

    @Override
    protected TreeListReferences<Value, ?, ?> getReference() {
        //TODO make type safe
        return (TreeListReferences<Value, ?, ?>) super.getReference();
    }

    @Override
    protected void doDereference(final DereferenceCallback<DataSize> callback) {
        getReference().doTraversal(new TreeDataReferenceBasic.TreeTraversalCallback() {
            @Override
            public void onTraversed(VisitorReviewNode visitor) {
                ItemsDereferencer<DataSize> dereferencer
                        = new ItemsDereferencer<>(getSizeReferences(visitor),
                        setSizeAndNotify(callback));
                dereferencer.dereference();
            }
        });
    }

    @Override
    protected void incrementForChild(ReviewNode child) {
        getReference().doTraversal(child.getReviewId(), new TreeDataReferenceBasic
                .TreeTraversalCallback() {
            @Override
            public void onTraversed(VisitorReviewNode visitor) {
                ItemsDereferencer<DataSize> dereferencer
                        = new ItemsDereferencer<>(getSizeReferences(visitor), addSizeAndNotify());
                dereferencer.dereference();
            }
        });
    }

    @Override
    protected void decrementForChild(ReviewNode child) {
        getReference().doTraversal(child.getReviewId(), new TreeDataReferenceBasic
                .TreeTraversalCallback() {
            @Override
            public void onTraversed(VisitorReviewNode visitor) {
                ItemsDereferencer<DataSize> dereferencer
                        = new ItemsDereferencer<>(getSizeReferences(visitor), removeSizeAndNotify
                        ());
                dereferencer.dereference();
            }
        });
    }

    @NonNull
    private TreeDataReferenceBasic.GetDataCallback<DataSize> setSizeAndNotify(final
                                                                              DereferenceCallback<DataSize> callback) {
        return new TreeDataReferenceBasic.GetDataCallback<DataSize>() {
            @Override
            public void onData(IdableList<DataSize> items) {
                setSize(getTotal(items));
                callback.onDereferenced(new DataValue<>(getSize()));
            }
        };
    }

    private TreeDataReferenceBasic.GetDataCallback<DataSize> addSizeAndNotify() {
        return new TreeDataReferenceBasic.GetDataCallback<DataSize>() {
            @Override
            public void onData(IdableList<DataSize> items) {
                addSize(getTotal(items));
                notifyValueBinders(getSize());
            }
        };
    }

    private TreeDataReferenceBasic.GetDataCallback<DataSize> removeSizeAndNotify() {
        return new TreeDataReferenceBasic.GetDataCallback<DataSize>() {
            @Override
            public void onData(IdableList<DataSize> items) {
                removeSize(getTotal(items));
                notifyValueBinders(getSize());
            }
        };
    }

    private int getTotal(IdableList<DataSize> items) {
        int total = 0;
        for (DataSize item : items) {
            total += item.getSize();
        }
        return total;
    }

    @NonNull
    private IdableList<ReviewItemReference<DataSize>> getSizeReferences(VisitorReviewNode visitor) {
        //TODO make type safe
        VisitorDataGetter<? extends ReviewListReference<?, ?>> getter
                = (VisitorDataGetter<? extends ReviewListReference<?,?>>) visitor;
        IdableList<ReviewItemReference<DataSize>> refs = new IdableDataList<>(getReviewId());
        for (ReviewListReference<?, ?> reference : getter.getData()) {
            refs.add(reference.getSize());
        }

        return refs;
    }

}
