/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 01/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UnattachedReviewViewException extends RuntimeException{
    public UnattachedReviewViewException() {
        super();
    }

    public UnattachedReviewViewException(String detailMessage) {
        super(detailMessage);
    }
}
