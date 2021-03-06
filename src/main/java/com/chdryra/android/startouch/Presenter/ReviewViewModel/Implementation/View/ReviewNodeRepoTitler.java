/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryDataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.NodeTitler;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;

/**
 * Created by: Rizwan Choudrey
 * On: 08/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeRepoTitler extends ReviewNodeRepo implements NodeTitler.TitleBinder {
    private final DataReview mMeta;

    public ReviewNodeRepoTitler(DataReview meta,
                                ReviewsRepoReadable repo,
                                FactoryDataReference referenceFactory,
                                FactoryReviewNode nodeFactory,
                                NodeTitler titler) {
        super(meta, repo, referenceFactory, nodeFactory);
        mMeta = meta;
        titler.bind(this);
    }

    @Override
    public void onTitle(String title) {
        DatumSubject subject = new DatumSubject(mMeta.getReviewId(), title);
        DataReview meta = new ReviewInfo(mMeta.getReviewId(), subject,
                mMeta.getRating(), mMeta.getAuthorId(), mMeta.getPublishDate());
        setMeta(meta);
    }
}
