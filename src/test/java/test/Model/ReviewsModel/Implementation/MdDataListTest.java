/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;

import org.junit.Test;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataListTest {

    @Test
    public void getReviewIdReturnsCorrectId() {
        MdReviewId correctId = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(correctId);
        assertThat(ids.getReviewId(), is((ReviewId) correctId));
    }

    @Test
    public void containsIdReturnsTrueIfTrue() {
        MdReviewId holderId = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum = RandomReviewId.nextMdReviewId();
        ids.add(idDatum);
        assertThat(ids.containsId(idDatum.getReviewId()), is(true));
    }

    @Test
    public void containsIdReturnsFalseIfFalse() {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(id);
        MdReviewId idDatum1 = RandomReviewId.nextMdReviewId();
        MdReviewId idDatum2 = RandomReviewId.nextMdReviewId();
        ids.add(idDatum1);
        assertThat(ids.containsId(idDatum2.getReviewId()), is(false));
    }

    @Test
    public void getItemNotNullIfContains() {
        MdReviewId holderId = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum = RandomReviewId.nextMdReviewId();
        ids.add(idDatum);
        assertThat(ids.getItem(idDatum.getReviewId()), is(idDatum));
    }

    @Test
    public void getItemNullIfNotContains() {
        MdReviewId id = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(id);
        MdReviewId idDatum1 = RandomReviewId.nextMdReviewId();
        MdReviewId idDatum2 = RandomReviewId.nextMdReviewId();
        ids.add(idDatum1);
        assertThat(ids.getItem(idDatum2.getReviewId()), is(nullValue()));
    }

    @Test
    public void removeIfContains() {
        MdReviewId holderId = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum = RandomReviewId.nextMdReviewId();
        ids.add(idDatum);
        assertThat(ids.getItem(idDatum.getReviewId()), is(idDatum));

        ids.remove(idDatum.getReviewId());
        assertThat(ids.getItem(idDatum.getReviewId()), is(nullValue()));
    }

    @Test
    public void doesNotRemoveIfNotContains() {
        MdReviewId holderId = RandomReviewId.nextMdReviewId();
        MdDataList<MdReviewId> ids = new MdDataList<>(holderId);
        MdReviewId idDatum1 = RandomReviewId.nextMdReviewId();
        MdReviewId idDatum2 = RandomReviewId.nextMdReviewId();
        ids.add(idDatum1);
        assertThat(ids.getItem(idDatum1.getReviewId()), is(idDatum1));

        ids.remove(idDatum2.getReviewId());
        assertThat(ids.getItem(idDatum1.getReviewId()), is(idDatum1));
    }
}
