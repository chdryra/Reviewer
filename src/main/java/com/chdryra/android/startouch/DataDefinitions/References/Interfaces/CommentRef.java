/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface CommentRef extends ReviewItemReference<DataComment> {
    interface SentencesCallback {
        void onSentenceReferences(IdableList<CommentRef> references);
    }

    @Nullable
    CommentRef getParent();

    CommentRef getFirstSentence();

    void toSentences(SentencesCallback callback);

    boolean isHeadline();
}
