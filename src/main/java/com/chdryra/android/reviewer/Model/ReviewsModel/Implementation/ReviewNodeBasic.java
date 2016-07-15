/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewNodeBasic extends ReviewReferenceBasic implements ReviewNode {
    private BindersManagerNode mBinders;
    protected ArrayList<NodeObserver> mObservers;

    public ReviewNodeBasic(FactoryBinders bindersFactory) {
        super(bindersFactory.newMetaBindersManager());
        mBinders = (BindersManagerNode) super.getBindersManager();
        mObservers = new ArrayList<>();
    }

    @Override
    public boolean isLeaf() {
        return getChildren().size() == 0 && isValidReference();
    }

    @Override
    public void unregisterObserver(NodeObserver binder) {
        if (mObservers.contains(binder)) mObservers.remove(binder);
    }

    @Override
    public void registerObserver(NodeObserver binder) {
        if (!mObservers.contains(binder)) mObservers.add(binder);
    }

    protected void notifyOnChildAdded(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildAdded(child);
        }
    }

    protected void notifyOnChildRemoved(ReviewNode child) {
        for (NodeObserver observer : mObservers) {
            observer.onChildRemoved(child);
        }
    }

    protected void notifyOnNodeChanged() {
        for (NodeObserver observer : mObservers) {
            observer.onNodeChanged();
        }
    }

    @Override
    public BindersManagerNode getBindersManager() {
        return mBinders;
    }


    protected void notifyReviewsBinders() {
        getData(new ReviewsCallback() {
            @Override
            public void onReviews(IdableList<ReviewReference> rev, CallbackMessage message) {
                if (!message.isError()) {
                    for (MetaBinders.ReviewsBinder binder : mBinders.getReviewsBinders()) {
                        binder.onValue(rev);
                    }
                }
            }
        });
    }

    protected void notifyAuthorsBinders() {
        getData(new AuthorsCallback() {
            @Override
            public void onAuthors(IdableList<? extends DataAuthorReview> rev, CallbackMessage message) {
                if (!message.isError()) {
                    for (MetaBinders.AuthorsBinder binder : mBinders.getAuthorsBinders()) {
                        binder.onValue(rev);
                    }
                }
            }
        });
    }

    protected void notifySubjectsBinders() {
        getData(new SubjectsCallback() {
            @Override
            public void onSubjects(IdableList<? extends DataSubject> rev, CallbackMessage message) {
                if (!message.isError()) {
                    for (MetaBinders.SubjectsBinder binder : mBinders.getSubjectsBinders()) {
                        binder.onValue(rev);
                    }
                }
            }
        });
    }

    protected void notifyDatesBinders() {
        getData(new DatesCallback() {
            @Override
            public void onDates(IdableList<? extends DataDateReview> rev, CallbackMessage message) {
                if (!message.isError()) {
                    for (MetaBinders.DatesBinder binder : mBinders.getDatesBinders()) {
                        binder.onValue(rev);
                    }
                }
            }
        });
    }

    protected void notifyNumReviewsBinders() {
        getSize(new ReviewsSizeCallback() {
            @Override
            public void onNumReviews(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumReviewsBinders()) {
                        binder.onValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumAuthorsBinders() {
        getSize(new AuthorsSizeCallback() {
            @Override
            public void onNumAuthors(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumAuthorsBinders()) {
                        binder.onValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumSubjectsBinders() {
        getSize(new SubjectsSizeCallback() {
            @Override
            public void onNumSubjects(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumSubjectsBinders()) {
                        binder.onValue(size);
                    }
                }
            }
        });
    }

    protected void notifyNumDatesBinders() {
        getSize(new DatesSizeCallback() {
            @Override
            public void onNumDates(DataSize size, CallbackMessage message) {
                if (!message.isError()) {
                    for (ReferenceBinders.SizeBinder binder : mBinders.getNumDatesBinders()) {
                        binder.onValue(size);
                    }
                }
            }
        });
    }

    @Override
    public void bind(final MetaBinders.ReviewsBinder binder) {
        mBinders.bind(binder);
        getData(new ReviewsCallback() {
            @Override
            public void onReviews(IdableList<ReviewReference> leaves, CallbackMessage message) {
                binder.onValue(leaves);
            }
        });
    }

    @Override
    public void bind(final MetaBinders.AuthorsBinder binder) {
        mBinders.bind(binder);
        getData(new AuthorsCallback() {
            @Override
            public void onAuthors(IdableList<? extends DataAuthorReview> leaves, CallbackMessage message) {
                binder.onValue(leaves);
            }
        });
    }

    @Override
    public void bind(final MetaBinders.SubjectsBinder binder) {
        mBinders.bind(binder);
        getData(new SubjectsCallback() {
            @Override
            public void onSubjects(IdableList<? extends DataSubject> leaves, CallbackMessage message) {
                binder.onValue(leaves);
            }
        });
    }

    @Override
    public void bind(final MetaBinders.DatesBinder binder) {
        mBinders.bind(binder);
        getData(new DatesCallback() {
            @Override
            public void onDates(IdableList<? extends DataDateReview> leaves, CallbackMessage message) {
                binder.onValue(leaves);
            }
        });
    }

    @Override
    public void bindToReviews(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToReviews(binder);
        getSize(new ReviewsSizeCallback() {
            @Override
            public void onNumReviews(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToAuthors(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToAuthors(binder);
        getSize(new AuthorsSizeCallback() {
            @Override
            public void onNumAuthors(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToSubjects(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToSubjects(binder);
        getSize(new SubjectsSizeCallback() {
            @Override
            public void onNumSubjects(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void bindToDates(final ReferenceBinders.SizeBinder binder) {
        mBinders.bindToDates(binder);
        getSize(new DatesSizeCallback() {
            @Override
            public void onNumDates(DataSize size, CallbackMessage message) {
                binder.onValue(size);
            }
        });
    }

    @Override
    public void unbind(MetaBinders.ReviewsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(MetaBinders.AuthorsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(MetaBinders.SubjectsBinder binder) {
        mBinders.unbind(binder);
    }

    @Override
    public void unbind(MetaBinders.DatesBinder binder) {
        mBinders.unbind(binder);
    }


    @Override
    public void unbindFromReviews(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromReviews(binder);
    }

    @Override
    public void unbindFromAuthors(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromAuthors(binder);
    }

    @Override
    public void unbindFromSubjects(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromSubjects(binder);
    }

    @Override
    public void unbindFromDates(ReferenceBinders.SizeBinder binder) {
        mBinders.unbindFromDates(binder);
    }
}
