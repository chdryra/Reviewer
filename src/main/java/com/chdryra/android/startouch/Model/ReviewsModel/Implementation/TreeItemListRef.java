/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.startouch.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.startouch.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.startouch.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 07/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeItemListRef<Value extends HasReviewId> extends
        TreeDataReferenceBasic<Value, ReviewItemReference<Value>> implements DataListRef<Value> {
    private final FactoryMdReference mReferenceFactory;
    private final VisitorFactory.ItemVisitor<Value> mVisitorFactory;

    public TreeItemListRef(ReviewNode root,
                           FactoryMdReference referenceFactory,
                           FactoryNodeTraverser traverserFactory,
                           VisitorFactory.ItemVisitor<Value> visitorFactory) {
        super(root, traverserFactory);
        mVisitorFactory = visitorFactory;
        mReferenceFactory = referenceFactory;
    }

    @Nullable
    @Override
    protected IdableList<Value> getNullValue() {
        return new IdableDataList<>(new DatumReviewId());
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<Value, ReviewItemReference<Value>>
                                             callback) {
        dereference(new DereferenceCallback<IdableList<Value>>() {
            @Override
            public void onDereferenced(DataValue<IdableList<Value>> value) {
                IdableList<ReviewItemReference<Value>> references
                        = new IdableDataList<>(getReviewId());
                if (value.hasValue()) {
                    for (Value datum : value.getData()) {
                        references.add(mReferenceFactory.getReferenceFactory().newWrapper(datum));
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
        //TODO make type safe
        VisitorDataGetter<Value> getter = (VisitorDataGetter<Value>) visitor;
        callback.onData(getter.getData());
    }
}
