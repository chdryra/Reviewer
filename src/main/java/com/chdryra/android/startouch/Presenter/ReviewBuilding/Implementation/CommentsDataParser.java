/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

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
    public static final String SEPARATOR = "(\\s+" + Strings.Buttons.CommentEdit.SEPARATOR + "\\s+)";
    private static final String WORD = "#?(\\w+)";
    private static final String STARS = "(\\d+(\\.\\d*)?)\\*";
    private static final String VALUE = "#?(\\S+?)((?:\\p{Punct}+\\s+)|$)";
    private static final String REGEX_CRIT = WORD + SEPARATOR + STARS;
    private static final String REGEX_FACT = WORD + SEPARATOR + "(?!" + STARS + ")" + VALUE;
    private static final String CAMEL = "(?=[A-Z])";

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
        mBuilder.getDataBuilder(GvTag.TYPE).commitData();
        mBuilder.getDataBuilder(GvCriterion.TYPE).commitData();
        mBuilder.getDataBuilder(GvFact.TYPE).commitData();
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
        Pattern criterionPattern = Pattern.compile(REGEX_CRIT);
        Matcher matcher = criterionPattern.matcher(comment.getComment());
        GvCriterionList criteria = new GvCriterionList();

        while (matcher.find()) {
            String subject = matcher.group(1);
            String stars = matcher.group(3);
            if(subject == null || stars == null) continue;

            float rating = Float.valueOf(stars.trim());
            if(rating > 5f) continue;

            criteria.add(new GvCriterion(upperFirst(subject.trim()), rating));
        }

        return criteria;
    }

    private GvFactList getFacts(GvComment comment) {
        Pattern factPattern = Pattern.compile(REGEX_FACT);
        Matcher matcher = factPattern.matcher(comment.getComment());
        GvFactList facts = new GvFactList();
        ArrayList<String> links = TextUtils.getLinks(comment.getComment());

        while (matcher.find()) {
            String label = matcher.group(1);
            String value = matcher.group(5);
            if(label == null || value == null) continue;

            if(links.contains(label)) {
                String temp = value;
                value = label;
                label = temp;
            }

            label = label.trim();
            value = value.trim().replaceAll("\\p{Punct}+$", "").trim();
            label = camelCaseToSpace(label);
            GvFact fact;
            if(links.contains(value)) {
                links.remove(value);
                fact = newUrl(upperFirst(label), value);
            } else {
                value = camelCaseToSpace(value);
                fact = new GvFact(upperFirst(label), upperFirst(value));
            }

            facts.add(fact);
        }

        for(int i = 0; i < links.size(); ++i) {
            facts.add(newUrl(Strings.Web.LINK + " " + String.valueOf(i + 1), links.get(i)));
        }

        return facts;
    }

    private String camelCaseToSpace(String string) {
        return StringUtils.join(string.split(CAMEL), " ").trim();
    }

    private GvFact newUrl(String label, String value) {
        return GvFact.newFactOrUrl(label, value);
    }

    @NonNull
    private String upperFirst(String subject) {
        return subject.substring(0, 1).toUpperCase() + subject.substring(1);
    }
}