/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;


/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class UiImagesBinder extends UiBinder<DataSize> {
    private final UiBinder<IdableList<DataImage>> mImages;
    private boolean mIsImages = false;

    public UiImagesBinder(Bindable<DataSize> bindable,
                          DataReference<DataSize> reference,
                          UiBinder<IdableList<DataImage>> images) {
        super(bindable, reference);
        mImages = images;
    }

    @Override
    public void onReferenceValue(DataSize value) {
        super.onReferenceValue(value);
        if (value.getSize() > 0) {
            bindImagesIfNecessary();
        } else {
            unbindImagesIfNecessary();
        }
    }

    @Override
    public void onInvalidated(DataReference<DataSize> reference) {
        super.onInvalidated(reference);
        unbindImagesIfNecessary();
    }

    private void bindImagesIfNecessary() {
        if(!mIsImages) {
            mImages.bind();
            mIsImages = true;
        }
    }

    private void unbindImagesIfNecessary() {
        if(mIsImages) {
            mImages.unbind();
            mIsImages = false;
        }
    }

    @Override
    public void unbind() {
        super.unbind();
        unbindImagesIfNecessary();
    }
}
