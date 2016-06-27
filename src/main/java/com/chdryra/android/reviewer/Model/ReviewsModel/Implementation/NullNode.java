/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullNode extends NodeLeaf {
    public NullNode() {
        super(new NullReviewReference(), new BindersManager());
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
