/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation.AuthorComparator;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorAlphabeticalTest extends ComparatorTest<AuthorName> {

    public AuthorAlphabeticalTest() {
        super(new AuthorComparator());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        AuthorName A = new AuthorNameDefault("A" + RandomString.nextWord(), AuthorIdGenerator
                .newId());
        AuthorName B = new AuthorNameDefault("B" + RandomString.nextWord(), AuthorIdGenerator
                .newId());
        AuthorName C = new AuthorNameDefault("C" + RandomString.nextWord(), AuthorIdGenerator
                .newId());

        ComparatorTester<AuthorName> tester = newComparatorTester();
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void alphabeticalAscendingStartingStemSame() {
        String name1 = RandomString.nextWord();
        String name2 = name1 + RandomString.nextWord();
        AuthorName author1 = new AuthorNameDefault(name1, AuthorIdGenerator.newId());
        AuthorName author2 = new AuthorNameDefault(name2, AuthorIdGenerator.newId());

        ComparatorTester<AuthorName> tester = newComparatorTester();
        if (name1.compareToIgnoreCase(name2) < 0) {
            tester.testFirstSecond(author1, author2);
        } else if (name1.compareToIgnoreCase(name2) > 0) {
            tester.testFirstSecond(author2, author1);
        } else {
            tester.testEquals(author2, author1);
        }
    }

    @Test
    public void comparatorEquality() {
        String name = RandomString.nextWord();
        AuthorName author = new AuthorNameDefault(name, AuthorIdGenerator.newId());
        AuthorName author2 = new AuthorNameDefault(name, AuthorIdGenerator.newId());

        ComparatorTester<AuthorName> tester = newComparatorTester();
        tester.testEquals(author, author);
        tester.testEquals(author, author2);
    }

    @Test
    public void comparatorEqualityIgnoresCase() {
        String name = RandomString.nextWord();
        AuthorName author = new AuthorNameDefault(name, AuthorIdGenerator.newId());
        AuthorName authorLower = new AuthorNameDefault(name.toLowerCase(), AuthorIdGenerator
                .newId());
        AuthorName authorUpper = new AuthorNameDefault(name.toUpperCase(), AuthorIdGenerator
                .newId());

        ComparatorTester<AuthorName> tester = newComparatorTester();
        tester.testEquals(author, authorLower);
        tester.testEquals(author, authorUpper);
    }
}
