/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataReference<T extends HasReviewId> extends TreeDataReferenceBasic<T> {
    private FactoryMdReference mReferenceFactory;
    private VisitorFactory<T> mVisitorFactory;

    public interface VisitorFactory<T extends HasReviewId> {
        VisitorDataGetter<ReviewListReference<T>> newVisitor();
    }
    public TreeDataReference(ReviewNode root,
                             FactoryMdReference referenceFactory,
                             FactoryNodeTraverser traverserFactory,
                             VisitorFactory<T> visitorFactory) {
        super(root, traverserFactory);
        mVisitorFactory = visitorFactory;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<T> callback) {
        doTraversal(new TreeTraversalCallback() {
            @Override
            public void onTraversed(VisitorReviewNode visitor) {
                VisitorDataGetter<ReviewListReference<T>> getter = castVisitor(visitor);
                ListRefsToItemRefs converter = new ListRefsToItemRefs(getter.getData(), callback);
                converter.convert();
            }
        });
    }

    @Override
    public ReviewItemReference<DataSize> getSizeReference() {
        return mReferenceFactory.newSize(this);
    }

    @Override
    public VisitorReviewNode newVisitor() {
        return mVisitorFactory.newVisitor();
    }

    @Override
    public void onDataTraversalComplete(VisitorReviewNode visitor, GetDataCallback<T> method) {
        VisitorDataGetter<ReviewListReference<T>> getter = castVisitor(visitor);
        ListsDereferencer<T> dereferencer = new ListsDereferencer<>(getter.getData(), method);
        dereferencer.dereference();
    }

    private VisitorDataGetter<ReviewListReference<T>> castVisitor(VisitorReviewNode visitor) {
        return (VisitorDataGetter<ReviewListReference<T>>) visitor;
    }

    private class ListRefsToItemRefs {
        private IdableList<ReviewListReference<T>> mRefs;
        private ItemReferencesCallback<T> mCallback;
        private IdableList<ReviewItemReference<T>> mData;
        private int mNumDereferences = 0;

        public ListRefsToItemRefs(IdableList<ReviewListReference<T>> refs,
                                  ItemReferencesCallback<T> callback) {
            mRefs = refs;
            mCallback = callback;
        }

        private void convert() {
            mData = new IdableDataList<>(mRefs.getReviewId());
            for(ReviewListReference<T> ref : mRefs) {
                ref.toItemReferences(new ItemReferencesCallback<T>() {
                    @Override
                    public void onItemReferences(IdableList<ReviewItemReference<T>> references) {
                        add(references);
                    }
                });
            }
        }

        private void add(IdableList<ReviewItemReference<T>> data) {
            mData.addAll(data);
            mNumDereferences++;
            if(mNumDereferences == mRefs.size()) mCallback.onItemReferences(mData);
        }
    }
}
