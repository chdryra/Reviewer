/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.text.Editable;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActionSubjectEdit extends ReviewViewAction.SubjectViewAction {
    public ActionSubjectEdit(ControllerReview controller,
            GvDataList.GvType dataType) {
        super(controller, dataType);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() > 0) {
            ControllerReviewEditable controller = (ControllerReviewEditable) getController();
            controller.setSubject(s.toString());
        }
    }
}
