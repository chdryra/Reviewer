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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewInfo;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference {
    interface ValueObserver<T> {
        void onValue(T value);
    }

    interface SubjectObserver extends ValueObserver<DataSubject> {}
    interface RatingObserver extends ValueObserver<DataRating> {}
    interface AuthorObserver extends ValueObserver<DataAuthorReview> {}
    interface DateObserver extends ValueObserver<DataDateReview> {}
    interface CoverObserver extends ValueObserver<DataImage> {}
    interface CriteriaObserver extends ValueObserver<IdableList<? extends DataCriterion>> {}
    interface CommentsObserver extends ValueObserver<IdableList<? extends DataComment>> {}
    interface FactsObserver extends ValueObserver<IdableList<? extends DataFact>> {}
    interface LocationsObserver extends ValueObserver<IdableList<? extends DataLocation>> {}
    interface ImagesObserver extends ValueObserver<IdableList<? extends DataImage>> {}
    interface TagsObserver extends ValueObserver<IdableList<? extends DataTag>> {}

    interface DereferenceCallback {
        void onDereferenced(@Nullable Review review, CallbackMessage message);
    }

    ReviewInfo getInfo();

    void registerObserver(SubjectObserver observer);

    void registerObserver(RatingObserver observer);

    void registerObserver(AuthorObserver observer);

    void registerObserver(DateObserver observer);

    void registerObserver(CoverObserver observer);

    void registerObserver(CriteriaObserver observer);

    void registerObserver(CommentsObserver observer);

    void registerObserver(FactsObserver observer);

    void registerObserver(ImagesObserver observer);

    void registerObserver(LocationsObserver observer);

    void registerObserver(TagsObserver observer);

    void unregisterObserver(SubjectObserver observer);

    void unregisterObserver(RatingObserver observer);

    void unregisterObserver(AuthorObserver observer);

    void unregisterObserver(DateObserver observer);

    void unregisterObserver(CoverObserver observer);

    void unregisterObserver(CriteriaObserver observer);

    void unregisterObserver(CommentsObserver observer);

    void unregisterObserver(FactsObserver observer);

    void unregisterObserver(ImagesObserver observer);

    void unregisterObserver(LocationsObserver observer);

    void unregisterObserver(TagsObserver observer);

    void dereference(DereferenceCallback callback);

    boolean isValid();
}
