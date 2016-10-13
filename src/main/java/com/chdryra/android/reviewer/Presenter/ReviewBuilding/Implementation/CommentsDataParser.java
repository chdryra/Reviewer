/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Rizwan Choudrey
 * On: 11/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class CommentsDataParser {
    private ReviewBuilder mBuilder;

    public CommentsDataParser(ReviewBuilder builder) {
        mBuilder = builder;
    }

    public void add(GvComment newDatum) {
        addTags(newDatum);
        addCriteria(newDatum);
    }

    public void delete(GvComment datum) {
        deleteTags(datum);
        deleteCriteria(datum);
    }

    void commit() {
        mBuilder.getDataBuilder(GvTag.TYPE).buildData();
        mBuilder.getDataBuilder(GvCriterion.TYPE).buildData();
    }

    private void addTags(GvComment newDatum) {
        DataBuilder<GvTag> dataBuilder = mBuilder.getDataBuilder(GvTag.TYPE);
        for (String tag : TextUtils.getHashTags(newDatum.getComment())) {
            dataBuilder.add(new GvTag(tag));
        }
    }

    private void addCriteria(GvComment newDatum) {
        DataBuilder<GvCriterion> dataBuilder = mBuilder.getDataBuilder(GvCriterion.TYPE);
        for (GvCriterion criterion : getCriteria(newDatum)) {
            dataBuilder.add(criterion);
        }
    }

    private void deleteTags(GvComment datum) {
        DataBuilder<GvTag> dataBuilder = mBuilder.getDataBuilder(GvTag.TYPE);
        for (String tag : TextUtils.getHashTags(datum.getComment())) {
            dataBuilder.delete(new GvTag(tag));
        }
    }

    private void deleteCriteria(GvComment newDatum) {
        DataBuilder<GvCriterion> dataBuilder = mBuilder.getDataBuilder(GvCriterion.TYPE);
        for (GvCriterion criterion : getCriteria(newDatum)) {
            dataBuilder.delete(criterion);
        }
    }

    private GvCriterionList getCriteria(GvComment comment) {
        String number = "(\\d+(\\.\\d*)?)";
        String asterisk = "(\\*\\s)";
        String criterion = "(#?(\\w+))";

        Pattern criteriaPattern = Pattern.compile(number + asterisk + criterion);
        Matcher matcher = criteriaPattern.matcher(comment.getComment());
        GvCriterionList criteria = new GvCriterionList();
        while (matcher.find()) {
            float rating = Float.valueOf(matcher.group(1));
            if(rating > 5f) continue;
            String subject = matcher.group(matcher.groupCount() == 5 ? 5 : 4);
            subject = subject.substring(0, 1).toUpperCase() + subject.substring(1);
            criteria.add(new GvCriterion(subject, rating));
        }

        return criteria;
    }
}