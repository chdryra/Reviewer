/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 22/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataComparatorsApi {
    ComparatorCollection<DataAuthor> getAuthorComparators();

    ComparatorCollection<DataComment> getCommentComparators();

    ComparatorCollection<DataCriterion> getCriterionComparators();

    ComparatorCollection<DataDate> getDateComparators();

    ComparatorCollection<DataFact> getFactComparators();

    ComparatorCollection<DataImage> getImageComparators();

    ComparatorCollection<DataLocation> getLocationComparators();

    ComparatorCollection<DataReviewSummary> getReviewComparators();

    ComparatorCollection<DataSocialPlatform> getSocialPlatformComparators();

    ComparatorCollection<DataSubject> getSubjectComparators();

    ComparatorCollection<DataTag> getTagComparators();

    ComparatorCollection<DataUrl> getUrlComparators();
}
