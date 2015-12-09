package test.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.fail;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdReviewIdTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validToStringFormatting() {
        String userId = "abc";
        long time = 123l;
        int increment = 314;

        MdReviewId id = new MdReviewId(userId, time, increment);
        String idString = id.toString();

        String[] split = idString.split(":");
        assertThat(split[0], is(userId));
        assertThat(Long.parseLong(split[1]), is(time));
        assertThat(Integer.parseInt(split[2]), is(increment));
    }

    @Test
    public void testEquals() {
        String userId = "abc";
        long time = 123l;
        int increment = 314;

        MdReviewId id1 = new MdReviewId(userId, time, increment);
        MdReviewId id2 = new MdReviewId(userId, time, increment);

        assertThat(id1, is(id2));
    }

    @Test
    public void testNotEquals() {
        String userId = "abc";
        long time = 123l;
        int increment = 314;

        MdReviewId id1 = new MdReviewId(userId, time, increment);
        MdReviewId id2 = new MdReviewId(userId, time, increment + 1);
        MdReviewId id3 = new MdReviewId(userId, time + 1l, increment);
        MdReviewId id4 = new MdReviewId(userId + "d", time, increment);

        assertThat(id1, not(id2));
        assertThat(id1, not(id3));
        assertThat(id1, not(id4));
    }

    @Test
    public void testConstructorThrowsOnNullUserId() {
        expectedException.expect(IllegalArgumentException.class);
        new MdReviewId(null, 123, 345);
    }

    @Test
    public void stringConstructorConstructsCorrectlyOnValidString() {
        String idString = "abc:123:314";
        try {
            MdReviewId id = new MdReviewId(idString);
            assertThat(id.toString(), is(idString));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void stringConstructorThrowsExceptionOnInvalidStringNoSeparator() {
        expectedException.expect(IllegalArgumentException.class);
        new MdReviewId("abc123314");
    }

    @Test
    public void stringConstructorThrowsExceptionOnInvalidStringOneSeparator() {
        expectedException.expect(IllegalArgumentException.class);
        new MdReviewId("abc:123314");
    }

    @Test
    public void stringConstructorThrowsExceptionOnInvalidStringThreeSeparators() {
        expectedException.expect(IllegalArgumentException.class);
        new MdReviewId("abc:123:31:4");
    }

    @Test
    public void stringConstructorThrowsExceptionOnInvalidStringNoTime() {
        expectedException.expect(IllegalArgumentException.class);
        new MdReviewId("abc:cde:314");
    }

    @Test
    public void stringConstructorThrowsExceptionOnInvalidStringNoIncrement() {
        expectedException.expect(IllegalArgumentException.class);
        new MdReviewId("abc:123:cde");
    }
}
