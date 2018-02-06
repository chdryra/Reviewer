/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Actions;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface MenuOptionsItem<T extends GvData> extends MenuActionItem<T>, OptionSelectListener{
}
