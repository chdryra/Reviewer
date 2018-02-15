/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation;



import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbCommentListRef extends FbReviewListRef<DataComment, CommentRef> implements CommentListRef {
    private final FbDataReferencer mReferenceFactory;

    public FbCommentListRef(ReviewId id,
                            Firebase reference,
                            ReviewItemReference<DataSize> sizeReference,
                            IdableListConverter<DataComment> converter,
                            ListItemsReferencer<DataComment, CommentRef> referencer,
                            FbDataReferencer referenceFactory) {
        super(id, reference, sizeReference, converter, referencer);
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void toSentences(final CommentRef.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, CommentRef>() {
            @Override
            public void onItemReferences(IdableList<CommentRef> references) {
                mReferenceFactory.newSentencesCollector(references).collectAll(callback);
            }
        });
    }

}
