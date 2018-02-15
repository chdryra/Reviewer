/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SentencesCollector {
    private final IdableList<CommentRef> mReferences;
    private int mCount = 0;
    private IdableList<CommentRef> mResults;

    public SentencesCollector(IdableList<CommentRef> references) {
        mReferences = references;
    }

    public void collectAll(final CommentRef.SentencesCallback callback) {
        mResults = new IdableDataList<>(mReferences.getReviewId());
        mCount = 0;
        for (CommentRef reference : mReferences) {
            if (reference.isValidReference()) {
                reference.toSentences(new CommentRef.SentencesCallback() {
                    @Override
                    public void onSentenceReferences(IdableList<CommentRef> references) {
                        mResults.addAll(references);
                        if (++mCount == mReferences.size()) callback.onSentenceReferences(mResults);
                    }
                });
            }
        }
    }

    public IdableList<CommentRef> collectFirst() {
        IdableList<CommentRef> results = new IdableDataList<>(mReferences.getReviewId());
        mCount = 0;
        for (CommentRef reference : mReferences) {
            if (reference.isValidReference()) {
                results.add(reference.getFirstSentence());
            }
        }

        return results;
    }
}
