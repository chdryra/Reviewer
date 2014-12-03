/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 December, 2014
 */

package com.chdryra.android.reviewer.test;

import com.chdryra.android.reviewer.CommentFormatter;
import com.chdryra.android.reviewer.test.TestUtils.RandomCommentGenerator;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class CommentFormatterTest extends TestCase {
    private static int COMMENTS = 100;
    private RandomCommentGenerator mGenerator;

    public void testGetHeadline() {
        for (int c = 0; c < COMMENTS; ++c) {
            String comment = mGenerator.nextComment();
            String[] sentences = mGenerator.getSentences();
            String headline = CommentFormatter.getHeadline(comment);
            String sentence = sentences[0];
            if (sentence.endsWith(".")) {
                sentence = sentence.substring(0, sentence.length() - 1);
            }
            assertEquals(sentence, headline);
        }
    }

    public void testSplit() {
        for (int c = 0; c < COMMENTS; ++c) {
            String comment = mGenerator.nextComment();
            String[] sentences = mGenerator.getSentences();

            ArrayList<String> split = CommentFormatter.split(comment);
            for (int i = 0; i < split.size(); ++i) {
                String sentence = sentences[i];
                if (sentence.endsWith(".")) {
                    sentence = sentence.substring(0, sentence.length() - 1);
                }
                assertEquals(sentence, split.get(i));
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mGenerator = new RandomCommentGenerator(CommentFormatter.SENTENCE_DELIMITERS);
    }
}
