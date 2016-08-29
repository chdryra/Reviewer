/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.BackendFirebase.Implementation;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SentencesCollector;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 23/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbRefCommentList extends FbReviewListRef<DataComment, RefComment> implements RefCommentList {
    public FbRefCommentList(ReviewId id,
                            Firebase reference,
                            ReviewItemReference<DataSize> sizeReference,
                            ListConverter<DataComment> converter,
                            ListItemsReferencer<DataComment, RefComment> referencer) {
        super(id, reference, sizeReference, converter, referencer);
    }

    @Override
    public void toSentences(final RefComment.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, RefComment>() {
            @Override
            public void onItemReferences(IdableList<RefComment> references) {
                new SentencesCollector(references, callback).collect();
            }
        });
    }

}
