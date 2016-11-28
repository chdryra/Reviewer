/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.CriterionComparator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriterionSubjectThenRatingTest extends ComparatorTest<DataCriterion> {

    public CriterionSubjectThenRatingTest() {
        super(new CriterionComparator());
    }

    @Test
    public void subjectAlphabeticalAscendingFirst_DescendingRatingSecond() {
        DataCriterion reviewA2 = new DatumCriterion(RandomReviewId.nextReviewId(), "A", 2f);
        DataCriterion reviewA1 = new DatumCriterion(RandomReviewId.nextReviewId(), "A", 1f);
        DataCriterion reviewb5 = new DatumCriterion(RandomReviewId.nextReviewId(), "b", 5f);
        DataCriterion reviewB2 = new DatumCriterion(RandomReviewId.nextReviewId(), "B", 2f);
        DataCriterion reviewc1 = new DatumCriterion(RandomReviewId.nextReviewId(), "c", 1f);

        ComparatorTester<DataCriterion> tester = newComparatorTester();
        tester.testFirstSecond(reviewA2, reviewA1);
        tester.testFirstSecond(reviewA1, reviewb5);
        tester.testFirstSecond(reviewb5, reviewB2);
        tester.testFirstSecond(reviewB2, reviewc1);
    }

    @Test
    public void subjectAlphabeticalAscendingDifferentFirstLetter_SameRating() {
        float rating = RandomRating.nextRating();
        DataCriterion reviewA = new DatumCriterion(RandomReviewId.nextReviewId(), "A" + RandomString.nextWord(), rating);
        DataCriterion reviewB = new DatumCriterion(RandomReviewId.nextReviewId(), "B" + RandomString.nextWord(), rating);
        DataCriterion reviewC = new DatumCriterion(RandomReviewId.nextReviewId(), "C" + RandomString.nextWord(), rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester();
        tester.testFirstSecond(reviewA, reviewB);
        tester.testFirstSecond(reviewB, reviewC);
        tester.testFirstSecond(reviewA, reviewC);
    }

    @Test
    public void ratingDescending_SameSubject() {
        String subject = RandomString.nextWord();
        DataCriterion review1 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, 1f);
        DataCriterion review2 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, 2f);
        DataCriterion review3 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, 3f);

        ComparatorTester<DataCriterion> tester = newComparatorTester();
        tester.testFirstSecond(review2, review1);
        tester.testFirstSecond(review3, review2);
        tester.testFirstSecond(review3, review1);
    }

    @Test
    public void subjectAlphabeticalAscendingStartingSubjectStemSame_SameRating() {
        String subject1 = RandomString.nextWord();
        String subject2 = subject1 + RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion review1 = new DatumCriterion(RandomReviewId.nextReviewId(), subject1, rating);
        DataCriterion review2 = new DatumCriterion(RandomReviewId.nextReviewId(), subject2, rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester();
        if(subject1.compareToIgnoreCase(subject2) < 0) {
            tester.testFirstSecond(review1, review2);
        } else if(subject1.compareToIgnoreCase(subject2) > 0){
            tester.testFirstSecond(review2, review1);
        } else {
            tester.testEquals(review1, review2);
        }
    }

    @Test
    public void comparatorEqualitySameSubject_SameRating() {
        String subject  = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion criterion1 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);
        DataCriterion criterion2 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester();
        tester.testEquals(criterion1, criterion1);
        tester.testEquals(criterion1, criterion2);
    }

    @Test
    public void comparatorEqualityIgnoresSubjectCase_SameRating() {
        String subject  = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion review = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);
        DataCriterion reviewLower = new DatumCriterion(RandomReviewId.nextReviewId(), subject.toLowerCase(), rating);
        DataCriterion reviewUpper = new DatumCriterion(RandomReviewId.nextReviewId(), subject.toUpperCase(), rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester();
        tester.testEquals(review, reviewLower);
        tester.testEquals(review, reviewUpper);
    }
}
