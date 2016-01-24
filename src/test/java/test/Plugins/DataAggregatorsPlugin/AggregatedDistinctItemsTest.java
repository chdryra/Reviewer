/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Factories
        .FactoryDataAggregatorParams;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedData;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.FactoryDataAggregator;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorLevenshteinDistance;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.FactoryDataAggregatorDefault;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class AggregatedDistinctItemsTest<T extends HasReviewId> {
    private static final int MIN_TYPES = 5;
    private static final int MAX_EXTRA_TYPES = 10;
    private static final int MIN_DATA = 10;
    private static final int MAX_EXTRA_DATA = 20;

    private static Random RAND = new Random();

    private DataAggregator<T> mAggregator;
    protected Map<T, Integer> mCanonicalsMap;

    @NonNull
    protected abstract DataAggregator<T> newAggregator(FactoryDataAggregator factory, DataAggregatorParams params);
    @NonNull
    protected abstract T randomDatum();
    @NonNull
    protected abstract T newSimilarDatum(ReviewId reviewId, T template);

    public Random getRAND() {
        return RAND;
    }

    @Before
    public void setUp() {
        FactoryDataAggregatorParams paramsFactory = new FactoryDataAggregatorParams();
        DataAggregatorParams defaultParams = paramsFactory.getDefaultParams();
        mAggregator = newAggregator(new FactoryDataAggregatorDefault(new ComparitorLevenshteinDistance()), defaultParams);
        mCanonicalsMap = new HashMap<>();
    }

    @Test
    public void singleTypeAggregatesIntoOnePile() {
        checkAggregation(1);
    }

    @Test
    public void separateTypesAggregateIntoSeparatePiles() {
        checkAggregation(nextNumTypes());
    }

    private void checkAggregation(int numTypes) {
        ReviewId id = RandomReviewId.nextReviewId();
        IdableList<T> data = getData(id, numTypes);

        AggregatedList<T> aggregatedList = mAggregator.aggregate(data);

        assertThat(aggregatedList.size(), is(numTypes));
        for (AggregatedData<T> aggregated : aggregatedList) {
            T canonical = aggregated.getCanonical();
            IdableList<T> aggregatedItems = aggregated.getAggregatedItems();

            checkCanonicalInMap(canonical);
            checkCanonicalItemsSize(canonical, aggregatedItems.size());
        }
    }

    protected void checkCanonicalItemsSize(T canonical, int size) {
        assertThat(mCanonicalsMap.get(canonical), is(size));
    }

    protected void checkCanonicalInMap(T canonical) {
        assertThat(mCanonicalsMap.containsKey(canonical), is(true));
    }

    @NonNull
    private IdableList<T> getData(ReviewId id, int numTypes) {
        ArrayList<T> dataArray = new ArrayList<>();
        for (int i = 0; i < numTypes; ++i) {
            ArrayList<T> data = addData(dataArray);
            mCanonicalsMap.put(getExampleCanonical(id, data), data.size());
        }

        return getIdableList(id, dataArray);
    }

    protected T getExampleCanonical(ReviewId id, ArrayList<T> data) {
        return newSimilarDatum(id, data.get(0));
    }

    @NonNull
    private IdableList<T> getIdableList(ReviewId id, ArrayList<T> dataArray) {
        Collections.shuffle(dataArray);
        IdableList<T> data = new IdableDataList<>(id);
        data.addAll(dataArray);
        return data;
    }

    @NonNull
    private ArrayList<T> addData(ArrayList<T> dataArray) {
        ArrayList<T> data = newData(nextNumData());
        dataArray.addAll(data);
        return data;
    }

    private ArrayList<T> newData(int numData) {
        ArrayList<T> data = new ArrayList<>();
        T randomDatum = randomDatum();
        for (int i = 0; i < numData; ++i) {
            data.add(newSimilarDatum(RandomReviewId.nextReviewId(), randomDatum));
        }

        return data;
    }

    private int nextNumTypes() {
        return MIN_TYPES + RAND.nextInt(MAX_EXTRA_TYPES);
    }

    private int nextNumData() {
        return MIN_DATA + RAND.nextInt(MAX_EXTRA_DATA);
    }
}
