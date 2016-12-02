/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Implementation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogOneButtonFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchOptionsCommand;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogOptions extends DialogOneButtonFragment implements
        LaunchableUi, Command.ExecutionListener {
    private static final String TAG = TagKeyGenerator.getTag(DialogOptions.class);
    private static final String OPTIONS = LaunchOptionsCommand.OPTIONS;
    private static final String SELECTED = LaunchOptionsCommand.CURRENTLY_SELECTED;

    private Button mSelected;

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    @Override
    public void onCommandExecuted(int requestCode) {
        dismiss();
    }

    @Override
    protected View createDialogUi() {
        LinearLayout view = newLinearLayout();

        OptionSelectListener listener = getTargetListenerOrThrow(OptionSelectListener.class);
        String selected = getSelected();
        for (final String option : getOptions()) {
            view.addView(newOptionButton(listener, option, selected));
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftButtonAction(ActionType.CANCEL);
        setDialogTitle(null);
        hideKeyboardOnLaunch();
    }

    private List<String> getOptions() {
        List<String> options = null;
        if (getArguments() != null) options = getArguments().getStringArrayList(OPTIONS);
        if (options == null) options = new ArrayList<>();

        return options;
    }

    @Nullable
    private String getSelected() {
        return getArguments() != null ? getArguments().getString(SELECTED) : null;
    }

    @NonNull
    private LinearLayout newLinearLayout() {
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        return layout;
    }

    @NonNull
    private Button newOptionButton(final OptionSelectListener listener, final String option,
                                   @Nullable String selected) {
        Button button = new Button(getActivity());
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        button.setText(option);
        if (option.equals(selected)) {
            mSelected = button;
            mSelected.setPressed(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSelected != null) mSelected.setPressed(false);
                listener.onOptionSelected(getTargetRequestCode(), option);
                dismiss();
            }
        });

        return button;
    }
}

