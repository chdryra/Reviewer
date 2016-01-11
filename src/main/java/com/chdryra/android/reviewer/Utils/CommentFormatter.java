/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Helper class for comment string processing.
 * <ul>
 * <li>Extraction of 1st sentence as a headline</li>
 * <li>Splitting of comments into sentences</li>
 * </ul>
 */
public class CommentFormatter {
    public static final String SENTENCE_DELIMITERS = ".!?";
    public static final String IGNORE_DELIMITER = ".";

    public static String getHeadline(String comment) {
        return getFirstSentence(comment, true);
    }

    public static ArrayList<String> split(String comment) {
        ArrayList<String> comments = new ArrayList<>();
        String remaining = comment;
        while (remaining != null && remaining.length() > 0) {
            String sentence = getFirstSentence(remaining, false);
            remaining = remaining.substring(sentence.length(), remaining.length());
            sentence = trim(sentence, IGNORE_DELIMITER + " ");
            if (sentence != null && sentence.length() > 0) comments.add(sentence);
        }

        return comments;
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

    private static String getFirstSentence(@NonNull String comment, boolean trimmed) {
        if (comment.length() > 0) {
            StringTokenizer tokens = new StringTokenizer(comment, SENTENCE_DELIMITERS, true);
            String headline = tokens.nextToken();
            if (tokens.hasMoreTokens()) headline += tokens.nextToken();
            return trimmed ? trim(headline, IGNORE_DELIMITER) : headline;
        } else {
            return comment;
        }
    }
}
