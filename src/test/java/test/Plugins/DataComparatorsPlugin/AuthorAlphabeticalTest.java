/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.AuthorComparator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorAlphabeticalTest extends ComparatorTest<NamedAuthor> {

    public AuthorAlphabeticalTest() {
        super(new AuthorComparator());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        NamedAuthor A = new DefaultNamedAuthor("A" + RandomString.nextWord(), AuthorIdGenerator.newId());
        NamedAuthor B = new DefaultNamedAuthor("B" + RandomString.nextWord(), AuthorIdGenerator.newId());
        NamedAuthor C = new DefaultNamedAuthor("C" + RandomString.nextWord(), AuthorIdGenerator.newId());

        ComparatorTester<NamedAuthor> tester = newComparatorTester();
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void alphabeticalAscendingStartingStemSame() {
        String name1 = RandomString.nextWord();
        String name2 = name1 + RandomString.nextWord();
        NamedAuthor author1 = new DefaultNamedAuthor(name1, AuthorIdGenerator.newId());
        NamedAuthor author2 = new DefaultNamedAuthor(name2, AuthorIdGenerator.newId());

        ComparatorTester<NamedAuthor> tester = newComparatorTester();
        if(name1.compareToIgnoreCase(name2) < 0) {
            tester.testFirstSecond(author1, author2);
        } else if(name1.compareToIgnoreCase(name2) > 0){
            tester.testFirstSecond(author2, author1);
        } else {
            tester.testEquals(author2, author1);
        }
    }

    @Test
    public void comparatorEquality() {
        String name = RandomString.nextWord();
        NamedAuthor author = new DefaultNamedAuthor(name, AuthorIdGenerator.newId());
        NamedAuthor author2 = new DefaultNamedAuthor(name, AuthorIdGenerator.newId());

        ComparatorTester<NamedAuthor> tester = newComparatorTester();
        tester.testEquals(author, author);
        tester.testEquals(author, author2);
    }

    @Test
    public void comparatorEqualityIgnoresCase() {
        String name = RandomString.nextWord();
        NamedAuthor author = new DefaultNamedAuthor(name, AuthorIdGenerator.newId());
        NamedAuthor authorLower = new DefaultNamedAuthor(name.toLowerCase(), AuthorIdGenerator.newId());
        NamedAuthor authorUpper = new DefaultNamedAuthor(name.toUpperCase(), AuthorIdGenerator.newId());

        ComparatorTester<NamedAuthor> tester = newComparatorTester();
        tester.testEquals(author, authorLower);
        tester.testEquals(author, authorUpper);
    }
}
