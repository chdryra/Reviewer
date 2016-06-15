/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;


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
    public void registerObserver(SubjectObserver observer) {

    }

    @Override
    public void registerObserver(RatingObserver observer) {

    }

    @Override
    public void registerObserver(AuthorObserver observer) {

    }

    @Override
    public void registerObserver(DateObserver observer) {

    }

    @Override
    public void registerObserver(CriteriaObserver
                                                     observer) {

    }

    @Override
    public void registerObserver(CommentsObserver observer) {

    }

    @Override
    public void registerObserver(FactsObserver observer) {

    }

    @Override
    public void registerObserver(ImagesObserver observer) {

    }

    @Override
    public void registerObserver(CoverObserver observer) {

    }

    @Override
    public void registerObserver(LocationsObserver
                                                      observer) {

    }

    @Override
    public void registerObserver(TagsObserver observer) {

    }

    @Override
    public void unregisterObserver(SubjectObserver observer) {

    }

    @Override
    public void unregisterObserver(RatingObserver observer) {

    }

    @Override
    public void unregisterObserver(AuthorObserver observer) {

    }

    @Override
    public void unregisterObserver(DateObserver observer) {

    }

    @Override
    public void unregisterObserver(CoverObserver observer) {

    }

    @Override
    public void unregisterObserver(CriteriaObserver observer) {

    }

    @Override
    public void unregisterObserver(CommentsObserver
                                                       observer) {

    }

    @Override
    public void unregisterObserver(FactsObserver observer) {

    }

    @Override
    public void unregisterObserver(ImagesObserver observer) {

    }

    @Override
    public void unregisterObserver(LocationsObserver observer) {

    }

    @Override
    public void unregisterObserver(TagsObserver observer) {

    }

    @Override
    public void dereference(DereferenceCallback callback) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
