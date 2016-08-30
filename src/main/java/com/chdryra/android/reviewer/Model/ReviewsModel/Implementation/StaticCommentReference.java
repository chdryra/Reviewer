/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .CommentFormatter;


import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticCommentReference extends StaticItemReference<DataComment> implements RefComment {
    public StaticCommentReference(final DataComment value) {
        super(value);
    }

    @Override
    public Sentence getFirstSentence() {
        return newSentence(getSplit().get(0));
    }

    @Override
    public void toSentences(SentencesCallback callback) {
        IdableList<RefComment.Sentence> sentences = new IdableDataList<>(getReviewId());
        for (String sentence : getSplit()) {
            sentences.add(newSentence(sentence));
        }
        callback.onSentenceReferences(sentences);
    }

    @Override
    public boolean isHeadline() {
        return getData().isHeadline();
    }

    private ArrayList<String> getSplit() {
        return CommentFormatter.split(getData().getComment());
    }

    @NonNull
    private Sentence newSentence(String sentence) {
        return new Sentence(new DatumComment(getReviewId(), sentence, isHeadline()), this);
    }

    private static class Sentence extends StaticCommentReference implements RefComment.Sentence {
        private RefComment mParent;

        public Sentence(DataComment value, RefComment parent) {
            super(value);
            mParent = parent;
        }

        @Override
        public RefComment getParent() {
            return mParent;
        }
    }
}
