/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomCommentGenerator {
    private static final int MIN = 3;
    private static final int MAX = 8;

    private final String   mDelimeters;
    private       String[] mSentences;
    private       Random   mRand;

    public RandomCommentGenerator(String delimeters) {
        mDelimeters = delimeters;
        mRand = new Random();
    }

    public String nextComment() {
        String comment = "";
        int numSentences = randomLength();
        mSentences = new String[numSentences];
        for (int w = 0; w < numSentences; ++w) {
            String sentence = nextSentence() + randomDelimiter();
            comment += sentence + " ";
            mSentences[w] = sentence;
        }

        return comment.trim();
    }

    public String[] getSentences() {
        return mSentences;
    }

    private String nextSentence() {
        String sentence = "";
        int numWords = randomLength();
        for (int w = 0; w < numWords; ++w) {
            sentence += nextWord() + " ";
        }

        return sentence.trim();
    }

    private String nextWord() {
        return RandomStringUtils.randomAlphanumeric(randomLength());
    }

    private char randomDelimiter() {
        return mDelimeters.charAt(mRand.nextInt(mDelimeters.length()));
    }

    private int randomLength() {
        return MIN + mRand.nextInt(MAX - MIN);
    }
}
