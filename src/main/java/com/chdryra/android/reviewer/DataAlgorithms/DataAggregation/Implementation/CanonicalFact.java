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
import com.chdryra.android.reviewer.DataDefinitions.Factories.NullData;
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
    private static final CanonicalStringMaker<DataFact> LABEL_MAKER = new LabelMaker();
    private static final CanonicalStringMaker<DataFact> VALUE_MAKER = new ValueMaker();

    @Override
    public DataFact getCanonical(IdableList<? extends DataFact> data) {
        ReviewId id = data.getReviewId();
        if (data.size() == 0) return NullData.nullFact(id);

        DataFact labelMode = LABEL_MAKER.getCanonical(data);
        DataFact valueMode = VALUE_MAKER.getCanonical(data);
        return new DatumFact(id, labelMode.getLabel(), valueMode.getValue());
    }

    private static class LabelMaker extends CanonicalStringMaker<DataFact> {
        @Override
        public DataFact getCanonical(IdableList<? extends DataFact> data) {
            return new DatumFact(data.getReviewId(), getModeString(data), "");
        }

        @NonNull
        @Override
        protected ItemGetter<DataFact, String> getStringGetter() {
            return new ItemGetter<DataFact, String>() {
                @Override
                public String getItem(DataFact datum) {
                    return datum.getLabel();
                }
            };
        }
    }

    private static class ValueMaker extends CanonicalStringMaker<DataFact> {
        @Override
        public DataFact getCanonical(IdableList<? extends DataFact> data) {
            return new DatumFact(data.getReviewId(), "", getModeString(data));
        }

        @NonNull
        @Override
        protected ItemGetter<DataFact, String> getStringGetter() {
            return new ItemGetter<DataFact, String>() {
                @Override
                public String getItem(DataFact datum) {
                    return datum.getValue();
                }
            };
        }

        @NonNull
        @Override
        protected String formatModeString(String modeString, int nonMode) {
            return String.valueOf(nonMode + 1) + " values";
        }
    }
}
