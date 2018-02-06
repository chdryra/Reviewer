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
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbRefCommentList extends FbReviewListRef<DataComment, RefComment> implements RefCommentList {
    private final FbDataReferencer mReferenceFactory;

    public FbRefCommentList(ReviewId id,
                            Firebase reference,
                            ReviewItemReference<DataSize> sizeReference,
                            IdableListConverter<DataComment> converter,
                            ListItemsReferencer<DataComment, RefComment> referencer,
                            FbDataReferencer referenceFactory) {
        super(id, reference, sizeReference, converter, referencer);
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void toSentences(final RefComment.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, RefComment>() {
            @Override
            public void onItemReferences(IdableList<RefComment> references) {
                mReferenceFactory.newSentencesCollector(references).collectAll(callback);
            }
        });
    }

}
