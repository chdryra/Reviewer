package test.DataAlgorithms.Sorting;

import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Factories.DataComparatorsFactory;
import com.chdryra.android.reviewer.DataAlgorithms.DataSorting.Interfaces.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Comparator;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CriterionComparatorsTest extends ComparatorCollectionImplTest<DataCriterion> {

    @Override
    protected ComparatorCollection<DataCriterion> getComparators(DataComparatorsFactory factory) {
        return factory.getCriterionComparators();
    }

    @Test
    public void defaultComparatorIsSubjectAlphabeticalAscendingFirst_DescendingRatingSecond() {
        Comparator<DataCriterion> comparator = getDefaultComparator();

        DataCriterion reviewA2 = new DatumCriterion(RandomReviewId.nextReviewId(), "A", 2f);
        DataCriterion reviewA1 = new DatumCriterion(RandomReviewId.nextReviewId(), "A", 1f);
        DataCriterion reviewb5 = new DatumCriterion(RandomReviewId.nextReviewId(), "b", 5f);
        DataCriterion reviewB2 = new DatumCriterion(RandomReviewId.nextReviewId(), "B", 2f);
        DataCriterion reviewc1 = new DatumCriterion(RandomReviewId.nextReviewId(), "c", 1f);

        ComparatorTester<DataCriterion> tester = newComparatorTester(comparator);
        tester.testFirstSecond(reviewA2, reviewA1);
        tester.testFirstSecond(reviewA1, reviewb5);
        tester.testFirstSecond(reviewb5, reviewB2);
        tester.testFirstSecond(reviewB2, reviewc1);
    }

    @Test
    public void defaultComparatorIsSubjectAlphabeticalAscendingDifferentFirstLetter_SameRating() {
        Comparator<DataCriterion> comparator = getDefaultComparator();

        float rating = RandomRating.nextRating();
        DataCriterion reviewA = new DatumCriterion(RandomReviewId.nextReviewId(), "A" + RandomString.nextWord(), rating);
        DataCriterion reviewB = new DatumCriterion(RandomReviewId.nextReviewId(), "B" + RandomString.nextWord(), rating);
        DataCriterion reviewC = new DatumCriterion(RandomReviewId.nextReviewId(), "C" + RandomString.nextWord(), rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester(comparator);
        tester.testFirstSecond(reviewA, reviewB);
        tester.testFirstSecond(reviewB, reviewC);
        tester.testFirstSecond(reviewA, reviewC);
    }

    @Test
    public void defaultComparatorIsRatingDescending_SameSubject() {
        Comparator<DataCriterion> comparator = getDefaultComparator();

        String subject = RandomString.nextWord();
        DataCriterion review1 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, 1f);
        DataCriterion review2 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, 2f);
        DataCriterion review3 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, 3f);

        ComparatorTester<DataCriterion> tester = newComparatorTester(comparator);
        tester.testFirstSecond(review2, review1);
        tester.testFirstSecond(review3, review2);
        tester.testFirstSecond(review3, review1);
    }

    @Test
    public void defaultComparatorIsAlphabeticalAscendingStartingSubjectStemSame_SameRating() {
        Comparator<DataCriterion> comparator = getDefaultComparator();
        String subject1 = RandomString.nextWord();
        String subject2 = subject1 + RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion review1 = new DatumCriterion(RandomReviewId.nextReviewId(), subject1, rating);
        DataCriterion review2 = new DatumCriterion(RandomReviewId.nextReviewId(), subject2, rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester(comparator);
        if(subject1.compareToIgnoreCase(subject2) < 0) {
            tester.testFirstSecond(review1, review2);
        } else if(subject1.compareToIgnoreCase(subject2) > 0){
            tester.testFirstSecond(review2, review1);
        } else {
            tester.testEquals(review1, review2);
        }
    }

    @Test
    public void defaultComparatorEqualitySameSubject_SameRating() {
        String subject  = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion criteiron1 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);
        DataCriterion criterion2 = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester(getDefaultComparator());
        tester.testEquals(criteiron1, criteiron1);
        tester.testEquals(criteiron1, criterion2);
    }

    @Test
    public void defaultComparatorEqualityIgnoresSubjectCase_SameRating() {
        String subject  = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        DataCriterion review = new DatumCriterion(RandomReviewId.nextReviewId(), subject, rating);
        DataCriterion reviewLower = new DatumCriterion(RandomReviewId.nextReviewId(), subject.toLowerCase(), rating);
        DataCriterion reviewUpper = new DatumCriterion(RandomReviewId.nextReviewId(), subject.toUpperCase(), rating);

        ComparatorTester<DataCriterion> tester = newComparatorTester(getDefaultComparator());
        tester.testEquals(review, reviewLower);
        tester.testEquals(review, reviewUpper);
    }
}
