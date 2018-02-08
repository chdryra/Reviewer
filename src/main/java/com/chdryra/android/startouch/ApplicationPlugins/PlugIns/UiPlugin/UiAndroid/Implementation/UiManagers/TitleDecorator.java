/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers;


/**
 * Created by: Rizwan Choudrey
 * On: 08/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public class TitleDecorator {
    public static final TitleDecorator NO_DECOR = new TitleDecorator("", "", Style.WRAP);
    private final String mDecorator;
    private final String mSeparator;
    private final Style mStyle;

    public enum Style {START, END, WRAP}

    public TitleDecorator(String decorator, String separator, Style style) {
        mDecorator = decorator;
        mSeparator = separator;
        mStyle = style;
    }

    public String unDecorate(String title) {
        if (mDecorator.length() == 0) return title;

        while (isDecorated(title)) {
            title = remove(remove(title, mDecorator), mSeparator);
        }

        return title;
    }

    public String decorate(String title) {
        if (mDecorator.length() == 0) return title;
        String decorated = title;

        if (isStartOrWrap()) decorated = mDecorator + mSeparator + title;
        if (isEndOrWrap()) decorated = decorated + mSeparator + mDecorator;

        return decorated;
    }

    private boolean isStartOrWrap() {
        return mStyle == Style.START || mStyle == Style.WRAP;
    }

    private boolean isEndOrWrap() {
        return mStyle == Style.END || mStyle == Style.WRAP;
    }

    private boolean isDecorated(String value) {
        if (mDecorator.length() == 0) return false;

        if (isStartOrWrap()) return value.startsWith(mDecorator);

        return value.endsWith(mDecorator);
    }

    private String remove(String string, String decoration) {
        String result = string;
        if (isStartOrWrap() && string.startsWith(decoration)) {
            result = string.substring(decoration.length(), string.length());
        }

        if (isEndOrWrap() && result.endsWith(decoration)) {
            result = result.substring(0, result.length() - decoration.length());
        }

        return result;
    }
}
