package test.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataListTest {

    @Test
    public void getReviewIdReturnsCorrectId() {
        MdReviewId correctId = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(correctId);
        assertThat(ids.getReviewId(), is(correctId.toString()));
    }

    @Test
    public void containsIdReturnsTrueIfTrue() {
        MdReviewId holderId = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum = new MdReviewId("cde", 123l, 1);
        ids.add(idDatum);
        assertThat(ids.containsId(idDatum.getReviewId()), is(true));
    }

    @Test
    public void containsIdReturnsFalseIfFalse() {
        MdReviewId id = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(id);
        MdReviewId idDatum1 = new MdReviewId("cde", 123l, 1);
        MdReviewId idDatum2 = new MdReviewId("cde", 123l, 2);
        ids.add(idDatum1);
        assertThat(ids.containsId(idDatum2.getReviewId()), is(false));
    }

    @Test
    public void getItemNotNullIfContains() {
        MdReviewId holderId = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum = new MdReviewId("cde", 123l, 1);
        ids.add(idDatum);
        assertThat(ids.getItem(idDatum.getReviewId()), is(idDatum));
    }

    @Test
    public void getItemNullIfNotContains() {
        MdReviewId id = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(id);
        MdReviewId idDatum1 = new MdReviewId("cde", 123l, 1);
        MdReviewId idDatum2 = new MdReviewId("cde", 123l, 2);
        ids.add(idDatum1);
        assertThat(ids.getItem(idDatum2.getReviewId()), is(nullValue()));
    }

    @Test
    public void removeIfContains() {
        MdReviewId holderId = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum = new MdReviewId("cde", 123l, 1);
        ids.add(idDatum);
        assertThat(ids.getItem(idDatum.getReviewId()), is(idDatum));

        ids.remove(idDatum.getReviewId());
        assertThat(ids.getItem(idDatum.getReviewId()), is(nullValue()));
    }

    @Test
    public void doesNotRemoveIfNotContains() {
        MdReviewId holderId = new MdReviewId("abc", 123l, 1);
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum1 = new MdReviewId("cde", 123l, 1);
        MdReviewId idDatum2 = new MdReviewId("cde", 123l, 2);
        ids.add(idDatum1);
        assertThat(ids.getItem(idDatum1.getReviewId()), is(idDatum1));

        ids.remove(idDatum2.getReviewId());
        assertThat(ids.getItem(idDatum1.getReviewId()), is(idDatum1));
    }
}
