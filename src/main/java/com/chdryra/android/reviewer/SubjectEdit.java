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
public class SubjectEdit extends ReviewViewAction.SubjectAction {
    //Because clear button not picked up by afterTextChanged
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0 && getAdapter().getSubject() != null && getAdapter().getSubject()
                .length() > 0) {
            setSubject(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        setSubject(s.toString());
    }

    private void setSubject(String subject) {
        ReviewViewBuilder controller = (ReviewViewBuilder) getAdapter();
        controller.setSubject(subject);
    }
}
