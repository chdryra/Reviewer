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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation.ReviewListEntry;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference {
    interface ValueObserver<T> {
        void onValue(T value);
    }

    interface DereferenceCallback {
        void onDereferenced(@Nullable Review review, CallbackMessage message);
    }

    ReviewListEntry getBasicInfo();

    void registerSubjectObserver(ValueObserver<DataSubject> observer);

    void registerRatingObserver(ValueObserver<DataRating> observer);

    void registerAuthorObserver(ValueObserver<DataAuthorReview> observer);

    void registerPublishDateObserver(ValueObserver<DataDateReview> observer);

    void registerCriteriaObserver(ValueObserver<IdableList<? extends DataCriterion>> observer);

    void registerCommentsObserver(ValueObserver<IdableList<? extends DataComment>> observer);

    void registerFactsObserver(ValueObserver<IdableList<? extends DataFact>> observer);

    void registerImagesObserver(ValueObserver<IdableList<? extends DataImage>> observer);

    void registerCoverObserver(ValueObserver<DataImage> observer);

    void registerLocationsObserver(ValueObserver<IdableList<? extends DataLocation>> observer);

    void registerTagsObserver(ValueObserver<List<String>> observer);

    void unregisterSubjectObserver(ValueObserver<DataSubject> observer);

    void unregisterRatingObserver(ValueObserver<DataRating> observer);

    void unregisterAuthorObserver(ValueObserver<DataAuthorReview> observer);

    void unregisterPublishDateObserver(ValueObserver<DataDateReview> observer);

    void unregisterCoverObserver(ValueObserver<DataImage> observer);

    void unregisterCriteriaObserver(ValueObserver<IdableList<? extends DataCriterion>> observer);

    void unregisterCommentsObserver(ValueObserver<IdableList<? extends DataComment>> observer);

    void unregisterFactsObserver(ValueObserver<IdableList<? extends DataFact>> observer);

    void unregisterImagesObserver(ValueObserver<IdableList<? extends DataImage>> observer);

    void unregisterLocationsObserver(ValueObserver<IdableList<? extends DataLocation>> observer);

    void unregisterTagsObserver(ValueObserver<List<String>> observer);

    void dereference(DereferenceCallback callback);
}
