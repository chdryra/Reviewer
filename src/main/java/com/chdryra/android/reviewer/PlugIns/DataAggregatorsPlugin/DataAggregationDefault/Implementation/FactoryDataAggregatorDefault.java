package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;


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
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.FactoryDataAggregator;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Interfaces.ComparitorString;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataAggregatorDefault implements FactoryDataAggregator {
    private final ComparitorString mStringComparitor;
    
    public FactoryDataAggregatorDefault(ComparitorString stringComparitor) {
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
