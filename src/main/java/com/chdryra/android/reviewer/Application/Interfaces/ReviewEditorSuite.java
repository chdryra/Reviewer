/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface ReviewEditorSuite {
    String QUICK_ADD = TagKeyGenerator.getKey(ReviewEditorSuite.class, "QuickAdd");
    String QUICK_REVIEW = TagKeyGenerator.getKey(ReviewEditorSuite.class, "QuickReview");

    interface DiscardListener {
        void onDiscarded(boolean discardConfirmed);
    }

    ReviewEditor<?> createEditor(ReviewEditor.EditMode editMode, LocationClient client, @Nullable Review template);

    ReviewEditor<?> getEditor();

    void discardEditor(boolean showAlert, @Nullable DiscardListener listener);
}
