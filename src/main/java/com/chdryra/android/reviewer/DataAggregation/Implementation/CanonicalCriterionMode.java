package com.chdryra.android.reviewer.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumCounter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.DataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionMode implements CanonicalDatumMaker<DataCriterion> {
    //Overridden
    @Override
    public DataCriterion getCanonical(IdableList<? extends DataCriterion> data) {
        String id = data.getReviewId();
        if (data.size() == 0) return new DatumCriterion(id, "", 0f);

        DatumCounter<DataCriterion, String> subjectCounter = getSubjectCounter(data);
        DatumCounter<DataCriterion, Float> ratingCounter = getRatingCounter(data);

        return new DatumCriterion(id, getModeSubject(subjectCounter), getModeRating(ratingCounter));
    }

    private float getModeRating(DatumCounter<DataCriterion, Float> ratingCounter) {
        return ratingCounter.getModeItem();
    }

    private String getModeSubject(DatumCounter<DataCriterion, String> subjectCounter) {
        String maxSubject = subjectCounter.getModeItem();
        int nonMax = subjectCounter.getNonModeCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        return maxSubject;
    }

    @NonNull
    private DatumCounter<DataCriterion, Float> getRatingCounter(IdableList<? extends DataCriterion> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataCriterion, Float>() {
                        @Override
                        public Float getData(DataCriterion datum) {
                            return datum.getRating();
                        }
                    });
    }

    @NonNull
    private DatumCounter<DataCriterion, String> getSubjectCounter(IdableList<? extends DataCriterion> data) {
        return new DatumCounter<>(data,
                    new DataGetter<DataCriterion, String>() {
                        @Override
                        public String getData(DataCriterion datum) {
                            return datum.getSubject();
                        }
                    });
    }
}
