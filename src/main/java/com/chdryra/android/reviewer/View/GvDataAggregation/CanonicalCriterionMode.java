package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionMode implements CanonicalDatumMaker<GvCriterion> {
    //Overridden
    @Override
    public GvCriterion getCanonical(GvDataList<GvCriterion> data) {
        if (data.size() == 0) return new GvCriterion(data.getGvReviewId(), "", 0f);

        DatumCounter<GvCriterion, String> subjectCounter = new DatumCounter<>(data,
                new DataGetter<GvCriterion, String>() {
                    //Overridden
                    @Override
                    public String getData(GvCriterion datum) {
                        return datum.getSubject();
                    }
                });

        DatumCounter<GvCriterion, Float> ratingCounter = new DatumCounter<>(data,
                new DataGetter<GvCriterion, Float>() {
//Overridden
                    @Override
                    public Float getData(GvCriterion datum) {
                        return datum.getRating();
                    }
                });

        String maxSubject = subjectCounter.getMaxItem();
        int nonMax = subjectCounter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        float maxRating = ratingCounter.getMaxItem();
        return new GvCriterion(data.getGvReviewId(), maxSubject, maxRating);
    }
}
