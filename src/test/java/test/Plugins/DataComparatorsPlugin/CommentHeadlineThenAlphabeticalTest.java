/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.CommentHeadlineThenAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.Random;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentHeadlineThenAlphabeticalTest extends ComparatorTest<DataComment>{
    private static final Random RAND = new Random();
    
    public CommentHeadlineThenAlphabeticalTest() {
        super(new CommentHeadlineThenAlphabetical());
    }

    @Test
    public void isHeadlineFirstThenAlphabeticalAscending() {
        DataComment commentD_true = new DatumComment(RandomReviewId.nextReviewId(), "D", true);
        DataComment commente_true = new DatumComment(RandomReviewId.nextReviewId(), "e", true);
        DataComment commentF_true = new DatumComment(RandomReviewId.nextReviewId(), "F", true);
        DataComment commenta_false = new DatumComment(RandomReviewId.nextReviewId(), "a", false);
        DataComment commentB_false = new DatumComment(RandomReviewId.nextReviewId(), "B", false);
        DataComment commentc_false = new DatumComment(RandomReviewId.nextReviewId(), "c", false);

        ComparatorTester<DataComment> tester = newComparatorTester();
        tester.testFirstSecond(commentD_true, commente_true);
        tester.testFirstSecond(commente_true, commentF_true);
        tester.testFirstSecond(commentF_true, commenta_false);
        tester.testFirstSecond(commenta_false, commentB_false);
        tester.testFirstSecond(commentB_false, commentc_false);
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter_SameIsHeadline() {
        boolean isHeadline = RAND.nextBoolean();
        DataComment commentA = new DatumComment(RandomReviewId.nextReviewId(), "A" + RandomString.nextSentence(), isHeadline);
        DataComment commentB = new DatumComment(RandomReviewId.nextReviewId(), "B" + RandomString.nextSentence(), isHeadline);
        DataComment commentC = new DatumComment(RandomReviewId.nextReviewId(), "C" + RandomString.nextSentence(), isHeadline);

        ComparatorTester<DataComment> tester = newComparatorTester();
        tester.testFirstSecond(commentA, commentB);
        tester.testFirstSecond(commentB, commentC);
        tester.testFirstSecond(commentA, commentC);
    }

    @Test
    public void isHeadlineFirst_SameComment() {
        String commentString = RandomString.nextSentence();
        DataComment comment1 = new DatumComment(RandomReviewId.nextReviewId(), commentString, false);
        DataComment comment2 = new DatumComment(RandomReviewId.nextReviewId(), commentString, true);

        ComparatorTester<DataComment> tester = newComparatorTester();
        tester.testFirstSecond(comment2, comment1);
    }

    @Test
    public void alphabeticalAscendingStartingSubjectStemSame_SameIsHeadline() {
        String commentString1 = RandomString.nextSentence();
        String commentString2 = commentString1 + RandomString.nextSentence();
        boolean isHeadline = RAND.nextBoolean();
        DataComment comment1 = new DatumComment(RandomReviewId.nextReviewId(), commentString1, isHeadline);
        DataComment comment2 = new DatumComment(RandomReviewId.nextReviewId(), commentString2, isHeadline);

        ComparatorTester<DataComment> tester = newComparatorTester();
        if(commentString1.compareToIgnoreCase(commentString2) < 0) {
            tester.testFirstSecond(comment1, comment2);
        } else if(commentString1.compareToIgnoreCase(commentString2) > 0){
            tester.testFirstSecond(comment2, comment1);
        } else {
            tester.testEquals(comment1, comment2);
        }
    }

    @Test
    public void comparatorEqualitySameComment_SameIsHeadline() {
        String commentString  = RandomString.nextSentence();
        boolean isHeadline = RAND.nextBoolean();
        DataComment comment1 = new DatumComment(RandomReviewId.nextReviewId(), commentString, isHeadline);
        DataComment comment2 = new DatumComment(RandomReviewId.nextReviewId(), commentString, isHeadline);

        ComparatorTester<DataComment> tester = newComparatorTester();
        tester.testEquals(comment1, comment1);
        tester.testEquals(comment1, comment2);
    }

    @Test
    public void comparatorEqualityIgnoresSubjectCase_SameIsHeadline() {
        String commentString = RandomString.nextSentence();
        boolean isHeadline = RAND.nextBoolean();
        DataComment comment = new DatumComment(RandomReviewId.nextReviewId(), commentString, isHeadline);
        DataComment commentLower = new DatumComment(RandomReviewId.nextReviewId(), commentString.toLowerCase(), isHeadline);
        DataComment commentUpper = new DatumComment(RandomReviewId.nextReviewId(), commentString.toUpperCase(), isHeadline);

        ComparatorTester<DataComment> tester = newComparatorTester();
        tester.testEquals(comment, commentLower);
        tester.testEquals(comment, commentUpper);
    }
}
