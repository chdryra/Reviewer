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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataReference<T extends HasReviewId> extends TreeDataReferenceBasic<T> {
    private VisitorFactory<T> mVisitorFactory;

    public interface VisitorFactory<T extends HasReviewId> {
        VisitorDataGetter<ReviewListReference<T>> newVisitor();
    }
    public TreeDataReference(ReviewNode root,
                             FactoryNodeTraverser traverserFactory,
                             VisitorFactory<T> visitorFactory) {
        super(root, traverserFactory);
        mVisitorFactory = visitorFactory;
    }

    @Override
    public void toItemReferences(final ItemReferencesCallback<T> callback) {
        doTraversal(new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {

            }
        });
    }

    @Override
    public ReviewItemReference<DataSize> getSizeReference() {
        return null;
    }

    @Override
    public VisitorReviewNode newVisitor() {
        return mVisitorFactory.newVisitor();
    }

    @Override
    public void onDataTraversalComplete(VisitorReviewNode visitor, GetDataCallback<T> method) {
        VisitorDataGetter<ReviewListReference<T>> getter = getVisitor(visitor);
        DataDereferencer dereferencer = new DataDereferencer(getter.getData(), method);
        dereferencer.dereference();
    }

    private VisitorDataGetter<ReviewListReference<T>> getVisitor(VisitorReviewNode visitor) {
        return (VisitorDataGetter<ReviewListReference<T>>) visitor;
    }

    private class DataDereferencer {
        private IdableList<ReviewListReference<T>> mRefs;
        private GetDataCallback<T> mPost;
        private IdableList<T> mData;
        private int mNumDereferences = 0;

        private DataDereferencer(IdableList<ReviewListReference<T>> refs, GetDataCallback<T> post) {
            mRefs = refs;
            mPost = post;
        }

        private void dereference() {
            mData = new IdableDataList<>(mRefs.getReviewId());
            for(ReviewListReference<T> ref : mRefs) {
                ref.dereference(new DereferenceCallback<IdableList<T>>() {
                    @Override
                    public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                        add(data, message);
                    }
                });
            }
        }

        private void add(@Nullable IdableList<T> data, CallbackMessage message) {
            if(data != null && !message.isError()) mData.addAll(data);
            mNumDereferences++;
            if(mNumDereferences == mRefs.size()) mPost.onData(mData);
        }
    }
}
