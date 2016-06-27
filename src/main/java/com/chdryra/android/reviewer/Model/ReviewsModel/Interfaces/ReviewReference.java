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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
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
        void onCovers(IdableList<? extends DataImage> covers, CallbackMessage message);    
    }

    interface TagsCallback {
        void onTags(IdableList<? extends DataTag> tags, CallbackMessage message);
    }

    interface CriteriaCallback {
        void onCriteria(IdableList<? extends DataCriterion> criteria, CallbackMessage message);
    }

    interface ImagesCallback {
        void onImages(IdableList<? extends DataImage> images, CallbackMessage message);
    }

    interface CommentsCallback {
        void onComments(IdableList<? extends DataComment> comments, CallbackMessage message);
    }

    interface LocationsCallback {
        void onLocations(IdableList<? extends DataLocation> locations, CallbackMessage message);
    }

    interface FactsCallback {
        void onFacts(IdableList<? extends DataFact> facts, CallbackMessage message);
    }

    interface TagsSizeCallback {
        void onNumTags(DataSize size, CallbackMessage message);
    }

    interface CriteriaSizeCallback {
        void onNumCriteria(DataSize size, CallbackMessage message);
    }

    interface ImagesSizeCallback {
        void onNumImages(DataSize size, CallbackMessage message);
    }

    interface CommentsSizeCallback {
        void onNumComments(DataSize size, CallbackMessage message);
    }

    interface LocationsSizeCallback {
        void onNumLocations(DataSize size, CallbackMessage message);
    }

    interface FactsSizeCallback {
        void onNumFacts(DataSize size, CallbackMessage message);
    }

    @Override
    ReviewId getReviewId();

    @Override
    DataSubject getSubject();

    @Override
    DataRating getRating();

    @Override
    DataAuthorReview getAuthor();

    @Override
    DataDateReview getPublishDate();

    ReviewNode asNode();

    void getData(CoversCallback callback);
    
    void getData(TagsCallback callback);
    
    void getData(CriteriaCallback callback);
    
    void getData(ImagesCallback callback);
    
    void getData(CommentsCallback callback);

    void getData(LocationsCallback callback);
    
    void getData(FactsCallback callback);


    void getSize(TagsSizeCallback callback);

    void getSize(CriteriaSizeCallback callback);

    void getSize(ImagesSizeCallback callback);

    void getSize(CommentsSizeCallback callback);

    void getSize(LocationsSizeCallback callback);

    void getSize(FactsSizeCallback callback);


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
