/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the behaviour of a ViewHolder-type object that knows how to inflate views and how to
 * update them once inflated.
 * <p/>
 * <p>
 * Provides a simple interface through which to pass
 * {@link GvDataList.GvData} back and forth with
 * inflated views in order to initialise and update themselves, and can return back similar
 * data given their current contents. Adheres to
 * {@link GvDataView}.
 * </p>
 * <p>
 * Aim is to separate out the general workings of a view (button presses,
 * title updates etc.) with that pertinent to viewing and extracting
 * review data.
 * </p>
 *
 * @param <T>: the {@link GvDataList.GvData} type.
 * @see com.chdryra.android.mygenerallibrary.ViewHolder
 * @see DialogGvDataAddFragment
 * @see DialogGvDataEditFragment
 */
interface GvDataViewHolder<T extends GvDataList.GvData> extends GvDataView<T> {
    void inflate(Context context);

    View getView();

    @Override
    void initialiseView(T data);

    @Override
    void updateView(T data);

    @Override
    T getGvData();
}
