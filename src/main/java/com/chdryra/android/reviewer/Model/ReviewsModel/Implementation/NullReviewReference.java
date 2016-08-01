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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 01/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullReviewReference extends NullDataReference<Review> implements ReviewReference {
    @Override
    public ReviewNode asNode() {
        return null;
    }

    @Override
    public DataReference<DataImage> getCover() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<IdableList<DataCriterion>> getCriteria() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<IdableList<DataComment>> getComments() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<IdableList<DataFact>> getFacts() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<IdableList<DataImage>> getImages() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<IdableList<DataLocation>> getLocations() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<IdableList<DataTag>> getTags() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<DataSize> getCriteriaSize() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<DataSize> getCommentsSize() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<DataSize> getFactsSize() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<DataSize> getImagesSize() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<DataSize> getLocationsSize() {
        return new NullDataReference<>();
    }

    @Override
    public DataReference<DataSize> getTagsSize() {
        return new NullDataReference<>();
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
