/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


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
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewReference implements ReviewReference {

    public NullReviewReference() {
    }

    @Override
    public ReviewListEntry getBasicInfo() {
        return new ReviewListEntry();
    }

    @Override
    public void registerSubjectObserver(ValueObserver<DataSubject> observer) {

    }

    @Override
    public void registerRatingObserver(ValueObserver<DataRating> observer) {

    }

    @Override
    public void registerAuthorObserver(ValueObserver<DataAuthorReview> observer) {

    }

    @Override
    public void registerPublishDateObserver(ValueObserver<DataDateReview> observer) {

    }

    @Override
    public void registerCriteriaObserver(ValueObserver<IdableList<? extends DataCriterion>>
                                                     observer) {

    }

    @Override
    public void registerCommentsObserver(ValueObserver<IdableList<? extends DataComment>> observer) {

    }

    @Override
    public void registerFactsObserver(ValueObserver<IdableList<? extends DataFact>> observer) {

    }

    @Override
    public void registerImagesObserver(ValueObserver<IdableList<? extends DataImage>> observer) {

    }

    @Override
    public void registerCoverObserver(ValueObserver<DataImage> observer) {

    }

    @Override
    public void registerLocationsObserver(ValueObserver<IdableList<? extends DataLocation>>
                                                      observer) {

    }

    @Override
    public void registerTagsObserver(ValueObserver<List<String>> observer) {

    }

    @Override
    public void unregisterSubjectObserver(ValueObserver<DataSubject> observer) {

    }

    @Override
    public void unregisterRatingObserver(ValueObserver<DataRating> observer) {

    }

    @Override
    public void unregisterAuthorObserver(ValueObserver<DataAuthorReview> observer) {

    }

    @Override
    public void unregisterPublishDateObserver(ValueObserver<DataDateReview> observer) {

    }

    @Override
    public void unregisterCoverObserver(ValueObserver<DataImage> observer) {

    }

    @Override
    public void unregisterCriteriaObserver(ValueObserver<IdableList<? extends DataCriterion>> observer) {

    }

    @Override
    public void unregisterCommentsObserver(ValueObserver<IdableList<? extends DataComment>>
                                                       observer) {

    }

    @Override
    public void unregisterFactsObserver(ValueObserver<IdableList<? extends DataFact>> observer) {

    }

    @Override
    public void unregisterImagesObserver(ValueObserver<IdableList<? extends DataImage>> observer) {

    }

    @Override
    public void unregisterLocationsObserver(ValueObserver<IdableList<? extends DataLocation>> observer) {

    }

    @Override
    public void unregisterTagsObserver(ValueObserver<List<String>> observer) {

    }

    @Override
    public void dereference(DereferenceCallback callback) {

    }
}
