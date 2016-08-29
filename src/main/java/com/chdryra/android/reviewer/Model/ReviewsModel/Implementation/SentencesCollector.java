/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SentencesCollector {
    private IdableList<RefComment> mReferences;
    private RefComment.SentencesCallback mCallback;
    private int mCount = 0;
    private IdableList<RefComment.Sentence> mResults;

    public SentencesCollector(IdableList<RefComment> references, RefComment
            .SentencesCallback callback) {
        mReferences = references;
        mCallback = callback;
    }

    public void collect() {
        mResults = new IdableDataList<>(mReferences.getReviewId());
        mCount = 0;
        for (RefComment reference : mReferences) {
            if (reference.isValidReference()) {
                reference.toSentences(new RefComment.SentencesCallback() {
                    @Override
                    public void onSentenceReferences(IdableList<RefComment.Sentence> references) {
                        mResults.addAll(references);
                        if (++mCount == mReferences.size()) callback();
                    }
                });
            }
        }
    }

    private void callback() {
        mCallback.onSentenceReferences(mResults);
    }
}
