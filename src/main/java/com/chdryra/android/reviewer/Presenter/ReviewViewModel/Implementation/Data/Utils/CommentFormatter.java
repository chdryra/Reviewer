/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils;

import android.support.annotation.NonNull;

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
    public static final String SENTENCE_DELIMITERS = ".!?";
    public static final String IGNORE_DELIMITER = ".";

    public static String getHeadline(String comment) {
        //return getFirstSentence(comment, true);
        return split(comment).get(0);
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

    //
//    public static ArrayList<String> split(String comment, boolean ignoreStop) {
//        ArrayList<String> comments = new ArrayList<>();
//        String remaining = comment;
//        while (remaining != null && remaining.length() > 0) {
//            String sentence = getFirstSentence(remaining, false);
//            remaining = remaining.substring(sentence.length(), remaining.length());
//            String toTrim = ignoreStop ? IGNORE_DELIMITER + " " : " ";
//            sentence = trim(sentence, toTrim);
//            if (sentence != null && sentence.length() > 0) comments.add(sentence);
//        }
//
//        return comments;
//    }

//
//    private static String getFirstSentence(@NonNull String comment, boolean trimmed) {
//        if (comment.length() > 0) {
//            StringTokenizer tokens = new StringTokenizer(comment, SENTENCE_DELIMITERS, true);
//            String headline = tokens.nextToken();
//            if (tokens.hasMoreTokens()) headline += tokens.nextToken();
//            return trimmed ? trim(headline, IGNORE_DELIMITER) : headline;
//        } else {
//            return comment;
//        }
//    }
}
