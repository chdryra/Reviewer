/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;


import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.SimpleCommentRef;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SimpleFirstSentenceRef extends SimpleCommentRef {
    public SimpleFirstSentenceRef(CommentRef parent, FactoryReferences referenceFactory) {
        super(parent, referenceFactory, new FirstSentenceDereferencer(parent));
    }

    @Override
    public CommentRef getFirstSentence() {
        return this;
    }

    @Override
    public void toSentences(SentencesCallback callback) {
        IdableList<CommentRef> sentences = new IdableDataList<>(getReviewId());
        sentences.add(this);
        callback.onSentenceReferences(sentences);
    }

    private static class FirstSentenceDereferencer implements Dereferencer<DataComment> {
        private final CommentRef mParent;

        public FirstSentenceDereferencer(CommentRef parent) {
            mParent = parent;
        }

        @Override
        public ReviewId getReviewId() {
            return mParent.getReviewId();
        }

        @Override
        public void dereference(final DereferenceCallback<DataComment> callback) {
            mParent.dereference(new DereferenceCallback<DataComment>() {
                @Override
                public void onDereferenced(DataValue<DataComment> value) {
                    DataValue<DataComment> sentence = value;
                    if (value.hasValue()) {
                        ArrayList<String> split = DataFormatter.split(value.getData(), true);
                        sentence = new DataValue<DataComment>(new DatumComment(getReviewId(),
                                split.get(0), mParent.isHeadline()));
                    }

                    callback.onDereferenced(sentence);
                }
            });
        }
    }
}
