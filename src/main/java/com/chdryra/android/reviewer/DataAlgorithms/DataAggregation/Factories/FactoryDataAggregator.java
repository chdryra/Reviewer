package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalAuthor;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalCommentMode;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalCriterionAverage;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalCriterionMode;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalDate;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalFact;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalImage;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalSubjectMode;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalTagMode;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorAuthor;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorComment;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorCriterion;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorCriterionSubject;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorDate;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorFactLabel;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorImageBitmap;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorLocationDistance;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorLocationName;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorSubject;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.ComparitorTag;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DataAggregatorImpl;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregatorParams;
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
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataAggregator {
    private final DataAggregatorParams mParams;

    public FactoryDataAggregator(DataAggregatorParams params) {
        mParams = params;
    }

    public DataAggregator<DataAuthorReview> newAuthorsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorAuthor(), mParams.getSimilarBoolean(),
                new CanonicalAuthor(new ComparitorAuthor()));
    }

    public DataAggregator<DataSubject> newSubjectsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorSubject(mParams.getStringComparitor()),
                mParams.getSimilarPercentage(), new CanonicalSubjectMode());

    }

    public DataAggregator<DataTag> newTagsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorTag(mParams.getStringComparitor()),
                mParams.getSimilarPercentage(), new CanonicalTagMode());
    }

    public DataAggregator<DataComment> newCommentsAggregator() {

        return new DataAggregatorImpl<>(new ComparitorComment(mParams.getStringComparitor()),
                mParams.getSimilarPercentage(), new CanonicalCommentMode());
    }

    public DataAggregator<DataDateReview> newDatesAggregator() {
        return new DataAggregatorImpl<>(new ComparitorDate(), mParams.getSimilarDate(),
                new CanonicalDate());
    }

    public DataAggregator<DataImage> newImagesAggregator() {
        return new DataAggregatorImpl<>(new ComparitorImageBitmap(), mParams.getSimilarBoolean(),
                new CanonicalImage());
    }

    public DataAggregator<DataLocation> newLocationsAggregator() {
        ComparitorLocation comparitorLocation
                = new ComparitorLocation(new ComparitorLocationDistance(),
                new ComparitorLocationName(mParams.getStringComparitor()));
        return new DataAggregatorImpl<>(comparitorLocation,
                mParams.getSimilarLocation(), new CanonicalLocation());
    }

    public DataAggregator<DataCriterion> newCriteriaAggregatorSubjectRating() {
        return new DataAggregatorImpl<>(new ComparitorCriterion(), mParams.getSimilarBoolean(),
                new CanonicalCriterionMode());
    }

    public DataAggregator<DataCriterion> newCriteriaAggregatorSubject() {
        return new DataAggregatorImpl<>(new ComparitorCriterionSubject(mParams.getStringComparitor()),
                mParams.getSimilarPercentage(), new CanonicalCriterionAverage());
    }

    public DataAggregator<DataFact> newFactsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorFactLabel(mParams.getStringComparitor()),
                mParams.getSimilarPercentage(), new CanonicalFact());
    }
}
