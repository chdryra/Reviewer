/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.ListsDereferencer;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.VisitorFactory;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeListReferences<Value extends HasReviewId,
        Reference extends ReviewItemReference<Value>,
        List extends ReviewListReference<Value, Reference>>
        extends TreeDataReferenceBasic<Value, Reference> {

    private final FactoryMdReference mReferenceFactory;
    private final VisitorFactory.ListVisitor<List> mVisitorFactory;

    TreeListReferences(ReviewNode root,
                       FactoryMdReference referenceFactory,
                       FactoryNodeTraverser traverserFactory,
                       VisitorFactory.ListVisitor<List> visitorFactory) {
        super(root, traverserFactory);
        mVisitorFactory = visitorFactory;
        mReferenceFactory = referenceFactory;
    }

    FactoryReference getReferenceFactory() {
        return mReferenceFactory.getReferenceFactory();
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<Value, Reference> callback) {
        doTraversal(new TreeTraversalCallback() {
            @Override
            public void onTraversed(VisitorReviewNode visitor) {
                VisitorDataGetter<List> getter = castVisitor(visitor);
                ListRefsToItemRefs<Value, Reference, List> converter
                        = new ListRefsToItemRefs<>(getter.getData(), callback);
                converter.convert();
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
    public void onDataTraversalComplete(VisitorReviewNode visitor, GetDataCallback<Value> callback) {
        VisitorDataGetter<List> getter = castVisitor(visitor);
        ListsDereferencer<Value, Reference, List> dereferencer
                = new ListsDereferencer<>(getter.getData(), callback);
        dereferencer.dereference();
    }

    private VisitorDataGetter<List> castVisitor(VisitorReviewNode visitor) {
        //TODO make type safe
        return (VisitorDataGetter<List>) visitor;
    }

    private static class ListRefsToItemRefs<V extends HasReviewId, R extends ReviewItemReference<V>, L extends ReviewListReference<V, R>> {
        private final IdableList<L> mRefs;
        private final ItemReferencesCallback<V, R> mCallback;
        private IdableList<R> mData;
        private int mNumDereferences = 0;

        public ListRefsToItemRefs(IdableList<L> refs,
                                  ItemReferencesCallback<V, R> callback) {
            mRefs = refs;
            mCallback = callback;
        }

        private void convert() {
            mData = new IdableDataList<>(mRefs.getReviewId());
            for (L ref : mRefs) {
                ref.toItemReferences(new ItemReferencesCallback<V, R>() {
                    @Override
                    public void onItemReferences(IdableList<R> references) {
                        add(references);
                    }
                });
            }
        }

        private void add(IdableList<R> data) {
            mData.addAll(data);
            if (++mNumDereferences == mRefs.size()) mCallback.onItemReferences(mData);
        }
    }
}
