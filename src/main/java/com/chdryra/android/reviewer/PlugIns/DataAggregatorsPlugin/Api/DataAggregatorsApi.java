/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceDate;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceLocation;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAggregatorsApi {
    DataAggregator<DataAuthorReview> newAuthorsAggregator(DifferenceBoolean threshold);

    DataAggregator<DataSubject> newSubjectsAggregator(DifferencePercentage threshold);

    DataAggregator<DataTag> newTagsAggregator(DifferencePercentage threshold);

    DataAggregator<DataComment> newCommentsAggregator(DifferencePercentage threshold);

    DataAggregator<DataDateReview> newDatesAggregator(DifferenceDate threshold);

    DataAggregator<DataImage> newImagesAggregator(DifferenceBoolean threshold);

    DataAggregator<DataLocation> newLocationsAggregator(DifferenceLocation threshold);

    DataAggregator<DataCriterion> newCriteriaAggregatorSameSubjectRating(DifferenceBoolean
                                                                                 threshold);

    DataAggregator<DataCriterion> newCriteriaAggregatorSameSubject(DifferencePercentage threshold);

    DataAggregator<DataFact> newFactsAggregator(DifferencePercentage threshold);
}