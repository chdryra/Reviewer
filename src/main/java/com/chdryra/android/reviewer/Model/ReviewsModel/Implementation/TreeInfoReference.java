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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeInfoReference<T extends HasReviewId> extends TreeDataReferenceBasic<T> {
    private FactoryMdReference mReferenceFactory;
    private VisitorFactory<T> mVisitorFactory;

    public interface VisitorFactory<T extends HasReviewId> {
        VisitorDataGetter<T> newVisitor();
    }

    public TreeInfoReference(ReviewNode root,
                             FactoryMdReference referenceFactory,
                             FactoryNodeTraverser traverserFactory,
                             VisitorFactory<T> visitorFactory) {
        super(root, traverserFactory);
        mVisitorFactory = visitorFactory;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<T> callback) {
        dereference(new DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                IdableList<ReviewItemReference<T>> references = new IdableDataList<>(getReviewId());
                if(data != null && !message.isError()) {
                    for(T datum : data) {
                        references.add(mReferenceFactory.newDataReference(getReviewId(), datum));
                    }
                }

                callback.onItemReferences(references);
            }
        });
    }

    @Override
    public ReviewItemReference<DataSize> getSizeReference() {
        return mReferenceFactory.newSizeReference(this);
    }

    @Override
    public VisitorReviewNode newVisitor() {
        return mVisitorFactory.newVisitor();
    }

    @Override
    public void onDataTraversalComplete(VisitorReviewNode visitor, GetDataCallback<T> method) {
        VisitorDataGetter<T> getter = (VisitorDataGetter<T>) visitor;
        method.onData(getter.getData());
    }
}
