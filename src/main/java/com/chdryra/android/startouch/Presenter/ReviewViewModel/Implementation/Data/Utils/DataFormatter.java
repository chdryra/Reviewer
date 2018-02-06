/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.CommentsDataParser;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Helper class for comment string processing.
 * <ul>
 * <li>Extraction of 1st sentence as a headline</li>
 * <li>Splitting of comments into sentences</li>
 * </ul>
 */
public class DataFormatter {
    private static final String IGNORE_DELIMITER = ".";
    private static final String SEPARATOR_REPLACEMENT = ": ";

    public static String getFirstSentence(DataComment comment) {
        return split(comment).get(0);
    }

    public static ArrayList<String> split(DataComment comment) {
        return split(comment, true);
    }

    public static ArrayList<String> split(DataComment comment, boolean ignoreStop) {
        ArrayList<String> comments = new ArrayList<>();
        BreakIterator boundary = BreakIterator.getSentenceInstance();
        String commentString = comment.getComment();
        boundary.setText(commentString);
        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {
            String substring = commentString.substring(start, end).trim();
            if(ignoreStop) substring = trim(substring, IGNORE_DELIMITER);
            if(substring != null && substring.length() > 0) comments.add(substring);
        }

        return comments;
    }

    public static String format(DataComment comment) {
        return comment.getComment().replaceAll(CommentsDataParser.SEPARATOR, SEPARATOR_REPLACEMENT);
    }

    private static String trim(@NonNull String string, @NonNull String toTrim) {
        String trimmed = string;

        while (trimmed.length() > 0 && toTrim.contains(String.valueOf(trimmed.charAt(0)))) {
            trimmed = (String) trimmed.subSequence(1, trimmed.length());
        }

        while (trimmed.length() > 0 && toTrim.contains(String.valueOf(trimmed.charAt(trimmed
                .length() - 1)))) {
            trimmed = (String) trimmed.subSequence(0, trimmed.length() - 1);
        }

        return trimmed;
    }

    public static String formatComments(Collection<? extends DataComment> comments) {
        String formatted = "";
        String bullet = comments.size() == 1 ? "" : "- ";
        for(DataComment comment : comments) {
            formatted += bullet + format(comment) + "\n\n";
        }

        return formatted.trim();
    }

    public static String getHeadlineQuote(IdableList<? extends DataComment> comments) {
        IdableList<DataComment> headlines = new IdableDataList<>(comments.getReviewId());
        for (DataComment comment : comments) {
            if (comment.isHeadline()) headlines.add(comment);
        }

        String headline = headlines.size() > 0 ? getFirstSentence(headlines.get(0)) : "";
        return headline.length() > 0 ? "\"" + headline + "\"" : headline;
    }

    public static String formatTags(IdableList<? extends DataTag> tags, int maxTags, @Nullable String ignoreTag) {
        String tagsString = "";
        int numTags = Math.min(tags.size(), Math.max(maxTags, tags.size()));
        int diff = tags.size() - numTags;
        int i = 0;
        int count = 0;
        boolean ignored = false;
        while (count < numTags && i < tags.size()) {
            DataTag item = tags.get(i);
            if(!item.getTag().equalsIgnoreCase(ignoreTag)) {
                tagsString += item + " ";
                ++count;
            } else {
                ignored = true;
            }
            ++i;
        }

        if (diff > 0) tagsString += "+ " + String.valueOf(ignored ? diff +1 : diff) + "#";

        return tagsString.trim();
    }

    public static String formatLocationsShort(IdableList<? extends DataLocation> locations) {
        ArrayList<String> locationNames = new ArrayList<>();
        for (DataLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }
        String location = "";
        int locs = locationNames.size();
        if (locs > 0) {
            location = locationNames.get(0);
            if (locs > 1) {
                String loc = locs == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(locationNames.size() - 1) + loc;
            }
        }

        return location.trim();
    }


    public static String getShortenedName(String name) {
        String delimeters = ",|";
        StringTokenizer tokens = new StringTokenizer(name, delimeters);
        String shortened = tokens.nextToken();

        return shortened != null ? shortened.trim() : name;
    }

    public static String getAddress(String name) {
        String[] split = name.split("|", 1);
        return split.length > 1 ? split[1] : name;
    }
}
