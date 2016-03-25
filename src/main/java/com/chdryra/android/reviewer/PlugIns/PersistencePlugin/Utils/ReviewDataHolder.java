/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataHolder {
    ReviewId getReviewId();

    DataAuthor getAuthor();

    DataDate getPublishDate();

    String getSubject();

    float getRating();

    int getRatingWeight();

    Iterable<? extends DataComment> getComments();

    Iterable<? extends DataImage> getImages();

    Iterable<? extends DataFact> getFacts();

    Iterable<? extends DataLocation> getLocations();

    Iterable<? extends DataCriterion> getCriteria();

    Iterable<String> getTags();

    boolean isAverage();

    boolean isValid(DataValidator validator);
}
