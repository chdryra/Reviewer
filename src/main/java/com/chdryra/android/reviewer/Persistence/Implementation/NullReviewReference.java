/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;


import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewReference implements ReviewReference {

    public NullReviewReference() {
    }

    @Override
    public ReviewInfo getInfo() {
        return new ReviewInfo();
    }

    @Override
    public void bind(ReferenceBinders.SubjectBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.RatingBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.AuthorBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.DateBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.CriteriaBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.CommentsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.FactsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.ImagesBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.CoverBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.LocationsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.TagsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.NumCriteriaBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.NumCommentsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.NumFactsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.NumImagesBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.NumLocationsBinder binder) {

    }

    @Override
    public void bind(ReferenceBinders.NumTagsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.SubjectBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.RatingBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.AuthorBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.DateBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.CoverBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.CriteriaBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.CommentsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.FactsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.ImagesBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.LocationsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.TagsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.NumCriteriaBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.NumCommentsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.NumFactsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.NumImagesBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.NumLocationsBinder binder) {

    }

    @Override
    public void unbind(ReferenceBinders.NumTagsBinder binder) {

    }

    @Override
    public void dereference(DereferenceCallback callback) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
