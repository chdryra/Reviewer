/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface MetaReference extends ReviewReference {
    interface ReviewsCallback {
        void onReviews(IdableList<ReviewReference> reviews, CallbackMessage message);
    }

    interface SubjectsCallback {
        void onSubjects(IdableList<? extends DataSubject> subjects, CallbackMessage message);
    }

    interface AuthorsCallback {
        void onAuthors(IdableList<? extends DataAuthorId> Authors, CallbackMessage message);
    }

    interface DatesCallback {
        void onDates(IdableList<? extends DataDate> Dates, CallbackMessage message);
    }

    interface ReviewsSizeCallback {
        void onNumReviews(DataSize size, CallbackMessage message);
    }

    interface SubjectsSizeCallback {
        void onNumSubjects(DataSize size, CallbackMessage message);
    }

    interface AuthorsSizeCallback {
        void onNumAuthors(DataSize size, CallbackMessage message);
    }

    interface DatesSizeCallback {
        void onNumDates(DataSize size, CallbackMessage message);
    }

    void getData(ReviewsCallback callback);

    void getData(SubjectsCallback callback);

    void getData(AuthorsCallback callback);

    void getData(DatesCallback callback);


    void getSize(ReviewsSizeCallback callback);

    void getSize(SubjectsSizeCallback callback);

    void getSize(AuthorsSizeCallback callback);

    void getSize(DatesSizeCallback callback);


    void bind(MetaBinders.ReviewsBinder binder);

    void bind(MetaBinders.SubjectsBinder binder);

    void bind(MetaBinders.AuthorsBinder binder);

    void bind(MetaBinders.DatesBinder binder);


    void bindToReviews(MetaBinders.SizeBinder binder);

    void bindToSubjects(MetaBinders.SizeBinder binder);

    void bindToAuthors(MetaBinders.SizeBinder binder);

    void bindToDates(MetaBinders.SizeBinder binder);


    void unbind(MetaBinders.ReviewsBinder binder);

    void unbind(MetaBinders.SubjectsBinder binder);

    void unbind(MetaBinders.AuthorsBinder binder);

    void unbind(MetaBinders.DatesBinder binder);


    void unbindFromReviews(MetaBinders.SizeBinder binder);

    void unbindFromSubjects(MetaBinders.SizeBinder binder);

    void unbindFromAuthors(MetaBinders.SizeBinder binder);

    void unbindFromDates(MetaBinders.SizeBinder binder);
}
