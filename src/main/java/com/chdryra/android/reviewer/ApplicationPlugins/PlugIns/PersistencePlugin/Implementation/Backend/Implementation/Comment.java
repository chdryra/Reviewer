/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .CommentFormatter;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Comment {
    public static final String SENTENCES = "sentences";
    public static final String NUM_SENTENCES = "numSentences";

    private List<String> sentences;
    private int numSentences;
    private boolean headline;

    public Comment() {
    }

    public Comment(DataComment comment) {
        String commentString = comment.getComment();
        sentences = CommentFormatter.split(commentString, false);
        numSentences = sentences.size();
        headline = comment.isHeadline();
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
        return CommentFormatter.join(sentences);
    }

    public static int size() {
        return 3;
    }
}
