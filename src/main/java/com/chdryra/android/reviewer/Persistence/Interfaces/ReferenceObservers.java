/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

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

/**
 * Created by: Rizwan Choudrey
 * On: 16/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReferenceObservers {
    interface SubjectObserver extends ValueObserver<DataSubject> {
    }

    interface RatingObserver extends ValueObserver<DataRating> {
    }

    interface AuthorObserver extends ValueObserver<DataAuthorReview> {
    }

    interface DateObserver extends ValueObserver<DataDateReview> {
    }

    interface CoverObserver extends ValueObserver<DataImage> {
    }

    interface CriteriaObserver extends ValueObserver<IdableList<? extends DataCriterion>> {
    }

    interface CommentsObserver extends ValueObserver<IdableList<? extends DataComment>> {
    }

    interface FactsObserver extends ValueObserver<IdableList<? extends DataFact>> {
    }

    interface LocationsObserver extends ValueObserver<IdableList<? extends DataLocation>> {
    }

    interface ImagesObserver extends ValueObserver<IdableList<? extends DataImage>> {
    }

    interface TagsObserver extends ValueObserver<IdableList<? extends DataTag>> {
    }
}
