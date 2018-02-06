/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.View;

/**
 * Created by: Rizwan Choudrey
 * On: 01/12/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface OptionSelectListener {
    boolean onOptionSelected(int requestCode, String option);

    boolean onOptionsCancelled(int requestCode);
}
