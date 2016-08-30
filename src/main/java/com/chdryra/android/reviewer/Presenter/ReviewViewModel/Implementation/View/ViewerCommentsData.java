/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerCommentsData<GvRef extends GvDataRef<GvRef, DataComment, ?>>
        extends ViewerReviewData<DataComment, GvRef, RefComment, RefCommentList> {

    public ViewerCommentsData(RefCommentList reference,
                              GvConverterReferences<DataComment, GvRef, RefComment> converter) {
        super(reference, converter);
    }

    public void setSplit(boolean split) {
        RefCommentList reference = getReference();
        if(split) {
            reference.toSentences(new RefComment.SentencesCallback() {
                @Override
                public void onSentenceReferences(IdableList<RefComment> references) {

                }
            });
        }
    }
}
