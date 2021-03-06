/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ProfileImageDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 * <p>
 * This doesn't work very well as binding is lost if cover deleted due to null path.
 * Maybe should dereference instead.
 */
public class ProfileFormattedUi extends ViewUi<ImageView, ProfileImage> {
    private final int mPlaceholder;

    public ProfileFormattedUi(ImageView view, int placeholder, @Nullable Command onClick) {
        super(view, onClick);
        mPlaceholder = placeholder;
    }

    @Override
    public void update(ProfileImage value) {
        Bitmap image = value.getBitmap();
        if (image != null) {
            getView().setImageBitmap(image);
        } else {
            getView().setImageResource(mPlaceholder);
        }
    }

    @Override
    public void onInvalidated() {
        update(new ProfileImageDefault());
    }
}
