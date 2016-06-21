/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces;


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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 13/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewReference extends DataReviewInfo{
    interface DereferenceCallback {
        void onDereferenced(@Nullable Review review, CallbackMessage message);
    }

    interface CoversCallback {
        void onCovers(IdableList<DataImage> covers, CallbackMessage message);    
    }

    interface TagsCallback {
        void onTags(IdableList<DataTag> tags, CallbackMessage message);
    }

    interface CriteriaCallback {
        void onCriteria(IdableList<DataCriterion> criteria, CallbackMessage message);
    }

    interface ImagesCallback {
        void onImages(IdableList<DataImage> images, CallbackMessage message);
    }

    interface CommentsCallback {
        void onComments(IdableList<DataComment> comments, CallbackMessage message);
    }

    interface LocationsCallback {
        void onLocations(IdableList<DataLocation> locations, CallbackMessage message);
    }

    interface FactsCallback {
        void onFacts(IdableList<DataFact> facts, CallbackMessage message);
    }

    interface TagsSizeCallback {
        void onNumTags(ReviewId id, int num, CallbackMessage message);
    }

    interface CriteriaSizeCallback {
        void onNumCriteria(ReviewId id, int num, CallbackMessage message);
    }

    interface ImagesSizeCallback {
        void onNumImages(ReviewId id, int num, CallbackMessage message);
    }

    interface CommentsSizeCallback {
        void onNumComments(ReviewId id, int num, CallbackMessage message);
    }

    interface LocationsSizeCallback {
        void onNumLocations(ReviewId id, int num, CallbackMessage message);
    }

    interface FactsSizeCallback {
        void onNumFacts(ReviewId id, int num, CallbackMessage message);
    }

    
    @Override
    DataSubject getSubject();

    @Override
    DataRating getRating();

    @Override
    DataDateReview getPublishDate();

    @Override
    DataAuthorReview getAuthor();

    @Override
    ReviewId getReviewId();

    ReviewNode asNode();

    void getCovers(CoversCallback callback);
    
    void getTags(TagsCallback callback);
    
    void getCriteria(CriteriaCallback callback);
    
    void getImages(ImagesCallback callback);
    
    void getComments(CommentsCallback callback);

    void getLocations(LocationsCallback callback);
    
    void getFacts(FactsCallback callback);

    void getNumTags(TagsSizeCallback callback);

    void getNumCriteria(CriteriaSizeCallback callback);

    void getNumImages(ImagesSizeCallback callback);

    void getNumComments(CommentsSizeCallback callback);

    void getNumLocations(LocationsSizeCallback callback);

    void getNumFacts(FactsSizeCallback callback);

    void bind(ReferenceBinders.CoversBinder binder);

    void bind(ReferenceBinders.TagsBinder binder);

    void bind(ReferenceBinders.CriteriaBinder binder);

    void bind(ReferenceBinders.ImagesBinder binder);

    void bind(ReferenceBinders.CommentsBinder binder);

    void bind(ReferenceBinders.LocationsBinder binder);

    void bind(ReferenceBinders.FactsBinder binder);
    

    void bindToTags(ReferenceBinders.SizeBinder binder);

    void bindToCriteria(ReferenceBinders.SizeBinder binder);

    void bindToImages(ReferenceBinders.SizeBinder binder);

    void bindToComments(ReferenceBinders.SizeBinder binder);

    void bindToLocations(ReferenceBinders.SizeBinder binder);

    void bindToFacts(ReferenceBinders.SizeBinder binder);
    
    
    void unbind(ReferenceBinders.CoversBinder binder);
    
    void unbind(ReferenceBinders.TagsBinder binder);

    void unbind(ReferenceBinders.CriteriaBinder binder);

    void unbind(ReferenceBinders.ImagesBinder binder);

    void unbind(ReferenceBinders.CommentsBinder binder);

    void unbind(ReferenceBinders.LocationsBinder binder);

    void unbind(ReferenceBinders.FactsBinder binder);


    void unbindFromTags(ReferenceBinders.SizeBinder binder);

    void unbindFromCriteria(ReferenceBinders.SizeBinder binder);

    void unbindFromImages(ReferenceBinders.SizeBinder binder);

    void unbindFromComments(ReferenceBinders.SizeBinder binder);

    void unbindFromLocations(ReferenceBinders.SizeBinder binder);

    void unbindFromFacts(ReferenceBinders.SizeBinder binder);

    
    void dereference(DereferenceCallback callback);

    boolean isValid();
}
