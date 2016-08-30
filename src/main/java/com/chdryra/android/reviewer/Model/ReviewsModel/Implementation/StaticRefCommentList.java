/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StaticRefCommentList extends StaticListReferenceBasic<DataComment, RefComment>
        implements RefCommentList {

    private FactoryMdReference mFactory;

    public StaticRefCommentList(IdableList<DataComment> value, FactoryMdReference factory) {
        super(value);
        mFactory = factory;
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
    protected RefComment newStaticReference(DataComment item) {
        return mFactory.newWrapper(item);
    }
}
