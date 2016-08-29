/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.NullDataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewReference extends NullDataReference<Review> implements ReviewReference {
    
    @Override
    public ReviewItemReference<DataImage> getCover() {
        return new NullDataReference.Item<>();
    }

    @Override
    public RefDataList<DataCriterion> getCriteria() {
        return new NullDataReference.List<>();
    }

    @Override
    public RefCommentList getComments() {
        return new NullDataReference.CommentList();
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return new NullDataReference.List<>();
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return new NullDataReference.List<>();
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return new NullDataReference.List<>();
    }

    @Override
    public RefDataList<DataTag> getTags() {
        return new NullDataReference.List<>();
    }

    
    @Override
    public DataSubject getSubject() {
        return new DatumSubject();
    }

    @Override
    public DataRating getRating() {
        return new DatumRating();
    }

    @Override
    public DataDate getPublishDate() {
        return new DatumDate();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return new DatumAuthorId();
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId();
    }
}
