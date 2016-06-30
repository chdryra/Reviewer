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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 21/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MetaBinder extends ReferenceBinder {
    private MetaReference mReference;

    private MetaBinders.ReviewsBinder mReviews;
    private MetaBinders.AuthorsBinder mAuthors;
    private MetaBinders.SubjectsBinder mSubjects;
    private MetaBinders.DatesBinder mDates;

    private ReferenceBinders.SizeBinder mReviewsSize;
    private ReferenceBinders.SizeBinder mAuthorsSize;
    private ReferenceBinders.SizeBinder mSubjectsSize;
    private ReferenceBinders.SizeBinder mDatesSize;

    private List<MetaDataBinder> mDataBinders;
    private List<MetaDataSizeBinder> mSizeBinders;

    public interface MetaDataBinder extends DataBinder,
            MetaReference.ReviewsCallback,
            MetaReference.AuthorsCallback,
            MetaReference.SubjectsCallback,
            MetaReference.DatesCallback {
    }

    public interface MetaDataSizeBinder extends DataSizeBinder,
            MetaReference.ReviewsSizeCallback,
            MetaReference.AuthorsSizeCallback,
            MetaReference.SubjectsSizeCallback,
            MetaReference.DatesSizeCallback {

    }

    public MetaBinder(MetaReference reference) {
        super(reference);
        mReference = reference;

        mReviews = new Reviews();
        mAuthors = new Authors();
        mSubjects = new Subjects();
        mDates = new Dates();

        mReviewsSize = new ReviewsSize();
        mAuthorsSize = new AuthorsSize();
        mSubjectsSize = new SubjectsSize();
        mDatesSize = new DatesSize();

        mDataBinders = new ArrayList<>();
        mSizeBinders = new ArrayList<>();
    }

    public void registerDataBinder(MetaDataBinder binder) {
        if (!mDataBinders.contains(binder)) mDataBinders.add(binder);
        super.registerDataBinder(binder);
    }

    public void unregisterDataBinder(MetaDataBinder binder) {
        if (mDataBinders.contains(binder)) mDataBinders.remove(binder);
        super.unregisterDataBinder(binder);
    }

    public void registerSizeBinder(MetaDataSizeBinder binder) {
        if (!mSizeBinders.contains(binder)) mSizeBinders.add(binder);
        super.registerSizeBinder(binder);
    }

    public void unregisterSizeBinder(MetaDataSizeBinder binder) {
        if (mSizeBinders.contains(binder)) mSizeBinders.remove(binder);
        super.unregisterSizeBinder(binder);
    }

    public void getReviews(final MetaReference.ReviewsCallback callback) {
        mReference.getData(new MetaReference.ReviewsCallback() {
            @Override
            public void onReviews(IdableList<ReviewReference> reviews, CallbackMessage message) {
                callback.onReviews(reviews, message);
            }
        });
    }

    public void getAuthors(final MetaReference.AuthorsCallback callback) {
        mReference.getData(new MetaReference.AuthorsCallback() {
            @Override
            public void onAuthors(IdableList<? extends DataAuthorReview> authors, CallbackMessage
                    message) {
                callback.onAuthors(authors, message);
            }
        });
    }

    public void getSubjects(final MetaReference.SubjectsCallback callback) {
        mReference.getData(new MetaReference.SubjectsCallback() {
            @Override
            public void onSubjects(IdableList<? extends DataSubject> subjects, CallbackMessage 
                    message) {
                callback.onSubjects(subjects, message);
            }
        });
    }

    public void getDates(final MetaReference.DatesCallback callback) {
        mReference.getData(new MetaReference.DatesCallback() {
            @Override
            public void onDates(IdableList<? extends DataDateReview> dates, CallbackMessage 
                    message) {
                callback.onDates(dates, message);
            }
        });
    }

    public void getNumReviews(final MetaReference.ReviewsSizeCallback callback) {
        mReference.getSize(new MetaReference.ReviewsSizeCallback() {
            @Override
            public void onNumReviews(DataSize size, CallbackMessage message) {
                callback.onNumReviews(size, message);
            }
        });
    }

    public void getNumAuthors(final MetaReference.AuthorsSizeCallback callback) {
        mReference.getSize(new MetaReference.AuthorsSizeCallback() {
            @Override
            public void onNumAuthors(DataSize size, CallbackMessage message) {
                callback.onNumAuthors(size, message);
            }
        });
    }

    public void getNumSubjects(final MetaReference.SubjectsSizeCallback callback) {
        mReference.getSize(new MetaReference.SubjectsSizeCallback() {
            @Override
            public void onNumSubjects(DataSize size, CallbackMessage message) {
                callback.onNumSubjects(size, message);
            }
        });
    }

    public void getNumDates(final MetaReference.DatesSizeCallback callback) {
        mReference.getSize(new MetaReference.DatesSizeCallback() {
            @Override
            public void onNumDates(DataSize size, CallbackMessage message) {
                callback.onNumDates(size, message);
            }
        });
    }

    protected void fireForBinder(final MetaDataBinder callback) {
        getReviews(callback);
        getAuthors(callback);
        getSubjects(callback);
        getDates(callback);
        super.fireForBinder(callback);
    }

    protected void fireForBinder(final MetaDataSizeBinder callback) {
        getNumReviews(callback);
        getNumAuthors(callback);
        getNumSubjects(callback);
        getNumDates(callback);
        super.fireForBinder(callback);
    }

    @Override
    public MetaReference getReference() {
        return mReference;
    }

    @Override
    protected void bind() {
        super.bind();
        mReference.bind(mReviews);
        mReference.bind(mAuthors);
        mReference.bind(mSubjects);
        mReference.bind(mDates);
        mReference.bindToReviews(mReviewsSize);
        mReference.bindToAuthors(mAuthorsSize);
        mReference.bindToSubjects(mSubjectsSize);
        mReference.bindToDates(mDatesSize);
    }

    @Override
    protected void unbind() {
        super.unbind();
        mReference.unbind(mReviews);
        mReference.unbind(mAuthors);
        mReference.unbind(mSubjects);
        mReference.unbind(mDates);
        mReference.unbindFromReviews(mReviewsSize);
        mReference.unbindFromAuthors(mAuthorsSize);
        mReference.unbindFromSubjects(mSubjectsSize);
        mReference.unbindFromDates(mDatesSize);
    }

    private void notifyDataBinders(BinderMethod<MetaDataBinder> method) {
        notifyBinders(mDataBinders, method);
    }

    private void notifySizeBinders(BinderMethod<MetaDataSizeBinder> method) {
        notifyBinders(mSizeBinders, method);
    }

    private class Reviews implements MetaBinders.ReviewsBinder {
        @Override
        public void onValue(final IdableList<ReviewReference> value) {
            notifyDataBinders(new BinderMethod<MetaDataBinder>() {
                @Override
                public void execute(MetaDataBinder binder) {
                    binder.onReviews(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Authors implements MetaBinders.AuthorsBinder {
        @Override
        public void onValue(final IdableList<? extends DataAuthorReview> value) {
            notifyDataBinders(new BinderMethod<MetaDataBinder>() {
                @Override
                public void execute(MetaDataBinder binder) {
                    binder.onAuthors(value, CallbackMessage.ok());
                }
            });
        }
    }

    private class Subjects implements MetaBinders.SubjectsBinder {
        @Override
        public void onValue(final IdableList<? extends DataSubject> value) {
            notifyDataBinders(new BinderMethod<MetaDataBinder>() {
                @Override
                public void execute(MetaDataBinder binder) {
                    binder.onSubjects(value, CallbackMessage.ok());
                }
            });
        }
    }
    
    private class Dates implements MetaBinders.DatesBinder {
        @Override
        public void onValue(final IdableList<? extends DataDateReview> value) {
            notifyDataBinders(new BinderMethod<MetaDataBinder>() {
                @Override
                public void execute(MetaDataBinder binder) {
                    binder.onDates(value, CallbackMessage.ok());
                }
            });
        }
    }
    
    private class ReviewsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<MetaDataSizeBinder>() {
                @Override
                public void execute(MetaDataSizeBinder binder) {
                    binder.onNumReviews(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class AuthorsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<MetaDataSizeBinder>() {
                @Override
                public void execute(MetaDataSizeBinder binder) {
                    binder.onNumAuthors(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class SubjectsSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<MetaDataSizeBinder>() {
                @Override
                public void execute(MetaDataSizeBinder binder) {
                    binder.onNumSubjects(size, CallbackMessage.ok());
                }
            });
        }
    }

    private class DatesSize implements ReferenceBinders.SizeBinder {
        @Override
        public void onValue(final DataSize size) {
            notifySizeBinders(new BinderMethod<MetaDataSizeBinder>() {
                @Override
                public void execute(MetaDataSizeBinder binder) {
                    binder.onNumDates(size, CallbackMessage.ok());
                }
            });
        }
    }
}
