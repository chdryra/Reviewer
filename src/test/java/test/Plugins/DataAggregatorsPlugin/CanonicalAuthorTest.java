/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.CanonicalAuthor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalAuthorTest {
    private static final int NUM = 10;
    private CanonicalAuthor mCanonical;
    private String mAuthorName;
    private AuthorId mAuthorId;

    @Before
    public void setUp() {
        mCanonical = new CanonicalAuthor(new ComparatorAuthor());
        mAuthorName = RandomString.nextWord();
        mAuthorId = new AuthorIdParcelable(RandomString.nextWord());
    }

    @Test
    public void ifNoDataThenReturnsNullAuthor() {
        ReviewId reviewId = RandomReviewId.nextReviewId();
        IdableList<DataAuthorName> authors = new IdableDataList<>(reviewId);

        DataAuthorName canonical = mCanonical.getCanonical(authors);

        assertThat(canonical, is(FactoryNullData.nullAuthor(reviewId)));
    }

    @Test
    public void ifMoreThanOneAuthorReferencedInListThenReturnsInvalidAuthor() {
        ReviewId reviewId = RandomReviewId.nextReviewId();
        IdableList<DataAuthorName> authors = getReferenceAuthors(reviewId);
        authors.add(RandomAuthor.nextDataAuthor());

        DataAuthorName canonical = mCanonical.getCanonical(authors);

        assertThat(canonical, is(FactoryNullData.nullAuthor(reviewId)));
    }

    @Test
    public void ifAllAuthorsTheSameInListThenReturnsAppropriateAuthor() {
        ReviewId reviewId = RandomReviewId.nextReviewId();
        IdableList<DataAuthorName> authors = getReferenceAuthors(reviewId);

        DataAuthorName canonical = mCanonical.getCanonical(authors);

        assertThat(canonical.getReviewId().toString(), is(reviewId.toString()));
        assertThat(canonical.getName(), is(mAuthorName));
        assertThat(canonical.getAuthorId(), is(mAuthorId));
    }

    private DatumAuthorName getReferenceAuthor() {
        return new DatumAuthorName(RandomReviewId.nextReviewId(), mAuthorName, mAuthorId);
    }

    @NonNull
    private IdableList<DataAuthorName> getReferenceAuthors(ReviewId reviewId) {
        IdableList<DataAuthorName> authors = new IdableDataList<>(reviewId);
        for (int i = 0; i < NUM; ++i) {
            authors.add(getReferenceAuthor());
        }
        return authors;
    }
}
