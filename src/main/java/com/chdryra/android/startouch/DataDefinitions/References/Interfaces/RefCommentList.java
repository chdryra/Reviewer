/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RefCommentList extends ReviewListReference<DataComment, RefComment> {
    void toSentences(RefComment.SentencesCallback callback);
}
