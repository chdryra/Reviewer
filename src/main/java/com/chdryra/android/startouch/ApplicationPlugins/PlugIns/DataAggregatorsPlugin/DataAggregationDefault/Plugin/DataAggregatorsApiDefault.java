/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Plugin;


import com.chdryra.android.corelibrary.Aggregation.ComparatorString;
import com.chdryra.android.corelibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.corelibrary.Aggregation.DifferenceDate;
import com.chdryra.android.corelibrary.Aggregation.DifferenceLocation;
import com.chdryra.android.corelibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsApi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalCommentMode;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalCriterionAverage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalCriterionMode;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalDate;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalImage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalSubjectMode;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalTagMode;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorComment;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorCriterion;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorCriterionSubject;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorDate;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorFactLabel;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorImageBitmap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLocationDistance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLocationName;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorSubject;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorTag;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.DataAggregatorImpl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorsApiDefault implements DataAggregatorsApi {
    private final ComparatorString mStringComparitor;

    public DataAggregatorsApiDefault(ComparatorString stringComparitor) {
        mStringComparitor = stringComparitor;
    }

    @Override
    public DataAggregator<DataAuthorName> newAuthorsAggregator(DifferenceBoolean threshold) {
        return new DataAggregatorImpl<>(new ComparatorAuthor(), threshold,
                new CanonicalAuthor(new ComparatorAuthor()));
    }

    @Override
    public DataAggregator<DataSubject> newSubjectsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparatorSubject(mStringComparitor), threshold,
                new CanonicalSubjectMode());

    }

    @Override
    public DataAggregator<DataTag> newTagsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparatorTag(mStringComparitor), threshold,
                new CanonicalTagMode());
    }

    @Override
    public DataAggregator<DataComment> newCommentsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparatorComment(mStringComparitor), threshold,
                new CanonicalCommentMode());
    }

    @Override
    public DataAggregator<DataDate> newDatesAggregator(DifferenceDate threshold) {
        return new DataAggregatorImpl<>(new ComparatorDate(), threshold, new CanonicalDate());
    }

    @Override
    public DataAggregator<DataImage> newImagesAggregator(DifferenceBoolean threshold) {
        return new DataAggregatorImpl<>(new ComparatorImageBitmap(), threshold,
                new CanonicalImage());
    }

    @Override
    public DataAggregator<DataLocation> newLocationsAggregator(DifferenceLocation threshold) {
        ComparatorLocation comparitorLocation
                = new ComparatorLocation(new ComparatorLocationDistance(),
                new ComparatorLocationName(mStringComparitor));
        return new DataAggregatorImpl<>(comparitorLocation, threshold, new CanonicalLocation());
    }

    @Override
    public DataAggregator<DataCriterion> newCriteriaAggregatorSameSubjectRating(DifferenceBoolean
                                                                                        threshold) {
        return new DataAggregatorImpl<>(new ComparatorCriterion(), threshold,
                new CanonicalCriterionMode());
    }

    @Override
    public DataAggregator<DataCriterion> newCriteriaAggregatorSameSubject(DifferencePercentage
                                                                                      threshold) {
        return new DataAggregatorImpl<>(new ComparatorCriterionSubject(mStringComparitor),
                threshold, new CanonicalCriterionAverage());
    }

    @Override
    public DataAggregator<DataFact> newFactsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparatorFactLabel(mStringComparitor), threshold,
                new CanonicalFact());
    }
}
