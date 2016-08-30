/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.References.Implementation;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.CommentFormatter;


/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SimpleRefComment extends SimpleItemReference<DataComment> implements RefComment {
    private RefComment mParent;
    private boolean mIsHeadline;
    private FactoryReference mReferenceFactory;

    public SimpleRefComment(boolean isHeadline, FactoryReference referenceFactory, Dereferencer<DataComment> dereferencer) {
        super(dereferencer);
        mIsHeadline = isHeadline;
        mReferenceFactory = referenceFactory;
    }

    public SimpleRefComment(RefComment parent, FactoryReference referenceFactory, Dereferencer<DataComment> dereferencer) {
        super(dereferencer);
        mParent = parent;
        mIsHeadline = mParent.isHeadline();
        mReferenceFactory = referenceFactory;
    }

    @Nullable
    @Override
    public RefComment getParent() {
        return mParent;
    }

    @Override
    public RefComment getFirstSentence() {
        return mReferenceFactory.newFirstSentenceReference(this);
    }

    @Override
    public void toSentences(final SentencesCallback callback) {
        dereference(new DereferenceCallback<DataComment>() {
            @Override
            public void onDereferenced(@Nullable DataComment data, CallbackMessage message) {
                IdableList<RefComment> sentences = new IdableDataList<>(getReviewId());
                if(data != null && !message.isError()) {
                    for(String string : CommentFormatter.split(data.getComment(), true)) {
                        sentences.add(newSentence(string));
                    }
                }
                callback.onSentenceReferences(sentences);
            }
        });
    }

    @NonNull
    private RefComment newSentence(String string) {
        return mReferenceFactory.newWrapper(new DatumComment(getReviewId(), string, isHeadline()), this);
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }
}
