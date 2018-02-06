/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationContexts.Implementation;

import com.chdryra.android.startouch.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext {
    private FactoryCommands mFactoryCommands;
    private FactoryReviewView mFactoryReviewView;
    private FactoryImageChooser mFactoryImageChooser;
    private ConverterGv mConverter;

    protected PresenterContextBasic() {
    }

    protected void setFactoryCommands(FactoryCommands factoryCommands) {
        mFactoryCommands = factoryCommands;
    }

    protected void setFactoryReviewView(FactoryReviewView factoryReviewView) {
        mFactoryReviewView = factoryReviewView;
    }

    protected void setFactoryImageChooser(FactoryImageChooser factoryImageChooser) {
        mFactoryImageChooser = factoryImageChooser;
    }

    protected void setConverter(ConverterGv converter) {
        mConverter = converter;
    }


    @Override
    public ConverterGv getGvConverter() {
        return mConverter;
    }

    @Override
    public FactoryCommands getCommandsFactory() {
        return mFactoryCommands;
    }

    @Override
    public FactoryReviewView getReviewViewFactory() {
        return mFactoryReviewView;
    }

    @Override
    public FactoryImageChooser getImageChooserFactory() {
        return mFactoryImageChooser;
    }
}
