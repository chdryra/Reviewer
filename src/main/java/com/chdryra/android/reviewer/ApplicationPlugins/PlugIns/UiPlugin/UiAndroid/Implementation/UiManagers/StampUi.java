/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.View;
import android.widget.TextView;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StampUi extends TextUi<TextView>{
    public StampUi(TextView view, ValueGetter<String> getter, final AuthorId authorId,
                   final ReviewLauncher launcher, final boolean clickable) {
        super(view, getter);

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickable) launcher.launchReviewsList(authorId);
            }
        });

        getView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(clickable) launcher.launchReviewsList(authorId);
                return true;
            }
        });
    }
}
