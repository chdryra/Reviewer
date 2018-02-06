/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationContexts.Interfaces;

import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PresenterContext {
    FactoryCommands getCommandsFactory();

    FactoryReviewView getReviewViewFactory();

    FactoryImageChooser getImageChooserFactory();

    ConverterGv getGvConverter();
}
