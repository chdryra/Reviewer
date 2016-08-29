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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeRefItemList<Value extends HasReviewId> extends TreeDataReferenceBasic<Value, ReviewItemReference<Value>> implements RefDataList<Value>{
    private FactoryMdReference mReferenceFactory;
    private VisitorFactory.ItemVisitor<Value> mVisitorFactory;

    public TreeRefItemList(ReviewNode root,
                           FactoryMdReference referenceFactory,
                           FactoryNodeTraverser traverserFactory,
                           VisitorFactory.ItemVisitor<Value> visitorFactory) {
        super(root, traverserFactory);
        mVisitorFactory = visitorFactory;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<Value, ReviewItemReference<Value>>
                                             callback) {
        dereference(new DereferenceCallback<IdableList<Value>>() {
            @Override
            public void onDereferenced(@Nullable IdableList<Value> data, CallbackMessage message) {
                IdableList<ReviewItemReference<Value>> references = new IdableDataList<>
                        (getReviewId());
                if (data != null && !message.isError()) {
                    for (Value datum : data) {
                        references.add(mReferenceFactory.newWrapper(datum));
                    }
                }

                callback.onItemReferences(references);
            }
        });
    }

    @Override
    public ReviewItemReference<DataSize> getSize() {
        return mReferenceFactory.newSize(this);
    }

    @Override
    public VisitorReviewNode newVisitor() {
        return mVisitorFactory.newVisitor();
    }

    @Override
    public void onDataTraversalComplete(VisitorReviewNode visitor, GetDataCallback<Value>
            callback) {
        VisitorDataGetter<Value> getter = (VisitorDataGetter<Value>) visitor;
        callback.onData(getter.getData());
    }
}
