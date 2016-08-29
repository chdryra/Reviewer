/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RefComment extends ReviewItemReference<DataComment> {
    interface SentencesCallback {
        void onSentenceReferences(IdableList<Sentence> references);
    }

    Sentence getFirstSentence();

    void toSentences(SentencesCallback callback);

    boolean isHeadline();

    interface Sentence extends ReviewItemReference<DataComment> {
        RefComment getParent();
    }
}
