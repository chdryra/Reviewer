/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Fragments;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.TitleDecorator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class Styles {
    public static class TitleDecorators {
        public static final TitleDecorator NONE = TitleDecorator.NO_DECOR;
        public static final TitleDecorator OPTION_BUTTON = new TitleDecorator("/", "",
                TitleDecorator.Style.START);
        public static final TitleDecorator DONE_BUTTON = new TitleDecorator("-", " ",
                TitleDecorator.Style.WRAP);
    }
}
