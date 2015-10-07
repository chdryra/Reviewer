package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionMode implements CanonicalDatumMaker<GvCriterionList.GvCriterion> {
    //Overridden
    @Override
    public GvCriterionList.GvCriterion getCanonical(GvDataList<GvCriterionList.GvCriterion> data) {
        if (data.size() == 0) return new GvCriterionList.GvCriterion(data.getReviewId(), "", 0f);

        DatumCounter<GvCriterionList.GvCriterion, String> subjectCounter = new DatumCounter<>(data,
                new DataGetter<GvCriterionList.GvCriterion, String>() {
                    //Overridden
                    @Override
                    public String getData(GvCriterionList.GvCriterion datum) {
                        return datum.getSubject();
                    }
                });

        DatumCounter<GvCriterionList.GvCriterion, Float> ratingCounter = new DatumCounter<>(data,
                new DataGetter<GvCriterionList.GvCriterion, Float>() {
                    @Override
                    public Float getData(GvCriterionList.GvCriterion datum) {
                        return datum.getRating();
                    }
                });

        String maxSubject = subjectCounter.getMaxItem();
        int nonMax = subjectCounter.getNonMaxCount();
        if (nonMax > 0) maxSubject += " + " + String.valueOf(nonMax);
        float maxRating = ratingCounter.getMaxItem();
        return new GvCriterionList.GvCriterion(data.getReviewId(), maxSubject, maxRating);
    }
}
