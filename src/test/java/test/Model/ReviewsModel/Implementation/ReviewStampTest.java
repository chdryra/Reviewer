/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomDataDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStampTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void validToStringFormatting() {
        AuthorName author = newAuthor();
        DateTime date = newDate();

        ReviewStamp id = ReviewStamp.newStamp(author, date);
        String idString = id.toString();

        String[] split = idString.split(":");
        assertThat(split[0], is(author.getAuthorId().toString()));
        assertThat(Long.parseLong(split[1]), is(date.getTime()));
    }

    private DateTime newDate() {
        return RandomDataDate.nextDateTime();
    }

    @NonNull
    private AuthorName newAuthor() {
        return RandomAuthor.nextAuthor();
    }

    @Test
    public void testEquals() {
        AuthorName author = newAuthor();
        DateTime date = newDate();

        ReviewStamp id1 = ReviewStamp.newStamp(author, date);
        ReviewStamp id2 = ReviewStamp.newStamp(author, date);
        
        assertThat(id1, is(id2));
    }

    @Test
    public void testNotEquals() {
        AuthorName author1 = newAuthor();
        DateTime date1 = newDate();
        AuthorName author2 = newAuthor();
        DateTime date2 = newDate();

        ReviewStamp id11 = ReviewStamp.newStamp(author1, date1);
        ReviewStamp id12 = ReviewStamp.newStamp(author1, date2);
        ReviewStamp id21 = ReviewStamp.newStamp(author2, date1);
        ReviewStamp id22 = ReviewStamp.newStamp(author2, date2);

        assertThat(id11, not(id12));
        assertThat(id11, not(id21));
        assertThat(id11, not(id22));
    }

    @Test
    public void staticCheckIdReturnsTrueForCorrectFormat() {
        String idString = "abc:123";
        assertThat(ReviewStamp.checkId(new DatumReviewId(idString)), is(true));
    }

    @Test
    public void staticCheckIdReturnsFalseForIncorrectFormat() {
        String idString = "abc123";
        assertThat(ReviewStamp.checkId(new DatumReviewId(idString)), is(false));

        idString = "123abc";
        assertThat(ReviewStamp.checkId(new DatumReviewId(idString)), is(false));

        idString = "abc";
        assertThat(ReviewStamp.checkId(new DatumReviewId(idString)), is(false));
    }
}
