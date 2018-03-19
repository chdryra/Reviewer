/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Comment {
    public static final String SENTENCES = "sentences";
    public static final String NUM_SENTENCES = "numSentences";

    private String string;
    private List<String> sentences;
    private int numSentences;
    private boolean headline;

    public Comment() {
    }

    public Comment(DataComment comment) {
        String commentString = comment.getComment();
        headline = comment.isHeadline();
        sentences = DataFormatter.split(new DatumComment(comment.getReviewId(), commentString,
                headline), false);
        string = commentString.replaceAll("\n", "<\n>");
        numSentences = sentences.size();
    }

    public static int size() {
        return 4;
    }

    public String getString() {
        return string;
    }

    public int getNumSentences() {
        return numSentences;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public boolean isHeadline() {
        return headline;
    }

    public String toComment() {
        return string.replaceAll("<\\n>", "\n");
    }
}
