package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
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
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DataAggregatorImpl;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceDate;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceFloat;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferencePercentage;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataAggregator {
    private static final DifferenceBoolean SAME_BOOL = new DifferenceBoolean(false);
    private static final DifferencePercentage SAME_PCNT = new DifferencePercentage(0);
    private static final DifferenceDate SAME_DAY = new DifferenceDate(DifferenceDate.DateBucket
            .DAY);
    private static final DifferenceFloat TEN_METRES = new DifferenceFloat(10f);
    private static final DifferenceLocation SAME_LOC = new DifferenceLocation(TEN_METRES,
            SAME_PCNT);

    public DataAggregator<DataAuthorReview> newAuthorsAggregator() {

        return new DataAggregatorImpl<>(new ComparitorAuthor(), SAME_BOOL, new CanonicalAuthor(new ComparitorAuthor()));
    }

    public DataAggregator<DataSubject> newSubjectsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorSubject(), SAME_PCNT, new CanonicalSubjectMode());

    }

    public DataAggregator<DataTag> newTagsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorTag(), SAME_PCNT, new CanonicalTagMode());
    }

    public DataAggregator<DataComment> newCommentsAggregator() {

        return new DataAggregatorImpl<>(new ComparitorComment(), SAME_PCNT, new CanonicalCommentMode());
    }

    public DataAggregator<DataDateReview> newDatesAggregator() {
        return new DataAggregatorImpl<>(new ComparitorDate(), SAME_DAY, new CanonicalDate());
    }

    public DataAggregator<DataImage> newImagesAggregator() {
        return new DataAggregatorImpl<>(new ComparitorImageBitmap(), SAME_BOOL, new CanonicalImage());
    }

    public DataAggregator<DataLocation> newLocationsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorLocation(new ComparitorLocationDistance(),
                new ComparitorLocationName()), SAME_LOC, new CanonicalLocation());
    }

    public DataAggregator<DataCriterion> newCriteriaAggregatorSubjectRating() {
        return new DataAggregatorImpl<>(new ComparitorCriterion(), SAME_BOOL, new CanonicalCriterionMode());
    }

    public DataAggregator<DataCriterion> newCriteriaAggregatorSubject() {
        return new DataAggregatorImpl<>(new ComparitorCriterionSubject(), SAME_PCNT, new CanonicalCriterionAverage());
    }

    public DataAggregator<DataFact> newFactsAggregator() {
        return new DataAggregatorImpl<>(new ComparitorFactLabel(), SAME_PCNT, new CanonicalFact());
    }
}
