/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.SubjectAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectAlphabeticalTest extends ComparatorTest<DataSubject>{
    public SubjectAlphabeticalTest() {
        super(new SubjectAlphabetical());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        DataSubject A = new DatumSubject(RandomReviewId.nextReviewId(), "A" + RandomString.nextWord());
        DataSubject B = new DatumSubject(RandomReviewId.nextReviewId(), "B" + RandomString.nextWord());
        DataSubject C = new DatumSubject(RandomReviewId.nextReviewId(), "C" + RandomString.nextWord());

        ComparatorTester<DataSubject> tester = newComparatorTester();
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void alphabeticalAscendingStartingStemSame() {
        String string1 = RandomString.nextWord();
        String string2 = string1 + RandomString.nextWord();
        DataSubject subject1 = new DatumSubject(RandomReviewId.nextReviewId(), string1);
        DataSubject subject2 = new DatumSubject(RandomReviewId.nextReviewId(), string2);

        ComparatorTester<DataSubject> tester = newComparatorTester();
        if(string1.compareToIgnoreCase(string2) < 0) {
            tester.testFirstSecond(subject1, subject2);
        } else if(string1.compareToIgnoreCase(string2) > 0){
            tester.testFirstSecond(subject2, subject1);
        } else {
            tester.testEquals(subject2, subject1);
        }
    }

    @Test
    public void comparatorEquality() {
        String string = RandomString.nextWord();
        DataSubject subject1 = new DatumSubject(RandomReviewId.nextReviewId(), string);
        DataSubject subject2 = new DatumSubject(RandomReviewId.nextReviewId(), string);

        ComparatorTester<DataSubject> tester = newComparatorTester();
        tester.testEquals(subject1, subject1);
        tester.testEquals(subject1, subject2);
    }

    @Test
    public void comparatorEqualityIgnoresCase() {
        String string = RandomString.nextWord();
        DataSubject subject = new DatumSubject(RandomReviewId.nextReviewId(), string);
        DataSubject subjectLower = new DatumSubject(RandomReviewId.nextReviewId(), string.toLowerCase());
        DataSubject subjectUpper = new DatumSubject(RandomReviewId.nextReviewId(), string.toUpperCase());

        ComparatorTester<DataSubject> tester = newComparatorTester();
        tester.testEquals(subject, subjectLower);
        tester.testEquals(subject, subjectUpper);
    }
}
