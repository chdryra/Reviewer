/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.MdConverterBasic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterBasicTest<T1 extends HasReviewId, T2 extends HasReviewId> {
    private static final int NUM = 10;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private MdConverterBasic<T1, T2> mConverter;

    public MdConverterBasicTest(MdConverterBasic<T1, T2> converter) {
        mConverter = converter;
    }

    protected abstract T1 newDatum();
    protected abstract void checkDatumEquivalence(T1 datum, T2 mdDatum);
    protected abstract void checkDatumEquivalence(T1 datum, T2 mdDatum, ReviewId mdDatumId);

    @Test
    public void convertDatum() {
        T1 datum = newDatum();
        T2 mdDatum = mConverter.convert(datum);
        checkDatumEquivalence(datum, mdDatum);
    }

    @Test
    public void convertDatumWithCorrectReviewId() {
        T1 datum = newDatum();
        T2 mdDatum = mConverter.convert(datum, datum.getReviewId());
        checkDatumEquivalence(datum, mdDatum);
    }

    @Test
    public void convertIterable() {
        ArrayList<T1> data = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }
        ReviewId id = RandomReviewId.nextReviewId();

        MdDataList<T2> mdData = mConverter.convert(data, id);

        assertThat(mdData.getReviewId().toString(), is(id.toString()));
        assertThat(mdData.size(), is(NUM));
        for (int i = 0; i < NUM; ++i) {
            checkDatumEquivalence(data.get(i), mdData.getItem(i), id);
        }
    }

    @Test
    public void convertIdable() {
        ReviewId id = RandomReviewId.nextReviewId();
        IdableDataList<T1> data = new IdableDataList<>(id);
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }

        MdDataList<T2> mdData = mConverter.convert(data);

        assertThat(mdData.getReviewId().toString(), is(id.toString()));
        assertThat(mdData.size(), is(NUM));
        for (int i = 0; i < NUM; ++i) {
            checkDatumEquivalence(data.get(i), mdData.getItem(i));
        }
    }

    @Test
    public void convertIdableViaConvertIterableWithCorrectReviewIdWorks() {
        ReviewId id = RandomReviewId.nextReviewId();
        IdableDataList<T1> data = new IdableDataList<>(id);
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }

        MdDataList<T2> mdData = mConverter.convert(data, id);

        assertThat(mdData.getReviewId().toString(), is(id.toString()));
        assertThat(mdData.size(), is(NUM));
        for (int i = 0; i < NUM; ++i) {
            checkDatumEquivalence(data.get(i), mdData.getItem(i));
        }
    }

    @Test
    public void convertIdableViaConvertIterableWithWrongReviewIdThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        ReviewId id = RandomReviewId.nextReviewId();
        IdableDataList<T1> data = new IdableDataList<>(id);
        for (int i = 0; i < NUM; ++i) {
            data.add(newDatum());
        }

        mConverter.convert(data, RandomReviewId.nextReviewId());
    }

    protected MdConverterBasic<T1, T2> getConverter() {
        return mConverter;
    }
}
