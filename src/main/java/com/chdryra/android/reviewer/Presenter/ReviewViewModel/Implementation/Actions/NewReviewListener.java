/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;


import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 03/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NewReviewListener {
    String TEMPLATE_ID = TagKeyGenerator.getKey(NewReviewListener.class, "TemplateId");

    void onNewReviewUsingTemplate(ReviewId template);
}
