/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.References.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class WrapperRefCommentList extends StaticListReferenceBasic<DataComment, RefComment>
        implements RefCommentList {

    private final FactoryReferences mFactory;

    public WrapperRefCommentList(IdableList<DataComment> value, FactoryReferences factory) {
        super(value);
        mFactory = factory;
    }

    @Override
    public void toSentences(final RefComment.SentencesCallback callback) {
        toItemReferences(new ItemReferencesCallback<DataComment, RefComment>() {
            @Override
            public void onItemReferences(IdableList<RefComment> refComments) {
                mFactory.newSentencesCollector(refComments).collectAll(callback);
            }
        });
    }

    @Override
    protected RefComment newStaticReference(DataComment item) {
        return mFactory.newWrapper(item, null);
    }
}
