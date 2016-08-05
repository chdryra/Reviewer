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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ListItemBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Implementation.VisitorDataGetter;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 04/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class TreeDataReferenceBasic<T extends HasReviewId> extends
        DataReferenceBasic<IdableList<T>>
        implements
        ReviewListReference<T>,
        ReviewNode.NodeObserver {

    private static final String REFERENCES = "References";
    private VisitorFactory<T> mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;
    private ReviewNode mRoot;

    private Collection<ListItemBinder<T>> mItemBinders;
    private Collection<ReferenceBinder<IdableList<T>>> mValueBinders;

    public interface PostTraversalMethod<T extends HasReviewId> {
        void execute(IdableList<T> items);
    }

    public interface VisitorFactory<T extends HasReviewId> {
        VisitorDataGetter<T> newVisitor();
    }

    public TreeDataReferenceBasic(ReviewNode root,
                                  FactoryNodeTraverser traverserFactory,
                                  VisitorFactory<T> visitorFactory) {
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
        mRoot = root;

        mItemBinders = new ArrayList<>();
        mValueBinders = new ArrayList<>();

        mRoot.registerObserver(this);
    }

    public void doTraversal(PostTraversalMethod<T> post) {
        doTraversal(mRoot, post);
    }

    public void registerObserver(ReviewNode.NodeObserver observer) {
        mRoot.registerObserver(observer);
    }

    public void unregisterObserver(ReviewNode.NodeObserver observer) {
        mRoot.unregisterObserver(observer);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        if (hasItemBinders()) {
            doTraversal(child, new PostTraversalMethod<T>() {
                @Override
                public void execute(IdableList<T> items) {
                    notifyItemBindersAdd(items);
                }
            });
        }

        notifyValueBinders();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        if (hasItemBinders()) {
            doTraversal(child, new PostTraversalMethod<T>() {
                @Override
                public void execute(IdableList<T> items) {
                    notifyItemBindersRemove(items);
                }
            });
        }

        notifyValueBinders();
    }

    @Override
    public void onNodeChanged() {
        notifyAllBinders();
    }

    @Override
    public void bindToItems(final ListItemBinder<T> binder) {
        if (!mItemBinders.contains(binder)) mItemBinders.add(binder);
        doTraversal(new PostTraversalMethod<T>() {
            @Override
            public void execute(IdableList<T> items) {
                notifyItemBinderAdd(binder, items);
            }
        });
    }

    @Override
    public void unbindFromItems(ListItemBinder<T> binder) {
        if (mItemBinders.contains(binder)) mItemBinders.remove(binder);
    }

    @Override
    public void dereference(final DereferenceCallback<IdableList<T>> callback) {
        if(isValidReference()) {
            doTraversal(new PostTraversalMethod<T>() {
                @Override
                public void execute(IdableList<T> items) {
                    callback.onDereferenced(items, CallbackMessage.ok());
                }
            });
        } else {
            callback.onDereferenced(null, CallbackMessage.error("Invalid reference"));
        }
    }

    @Override
    public void bindToValue(final ReferenceBinder<IdableList<T>> binder) {
        if (!mValueBinders.contains(binder)) mValueBinders.add(binder);
        dereference(new DereferenceCallback<IdableList<T>>() {
            @Override
            public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                if (data != null && !message.isError()) binder.onReferenceValue(data);
            }
        });
    }

    @Override
    public void unbindFromValue(ReferenceBinder<IdableList<T>> binder) {
        if (mValueBinders.contains(binder)) mValueBinders.remove(binder);
    }

    @Override
    public boolean isValidReference() {
        return mRoot.getChildren().size() > 0 || mRoot.getReference() != null;
    }

    @Override
    protected void onInvalidate() {
        for (ListItemBinder<T> binder : mItemBinders) {
            binder.onInvalidated(this);
        }

        for (ReferenceBinder<IdableList<T>> binder : mValueBinders) {
            binder.onInvalidated(this);
        }

        mVisitorFactory = null;
        mTraverserFactory = null;
        mRoot = null;

        mItemBinders.clear();
        mItemBinders = null;
        mValueBinders.clear();
        mValueBinders = null;
    }

    @Override
    public ReviewId getReviewId() {
        return mRoot.getReviewId();
    }

    private void notifyValueBinders() {
        if (hasValueBinders()) {
            dereference(new DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                    if (data != null && !message.isError()) notifyValueBinders(data);
                }
            });
        }
    }

    private void notifyAllBinders() {
        if (hasValueBinders() || hasItemBinders()) {
            dereference(new DereferenceCallback<IdableList<T>>() {
                @Override
                public void onDereferenced(@Nullable IdableList<T> data, CallbackMessage message) {
                    if (data != null && !message.isError()) {
                        notifyValueBinders(data);
                        notifyItemBinders(data);
                    }
                }
            });
        }
    }

    private void notifyItemBinders(IdableList<T> items) {
        for (ListItemBinder<T> binder : mItemBinders) {
            binder.onListChanged(items);
        }
    }

    private boolean hasItemBinders() {
        return mItemBinders.size() > 0;
    }

    private boolean hasValueBinders() {
        return mValueBinders.size() > 0;
    }

    private void notifyItemBindersAdd(IdableList<T> data) {
        for (ListItemBinder<T> binder : mItemBinders) {
            notifyItemBinderAdd(binder, data);
        }
    }

    private void notifyItemBinderAdd(ListItemBinder<T> binder,
                                     IdableList<T> data) {
        for (T reference : data) {
            binder.onItemAdded(reference);
        }
    }

    private void notifyItemBindersRemove(IdableList<T> data) {
        for (ListItemBinder<T> binder : mItemBinders) {
            for (T reference : data) {
                binder.onItemRemoved(reference);
            }
        }
    }

    private void notifyValueBinders(IdableList<T> data) {
        for (ReferenceBinder<IdableList<T>> binder : mValueBinders) {
            binder.onReferenceValue(data);
        }
    }

    private void doTraversal(ReviewNode root, final PostTraversalMethod<T> post) {
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(root);
        traverser.addVisitor(REFERENCES, mVisitorFactory.newVisitor());
        traverser.traverse(new TreeTraverser.TraversalCallback() {
            @Override
            public void onTraversed(Map<String, VisitorReviewNode> visitors) {
                post.execute(getVisitor(visitors).getData());
            }
        });
    }

    private VisitorDataGetter<T> getVisitor(Map<String, VisitorReviewNode>
                                                    visitors) {
        return (VisitorDataGetter<T>) visitors.get(REFERENCES);
    }
}
