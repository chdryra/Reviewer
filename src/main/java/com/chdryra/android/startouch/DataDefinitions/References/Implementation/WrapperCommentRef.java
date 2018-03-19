/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperCommentRef extends StaticItemReference<DataComment> implements CommentRef {
    private final CommentRef mParent;

    public WrapperCommentRef(DataComment value, @Nullable CommentRef parent) {
        super(value);
        mParent = parent;
    }

    @Nullable
    @Override
    public CommentRef getParent() {
        return mParent;
    }

    @Override
    public CommentRef getFirstSentence() {
        return new WrapperCommentRef(newComment(getSplit().get(0)), this);
    }

    @Override
    public void toSentences(SentencesCallback callback) {
        IdableList<CommentRef> sentences = new IdableDataList<>(getReviewId());
        for (String sentence : getSplit()) {
            sentences.add(new WrapperCommentRef(newComment(sentence), this));
        }
        callback.onSentenceReferences(sentences);
    }

    @Override
    public boolean isHeadline() {
        return getData().isHeadline();
    }

    private ArrayList<String> getSplit() {
        return DataFormatter.split(getData());
    }


    @NonNull
    private DatumComment newComment(String sentence) {
        return new DatumComment(getReviewId(), sentence, isHeadline());
    }
}
