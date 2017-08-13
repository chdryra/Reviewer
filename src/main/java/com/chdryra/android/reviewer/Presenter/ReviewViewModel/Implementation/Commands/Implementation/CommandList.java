/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class CommandList extends ArrayList<Command> implements Collection<Command> {
    private String mName;

    public CommandList() {
        this("");
    }

    public CommandList(String name) {
        super();
        mName = name;
    }

    public CommandList(@NonNull Collection<? extends Command> c) {
        super(c);
    }

    public CommandList(int initialCapacity) {
        super(initialCapacity);
    }

    public String getListName() {
        return mName;
    }

    public void setListName(String name) {
        mName = name;
    }

    public ArrayList<String> getCommandNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Command command : this) {
            names.add(command.getName());
        }

        return names;
    }

    public void execute(String name) {
        for (Command command : this) {
            if (command.getName().equals(name)) {
                command.execute();
                break;
            }
        }
    }
}
