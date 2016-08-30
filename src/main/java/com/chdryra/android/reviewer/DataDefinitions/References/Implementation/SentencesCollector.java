/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SentencesCollector {
    private IdableList<RefComment> mReferences;
    private int mCount = 0;
    private IdableList<RefComment> mResults;

    public SentencesCollector(IdableList<RefComment> references) {
        mReferences = references;
    }

    public void collect(final RefComment.SentencesCallback callback) {
        mResults = new IdableDataList<>(mReferences.getReviewId());
        mCount = 0;
        for (RefComment reference : mReferences) {
            if (reference.isValidReference()) {
                reference.toSentences(new RefComment.SentencesCallback() {
                    @Override
                    public void onSentenceReferences(IdableList<RefComment> references) {
                        mResults.addAll(references);
                        if (++mCount == mReferences.size()) callback.onSentenceReferences(mResults);
                    }
                });
            }
        }
    }
}
