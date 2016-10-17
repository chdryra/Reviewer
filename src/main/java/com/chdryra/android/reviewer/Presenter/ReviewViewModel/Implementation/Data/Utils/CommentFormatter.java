/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.CommentsDataParser;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for comment string processing.
 * <ul>
 * <li>Extraction of 1st sentence as a headline</li>
 * <li>Splitting of comments into sentences</li>
 * </ul>
 */
public class CommentFormatter {
    private static final String IGNORE_DELIMITER = ".";
    private static final String SEPARATOR_REPLACEMENT = ": ";

    public static String getFirstSentence(String comment) {
        return format(split(comment).get(0));
    }

    public static ArrayList<String> split(String comment) {
        return split(comment, true);
    }

    public static String join(List<String> sentences) {
        String comment = "";
        for(String sentence : sentences) {
            comment += sentence + " ";
        }

        return comment.trim();
    }

    public static ArrayList<String> split(String comment, boolean ignoreStop) {
        ArrayList<String> comments = new ArrayList<>();
        BreakIterator boundary = BreakIterator.getSentenceInstance();
        boundary.setText(comment);
        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {
            String substring = comment.substring(start, end).trim();
            if(ignoreStop) substring = trim(substring, IGNORE_DELIMITER);
            comments.add(substring);
        }

        return comments;
    }

    public static String format(String comment) {
        return comment.replaceAll(CommentsDataParser.SEPARATOR, SEPARATOR_REPLACEMENT);
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
}
