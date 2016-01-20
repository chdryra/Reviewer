package com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault;


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
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.Api.DataAggregationPlugin;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalAuthor;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalCommentMode;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalCriterionAverage;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalCriterionMode;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalDate;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalFact;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalImage;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalLocation;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalSubjectMode;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.CanonicalTagMode;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorAuthor;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorComment;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorCriterion;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorCriterionSubject;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorDate;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorFactLabel;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorImageBitmap;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorLocation;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorLocationDistance;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorLocationName;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorSubject;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorTag;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.DataAggregatorImpl;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Interfaces.ComparitorString;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregationPluginDefault implements DataAggregationPlugin {
    private final ComparitorString mStringComparitor;
    
    public DataAggregationPluginDefault(ComparitorString stringComparitor) {
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
