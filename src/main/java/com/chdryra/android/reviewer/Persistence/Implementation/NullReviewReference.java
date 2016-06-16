/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;


import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceObservers;
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
    public void registerObserver(ReferenceObservers.SubjectObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.RatingObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.AuthorObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.DateObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.CriteriaObserver
                                                     observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.CommentsObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.FactsObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.ImagesObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.CoverObserver observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.LocationsObserver
                                                      observer) {

    }

    @Override
    public void registerObserver(ReferenceObservers.TagsObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.SubjectObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.RatingObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.AuthorObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.DateObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.CoverObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.CriteriaObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.CommentsObserver
                                                       observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.FactsObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.ImagesObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.LocationsObserver observer) {

    }

    @Override
    public void unregisterObserver(ReferenceObservers.TagsObserver observer) {

    }

    @Override
    public void dereference(DereferenceCallback callback) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
