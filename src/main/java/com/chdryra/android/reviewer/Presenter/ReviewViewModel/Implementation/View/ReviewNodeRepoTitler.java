/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeTitler;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsArchive;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeRepoTitler extends ReviewNodeRepo implements NodeTitler.TitleBinder {
    private final DataReviewInfo mMeta;

    public ReviewNodeRepoTitler(DataReviewInfo meta,
                                ReviewsArchive repo,
                                FactoryMdReference referenceFactory,
                                FactoryReviewNode nodeFactory,
                                NodeTitler titler) {
        super(meta, repo, referenceFactory, nodeFactory);
        mMeta = meta;
        titler.bind(this);
    }

    @Override
    public void onTitle(String title) {
        DatumSubject subject = new DatumSubject(mMeta.getReviewId(), title);
        DataReviewInfo meta = new ReviewInfo(mMeta.getReviewId(), subject,
                mMeta.getRating(), mMeta.getAuthorId(), mMeta.getPublishDate());
        setMeta(meta);
    }
}
