/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.FactoryNullData;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.CanonicalDatumMaker;
import com.chdryra.android.mygenerallibrary.Aggregation.ItemGetter;

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
        if (data.size() == 0) return FactoryNullData.nullFact(id);

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
