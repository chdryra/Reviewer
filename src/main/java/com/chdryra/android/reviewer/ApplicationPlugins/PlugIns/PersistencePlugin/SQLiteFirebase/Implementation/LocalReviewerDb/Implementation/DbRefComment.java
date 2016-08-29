/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SimpleItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.StaticItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.CommentFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbRefComment extends SimpleItemReference<DataComment> implements RefComment {
    private boolean mIsHeadline;

    public DbRefComment(boolean isHeadline, DbItemDereferencer<RowComment, DataComment> dereferencer) {
        super(dereferencer);
        mIsHeadline = isHeadline;
    }

    @Override
    public Sentence getFirstSentence() {
        return new DbFirstSentence(this);
    }

    @Override
    public void toSentences(final SentencesCallback callback) {
        dereference(new DereferenceCallback<DataComment>() {
            @Override
            public void onDereferenced(@Nullable DataComment data, CallbackMessage message) {
                IdableList<Sentence> sentences = new IdableDataList<>(getReviewId());
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
    private DbSentence newSentence(String string) {
        return new DbSentence(new DatumComment(getReviewId(), string, isHeadline()), this);
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }

    private static class DbSentence extends StaticItemReference<DataComment> implements Sentence {
        private RefComment mParent;

        public DbSentence(DataComment value, RefComment parent) {
            super(value);
            mParent = parent;
        }

        @Override
        public RefComment getParent() {
            return mParent;
        }
    }

    private static class DbFirstSentence extends SimpleItemReference<DataComment> implements Sentence {
        public DbFirstSentence(RefComment parent) {
            super(new FirstSentenceDereferencer(parent));
        }

        @Override
        public RefComment getParent() {
            return ((FirstSentenceDereferencer)getDereferencer()).getParent();
        }

        private static class FirstSentenceDereferencer implements Dereferencer<DataComment> {
            private RefComment mParent;

            public FirstSentenceDereferencer(RefComment parent) {
                mParent = parent;
            }

            public RefComment getParent() {
                return mParent;
            }

            @Override
            public ReviewId getReviewId() {
                return mParent.getReviewId();
            }

            @Override
            public void dereference(final DereferenceCallback<DataComment> callback) {
                mParent.dereference(new DereferenceCallback<DataComment>() {
                    @Override
                    public void onDereferenced(@Nullable DataComment data, CallbackMessage message) {
                        DataComment sentence = null;
                        if(data != null && !message.isError()) {
                            String comment = data.getComment();
                            ArrayList<String> split = CommentFormatter.split(comment, true);
                            sentence = new DatumComment(getReviewId(), split.get(0), mParent.isHeadline());
                        }
                        callback.onDereferenced(sentence, message);
                    }
                });
            }
        }
    }

}
