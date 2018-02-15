/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbCommentListRef extends DbListReferenceBasic<RowComment, DataComment, CommentRef> implements CommentListRef {
    public DbCommentListRef(DataLoader<RowComment> loader,
                            FactoryDbReference referenceFactory,
                            Converter<RowComment, DataComment> converter) {
        super(loader, referenceFactory, converter);
    }

    @Override
    public void toSentences(final CommentRef.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, CommentRef>() {
            @Override
            public void onItemReferences(IdableList<CommentRef> commentRefs) {
                getDbReferenceFactory().getReferenceFactory().newSentencesCollector(commentRefs).collectAll(callback);
            }
        });
    }

    @Override
    protected CommentRef newReference(DataLoader.RowLoader<RowComment> loader, RowComment datum) {
        return getDbReferenceFactory().newReference(loader, datum.isHeadline());
    }
}
