/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference {

    interface DereferenceCallback {
        void onDereferenced(@Nullable Review review, CallbackMessage message);
    }

    ReviewInfo getInfo();

    void bind(ReferenceBinders.SubjectBinder binder);

    void bind(ReferenceBinders.RatingBinder binder);

    void bind(ReferenceBinders.AuthorBinder binder);

    void bind(ReferenceBinders.DateBinder binder);

    void bind(ReferenceBinders.CoverBinder binder);


    void bind(ReferenceBinders.CriteriaBinder binder);

    void bind(ReferenceBinders.CommentsBinder binder);

    void bind(ReferenceBinders.FactsBinder binder);

    void bind(ReferenceBinders.ImagesBinder binder);

    void bind(ReferenceBinders.LocationsBinder binder);

    void bind(ReferenceBinders.TagsBinder binder);


    void bind(ReferenceBinders.NumCriteriaBinder binder);

    void bind(ReferenceBinders.NumCommentsBinder binder);

    void bind(ReferenceBinders.NumFactsBinder binder);

    void bind(ReferenceBinders.NumImagesBinder binder);

    void bind(ReferenceBinders.NumLocationsBinder binder);

    void bind(ReferenceBinders.NumTagsBinder binder);


    void unbind(ReferenceBinders.SubjectBinder binder);

    void unbind(ReferenceBinders.RatingBinder binder);

    void unbind(ReferenceBinders.AuthorBinder binder);

    void unbind(ReferenceBinders.DateBinder binder);

    void unbind(ReferenceBinders.CoverBinder binder);

    
    void unbind(ReferenceBinders.CriteriaBinder binder);

    void unbind(ReferenceBinders.CommentsBinder binder);

    void unbind(ReferenceBinders.FactsBinder binder);

    void unbind(ReferenceBinders.ImagesBinder binder);

    void unbind(ReferenceBinders.LocationsBinder binder);

    void unbind(ReferenceBinders.TagsBinder binder);


    void unbind(ReferenceBinders.NumCriteriaBinder binder);

    void unbind(ReferenceBinders.NumCommentsBinder binder);

    void unbind(ReferenceBinders.NumFactsBinder binder);

    void unbind(ReferenceBinders.NumImagesBinder binder);

    void unbind(ReferenceBinders.NumLocationsBinder binder);

    void unbind(ReferenceBinders.NumTagsBinder binder);

    void dereference(DereferenceCallback callback);

    boolean isValid();
}
