/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
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
        return new NullIdableList<>();
    }

    @Override
    public RefCommentList getComments() {
        return new NullDataReference.CommentList();
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return new NullIdableList<>();
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return new NullIdableList<>();
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return new NullIdableList<>();
    }

    @Override
    public RefDataList<DataTag> getTags() {
        return new NullIdableList<>();
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
