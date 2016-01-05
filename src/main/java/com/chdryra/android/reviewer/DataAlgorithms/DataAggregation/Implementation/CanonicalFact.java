/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalFact implements CanonicalDatumMaker<DataFact> {
    @Override
    public DataFact getCanonical(IdableList<? extends DataFact> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return new DatumFact(id, "", "");

        return new DatumFact(id, getLabel(data), getValue(data));
    }

    private String getValue(IdableList<? extends DataFact> data) {
        ItemCounter<DataFact, String> valueCounter = getValueCounter();
        valueCounter.performCount(data);
        String maxValue = valueCounter.getModeItem();
        int nonMax = valueCounter.getNonModeCount();
        if (nonMax > 0) maxValue = String.valueOf(nonMax + 1) + " values";
        return maxValue;
    }

    private String getLabel(IdableList<? extends DataFact> data) {
        ItemCounter<DataFact, String> labelCounter = getLabelCounter();
        labelCounter.performCount(data);
        String maxLabel = labelCounter.getModeItem();
        int nonMax = labelCounter.getNonModeCount();
        if (nonMax > 0) maxLabel += " + " + String.valueOf(nonMax);
        return maxLabel;
    }

    @NonNull
    private ItemCounter<DataFact, String> getValueCounter() {
        ItemCounter<DataFact, String> counter;
        counter = new ItemCounter<>(new ItemGetter<DataFact, String>() {
                    @Override
                    public String getData(DataFact datum) {
                        return datum.getValue();
                    }
                });
        return counter;
    }

    @NonNull
    private ItemCounter<DataFact, String> getLabelCounter() {
        return new ItemCounter<>(new ItemGetter<DataFact, String>() {
                        @Override
                        public String getData(DataFact datum) {
                            return datum.getLabel();
                        }
                    });
    }
}
