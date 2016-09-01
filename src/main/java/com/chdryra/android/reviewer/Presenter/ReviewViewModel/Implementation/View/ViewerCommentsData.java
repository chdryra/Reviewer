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
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterReferences;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerCommentsData
        extends ViewerReviewData<DataComment, GvComment.Reference, RefComment, RefCommentList> {
    private FactoryReference mReferenceFactory;

    public ViewerCommentsData(RefCommentList reference,
                              GvConverterReferences<DataComment, GvComment.Reference, RefComment> converter,
                              FactoryReference referenceFactory) {
        super(reference, converter);
        mReferenceFactory = referenceFactory;
    }

    public void setSplit(boolean split) {
        RefCommentList reference = getReference();
        if(split) {
            toAllSentences(reference);
        } else {
            toFirstSentences(reference);
        }
    }

    private void toFirstSentences(RefCommentList reference) {
        reference.toItemReferences(new ReviewListReference.ItemReferencesCallback<DataComment, RefComment>() {
            @Override
            public void onItemReferences(IdableList<RefComment> references) {
                setData(mReferenceFactory.newSentencesCollector(references).collectFirst());
            }
        });
    }

    private void toAllSentences(RefCommentList reference) {
        reference.toSentences(new RefComment.SentencesCallback() {
            @Override
            public void onSentenceReferences(IdableList<RefComment> references) {
                setData(references);
            }
        });
    }

    private void setData(IdableList<RefComment> references) {
        onItemReferences(references);
    }
}
