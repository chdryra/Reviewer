/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api;

import com.chdryra.android.mygenerallibrary.Comparators.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 22/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataComparatorsApi {
    ComparatorCollection<NamedAuthor> newAuthorComparators();

    ComparatorCollection<DataComment> newCommentComparators();

    ComparatorCollection<DataCriterion> newCriterionComparators();

    ComparatorCollection<DateTime> newDateTimeComparators();

    ComparatorCollection<DataFact> newFactComparators();

    ComparatorCollection<DataImage> newImageComparators();

    ComparatorCollection<DataLocation> newLocationComparators();

    ComparatorCollection<DataReviewInfo> newReviewComparators();

    ComparatorCollection<DataSocialPlatform> newSocialPlatformComparators();

    ComparatorCollection<DataSubject> newSubjectComparators();

    ComparatorCollection<DataTag> newTagComparators();

    ComparatorCollection<DataUrl> newUrlComparators();
}
