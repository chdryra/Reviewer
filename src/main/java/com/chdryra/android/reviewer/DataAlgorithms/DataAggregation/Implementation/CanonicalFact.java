/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalFact implements CanonicalDatumMaker<DataFact> {
    //Overridden
    @Override
    public DataFact getCanonical(IdableList<? extends DataFact> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumFact(id, "", "");

        DatumCounter<DataFact, String> labelCounter = getLabelCounter(data);
        DatumCounter<DataFact, String> valueCounter = getValueCounter(data);

        return new DatumFact(id, getLabelMode(labelCounter), getValueMode(valueCounter));
    }

    private String getValueMode(DatumCounter<DataFact, String> valueCounter) {
        int nonMax;
        String maxValue = valueCounter.getModeItem();
        nonMax = valueCounter.getNonModeCount();
        if (nonMax > 0) maxValue = String.valueOf(nonMax + 1) + " values";
        return maxValue;
    }

    private String getLabelMode(DatumCounter<DataFact, String> labelCounter) {
        String maxLabel = labelCounter.getModeItem();
        int nonMax = labelCounter.getNonModeCount();
        if (nonMax > 0) maxLabel += " + " + String.valueOf(nonMax);
        return maxLabel;
    }

    @NonNull
    private DatumCounter<DataFact, String> getValueCounter(IdableList<? extends DataFact> data) {
        DatumCounter<DataFact, String> counter;
        counter = new DatumCounter<>(data,
                new DataGetter<DataFact, String>() {
                    @Override
                    public String getData(DataFact datum) {
                        return datum.getValue();
                    }
                });
        return counter;
    }

    @NonNull
    private DatumCounter<DataFact, String> getLabelCounter(IdableList<? extends DataFact> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataFact, String>() {
                        //Overridden
                        @Override
                        public String getData(DataFact datum) {
                            return datum.getLabel();
                        }
                    });
    }
}
