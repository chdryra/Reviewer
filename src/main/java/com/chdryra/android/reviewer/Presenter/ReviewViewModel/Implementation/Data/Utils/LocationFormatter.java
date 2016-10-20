/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils;

import java.util.StringTokenizer;

/**
 * Helper class for comment string processing.
 * <ul>
 * <li>Extraction of 1st sentence as a headline</li>
 * <li>Splitting of comments into sentences</li>
 * </ul>
 */
public class LocationFormatter {
    private static final String DELIMITERS = ",|";

    public static String getShortenedName(String name) {
        StringTokenizer tokens = new StringTokenizer(name, DELIMITERS);
        String shortened = tokens.nextToken();

        return shortened != null ? shortened.trim() : name;
    }
}
