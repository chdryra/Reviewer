/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.MetaBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BindersManagerMeta extends BindersManagerReference {
    private Collection<MetaBinders.ReviewsBinder> mReviewsBinders;
    private Collection<MetaBinders.AuthorsBinder> mAuthorsBinders;
    private Collection<MetaBinders.SubjectsBinder> mSubjectsBinders;
    private Collection<MetaBinders.DatesBinder> mDatesBinders;

    private Collection<ReferenceBinders.SizeBinder> mNumReviewsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumAuthorsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumSubjectsBinders;
    private Collection<ReferenceBinders.SizeBinder> mNumDatesBinders;

    public BindersManagerMeta() {
        mReviewsBinders = new ArrayList<>();
        mAuthorsBinders = new ArrayList<>();
        mSubjectsBinders = new ArrayList<>();
        mDatesBinders = new ArrayList<>();

        mNumReviewsBinders = new ArrayList<>();
        mNumAuthorsBinders = new ArrayList<>();
        mNumSubjectsBinders = new ArrayList<>();
        mNumDatesBinders = new ArrayList<>();
    }

    public Collection<MetaBinders.ReviewsBinder> getReviewsBinders() {
        return mReviewsBinders;
    }

    public Collection<MetaBinders.AuthorsBinder> getAuthorsBinders() {
        return mAuthorsBinders;
    }

    public Collection<MetaBinders.SubjectsBinder> getSubjectsBinders() {
        return mSubjectsBinders;
    }

    public Collection<MetaBinders.DatesBinder> getDatesBinders() {
        return mDatesBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumReviewsBinders() {
        return mNumReviewsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumAuthorsBinders() {
        return mNumAuthorsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumSubjectsBinders() {
        return mNumSubjectsBinders;
    }

    public Collection<ReferenceBinders.SizeBinder> getNumDatesBinders() {
        return mNumDatesBinders;
    }

    public void bind(final MetaBinders.ReviewsBinder binder) {
        if(!mReviewsBinders.contains(binder)) mReviewsBinders.add(binder);
    }

    public void bind(final MetaBinders.AuthorsBinder binder) {
        if(!mAuthorsBinders.contains(binder)) mAuthorsBinders.add(binder);
    }

    public void bind(final MetaBinders.SubjectsBinder binder) {
        if(!mSubjectsBinders.contains(binder)) mSubjectsBinders.add(binder);
    }

    public void bind(final MetaBinders.DatesBinder binder) {
        if(!mDatesBinders.contains(binder)) mDatesBinders.add(binder);
    }

    public void bindToReviews(final ReferenceBinders.SizeBinder binder) {
        if(!mNumReviewsBinders.contains(binder)) mNumReviewsBinders.add(binder);
    }

    public void bindToAuthors(final ReferenceBinders.SizeBinder binder) {
        if(!mNumAuthorsBinders.contains(binder)) mNumAuthorsBinders.add(binder);
    }

    public void bindToSubjects(final ReferenceBinders.SizeBinder binder) {
        if(!mNumSubjectsBinders.contains(binder)) mNumSubjectsBinders.add(binder);
    }

    public void bindToDates(final ReferenceBinders.SizeBinder binder) {
        if(!mNumDatesBinders.contains(binder)) mNumDatesBinders.add(binder);
    }

    public void unbind(MetaBinders.ReviewsBinder binder) {
        if(mReviewsBinders.contains(binder)) mReviewsBinders.remove(binder);
    }

    public void unbind(MetaBinders.AuthorsBinder binder) {
        if(mAuthorsBinders.contains(binder)) mAuthorsBinders.remove(binder);
    }

    public void unbind(MetaBinders.SubjectsBinder binder) {
        if(mSubjectsBinders.contains(binder)) mSubjectsBinders.remove(binder);
    }

    public void unbind(MetaBinders.DatesBinder binder) {
        if(mDatesBinders.contains(binder)) mDatesBinders.remove(binder);
    }

    public void unbindFromReviews(ReferenceBinders.SizeBinder binder) {
        if(mNumReviewsBinders.contains(binder)) mNumReviewsBinders.remove(binder);
    }

    public void unbindFromAuthors(ReferenceBinders.SizeBinder binder) {
        if(mNumSubjectsBinders.contains(binder)) mNumSubjectsBinders.remove(binder);
    }

    public void unbindFromSubjects(ReferenceBinders.SizeBinder binder) {
        if(mNumAuthorsBinders.contains(binder)) mNumAuthorsBinders.remove(binder);
    }

    public void unbindFromDates(ReferenceBinders.SizeBinder binder) {
        if(mNumDatesBinders.contains(binder)) mNumDatesBinders.remove(binder);
    }
}
