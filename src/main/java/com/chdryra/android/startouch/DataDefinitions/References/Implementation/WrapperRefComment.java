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
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperRefComment extends StaticItemReference<DataComment> implements RefComment {
    private final RefComment mParent;

    public WrapperRefComment(DataComment value, @Nullable RefComment parent) {
        super(value);
        mParent = parent;
    }

    @Nullable
    @Override
    public RefComment getParent() {
        return mParent;
    }

    @Override
    public RefComment getFirstSentence() {
        return new WrapperRefComment(newComment(getSplit().get(0)), this);
    }

    @Override
    public void toSentences(SentencesCallback callback) {
        IdableList<RefComment> sentences = new IdableDataList<>(getReviewId());
        for (String sentence : getSplit()) {
            sentences.add(new WrapperRefComment(newComment(sentence), this));
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