/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.corelibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.TestUtils.RandomAuthor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorAuthorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ComparatorAuthor mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparatorAuthor();
    }

    @Test
    public void sameAuthorReturnsDifferenceBooleanOfFalse() {
        AuthorName lhs = RandomAuthor.nextAuthor();
        AuthorName rhs = new AuthorNameDefault(lhs.getName(), lhs.getAuthorId());
        checkComparisonHasDifference(lhs, rhs, false);
    }

    @Test
    public void differentAuthorsReturnsDifferenceBooleanOfTrue() {
        AuthorName lhs = RandomAuthor.nextAuthor();
        AuthorName rhs = RandomAuthor.nextAuthor();
        checkComparisonHasDifference(lhs, rhs, true);
    }

    @Test
    public void sameNamesDifferentUserIdsThrowsRuntimeException() {
        expectedException.expect(RuntimeException.class);
        AuthorName lhs = RandomAuthor.nextAuthor();
        AuthorName rhs = RandomAuthor.nextAuthor();
        mComparitor.compare(lhs, new AuthorNameDefault(lhs.getName(), rhs.getAuthorId()));
    }

    @Test
    public void differentNamesSameUserIdsThrowsRuntimeException() {
        expectedException.expect(RuntimeException.class);
        AuthorName lhs = RandomAuthor.nextAuthor();
        AuthorName rhs = RandomAuthor.nextAuthor();
        mComparitor.compare(lhs, new AuthorNameDefault(rhs.getName(), lhs.getAuthorId()));
    }

    private void checkComparisonHasDifference(AuthorName lhs, AuthorName rhs, boolean
            expected) {
        DifferenceBoolean calculated1 = mComparitor.compare(lhs, rhs);
        DifferenceBoolean calculated2 = mComparitor.compare(rhs, lhs);
        DifferenceBoolean expectedDiff = new DifferenceBoolean(expected);
        assertThat(calculated1.lessThanOrEqualTo(expectedDiff), is(true));
        assertThat(calculated2.lessThanOrEqualTo(expectedDiff), is(true));
    }
}
