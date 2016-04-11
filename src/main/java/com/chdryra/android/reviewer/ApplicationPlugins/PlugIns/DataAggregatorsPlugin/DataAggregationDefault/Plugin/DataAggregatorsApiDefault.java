/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin;


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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalCommentMode;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalCriterionAverage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.CanonicalCriterionMode;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalDate;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalSubjectMode;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.CanonicalTagMode;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorCriterionSubject;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorDate;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorFactLabel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.ComparitorImageBitmap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.ComparitorLocationDistance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorLocationName;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorSubject;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.ComparitorTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.DataAggregatorImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Interfaces.ComparitorString;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorsApiDefault implements DataAggregatorsApi {
    private final ComparitorString mStringComparitor;
    
    public DataAggregatorsApiDefault(ComparitorString stringComparitor) {
        mStringComparitor = stringComparitor;
    }

    @Override
    public DataAggregator<DataAuthorReview> newAuthorsAggregator(DifferenceBoolean threshold) {
        return new DataAggregatorImpl<>(new ComparitorAuthor(), threshold,
                new CanonicalAuthor(new ComparitorAuthor()));
    }

    @Override
    public DataAggregator<DataSubject> newSubjectsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparitorSubject(mStringComparitor), threshold,
                new CanonicalSubjectMode());

    }

    @Override
    public DataAggregator<DataTag> newTagsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparitorTag(mStringComparitor), threshold,
                new CanonicalTagMode());
    }

    @Override
    public DataAggregator<DataComment> newCommentsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparitorComment(mStringComparitor), threshold,
                new CanonicalCommentMode());
    }

    @Override
    public DataAggregator<DataDateReview> newDatesAggregator(DifferenceDate threshold) {
        return new DataAggregatorImpl<>(new ComparitorDate(), threshold, new CanonicalDate());
    }

    @Override
    public DataAggregator<DataImage> newImagesAggregator(DifferenceBoolean threshold) {
        return new DataAggregatorImpl<>(new ComparitorImageBitmap(), threshold,
                new CanonicalImage());
    }

    @Override
    public DataAggregator<DataLocation> newLocationsAggregator(DifferenceLocation threshold) {
        ComparitorLocation comparitorLocation
                = new ComparitorLocation(new ComparitorLocationDistance(),
                new ComparitorLocationName(mStringComparitor));
        return new DataAggregatorImpl<>(comparitorLocation, threshold, new CanonicalLocation());
    }

    @Override
    public DataAggregator<DataCriterion> newCriteriaAggregatorSameSubjectRating(DifferenceBoolean
                                                                                        threshold) {
        return new DataAggregatorImpl<>(new ComparitorCriterion(), threshold,
                new CanonicalCriterionMode());
    }

    @Override
    public DataAggregator<DataCriterion> newCriteriaAggregatorSameSubject(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparitorCriterionSubject(mStringComparitor),
                threshold, new CanonicalCriterionAverage());
    }

    @Override
    public DataAggregator<DataFact> newFactsAggregator(DifferencePercentage threshold) {
        return new DataAggregatorImpl<>(new ComparitorFactLabel(mStringComparitor), threshold,
                new CanonicalFact());
    }
}
