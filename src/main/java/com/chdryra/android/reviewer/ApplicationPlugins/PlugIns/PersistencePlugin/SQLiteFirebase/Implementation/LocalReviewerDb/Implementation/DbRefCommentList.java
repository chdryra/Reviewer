/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Factories.FactoryDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.SentencesCollector;

/**
 * Created by: Rizwan Choudrey
 * On: 29/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbRefCommentList extends DbListReferenceBasic<RowComment, DataComment, RefComment> implements RefCommentList {
    public DbRefCommentList(DataLoader<RowComment> loader,
                            FactoryDbReference referenceFactory,
                            Converter<RowComment, DataComment> converter) {
        super(loader, referenceFactory, converter);
    }

    @Override
    public void toSentences(final RefComment.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, RefComment>() {
            @Override
            public void onItemReferences(IdableList<RefComment> refComments) {
                new SentencesCollector(refComments, callback).collect();
            }
        });
    }

    @Override
    protected RefComment newReference(DataLoader.RowLoader<RowComment> loader, RowComment datum) {
        return getReferenceFactory().newReference(loader, datum.isHeadline());
    }
}
