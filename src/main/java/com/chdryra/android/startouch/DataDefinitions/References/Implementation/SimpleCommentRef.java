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
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;



/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SimpleCommentRef extends SimpleItemReference<DataComment> implements CommentRef {
    private CommentRef mParent;
    private final boolean mIsHeadline;
    private final FactoryReferences mReferenceFactory;

    public SimpleCommentRef(boolean isHeadline, FactoryReferences referenceFactory, Dereferencer<DataComment> dereferencer) {
        super(dereferencer);
        mIsHeadline = isHeadline;
        mReferenceFactory = referenceFactory;
    }

    protected SimpleCommentRef(CommentRef parent, FactoryReferences referenceFactory,
                               Dereferencer<DataComment> dereferencer) {
        super(dereferencer);
        mParent = parent;
        mIsHeadline = mParent.isHeadline();
        mReferenceFactory = referenceFactory;
    }

    @Nullable
    @Override
    public CommentRef getParent() {
        return mParent;
    }

    @Override
    public CommentRef getFirstSentence() {
        return mReferenceFactory.newFirstSentenceReference(this);
    }

    @Override
    public void toSentences(final SentencesCallback callback) {
        dereference(new DereferenceCallback<DataComment>() {
            @Override
            public void onDereferenced(DataValue<DataComment> value) {
                IdableList<CommentRef> sentences = new IdableDataList<>(getReviewId());
                if(value.hasValue()) {
                    for(String string : DataFormatter.split(value.getData(), true)) {
                        sentences.add(newSentence(string));
                    }
                }
                callback.onSentenceReferences(sentences);
            }
        });
    }

    @NonNull
    private CommentRef newSentence(String string) {
        return mReferenceFactory.newWrapper(new DatumComment(getReviewId(), string, isHeadline()), this);
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }
}
