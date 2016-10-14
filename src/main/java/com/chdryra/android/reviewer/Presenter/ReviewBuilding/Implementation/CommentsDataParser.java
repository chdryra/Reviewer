/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Rizwan Choudrey
 * On: 11/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class CommentsDataParser {
    private static final String WORD = "#?(\\w+)";
    private static final String COLON = "(:\\s)";
    private static final String STARS = "(\\d+(\\.\\d*)?)\\*";
    private static final String VALUE = "#?(\\S+)";
    private ReviewBuilder mBuilder;

    public CommentsDataParser(ReviewBuilder builder) {
        mBuilder = builder;
    }

    public void add(GvComment newDatum) {
        addTags(newDatum);
        addCriteria(newDatum);
        addFacts(newDatum);
    }

    public void delete(GvComment datum) {
        deleteTags(datum);
        deleteCriteria(datum);
        deleteFacts(datum);
    }

    void commit() {
        mBuilder.getDataBuilder(GvTag.TYPE).buildData();
        mBuilder.getDataBuilder(GvCriterion.TYPE).buildData();
        mBuilder.getDataBuilder(GvFact.TYPE).buildData();
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

    private void addFacts(GvComment newDatum) {
        DataBuilder<GvFact> dataBuilder = mBuilder.getDataBuilder(GvFact.TYPE);
        for (GvFact fact : getFacts(newDatum)) {
            dataBuilder.add(fact);
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

    private void deleteFacts(GvComment newDatum) {
        DataBuilder<GvFact> dataBuilder = mBuilder.getDataBuilder(GvFact.TYPE);
        for (GvFact fact : getFacts(newDatum)) {
            dataBuilder.delete(fact);
        }
    }

    private GvCriterionList getCriteria(GvComment comment) {
        Pattern criterionPattern = Pattern.compile(WORD + COLON + STARS);
        Matcher matcher = criterionPattern.matcher(comment.getComment());
        GvCriterionList criteria = new GvCriterionList();
        while (matcher.find()) {
            String subject = matcher.group(1).trim();
            float rating = Float.valueOf(matcher.group(3).trim());
            if(rating > 5f) continue;

            criteria.add(new GvCriterion(upperFirst(subject), rating));
        }

        return criteria;
    }

    @NonNull
    private String upperFirst(String subject) {
        return subject.substring(0, 1).toUpperCase() + subject.substring(1);
    }

    private GvFactList getFacts(GvComment comment) {
        String val = "(?!" + STARS + ")" + "(#?(\\S+))";

        ArrayList<String> links = TextUtils.getLinks(comment.getComment());
        Pattern factPattern = Pattern.compile(WORD + COLON + val);
        Matcher matcher = factPattern.matcher(comment.getComment());
        GvFactList facts = new GvFactList();
        while (matcher.find()) {
            for(int i = 0; i < matcher.groupCount(); ++i) {
                String a = matcher.group(i);
                if(a != null) Log.i("Regex", a);
            }

            String label = matcher.group(1).trim();
            String value = matcher.group(4).trim();
            if(value.startsWith("#")) value = value.substring(1);

            if(links.contains(label)) {
                String temp = value;
                value = label;
                label = temp;
            }

            boolean isUrl = false;
            if(links.contains(value)) {
                isUrl = true;
                links.remove(value);
            }

            value = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(value), " ");

            facts.add(new GvFact(upperFirst(label), isUrl ? value : upperFirst(value)));
        }

        for(int i = 0; i < links.size(); ++i) {
            facts.add(GvFact.newFactOrUrl(Strings.LINK + " " + String.valueOf(i+1), links.get(i)));
        }

        return facts;
    }
}