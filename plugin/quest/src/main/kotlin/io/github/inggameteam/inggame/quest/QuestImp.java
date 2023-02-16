package io.github.inggameteam.inggame.quest;

import io.github.inggameteam.inggame.component.wrapper.SimpleWrapper;
import io.github.inggameteam.inggame.component.wrapper.Wrapper;
import org.jetbrains.annotations.NotNull;
import org.koin.core.scope.Scope;

public class QuestImp extends SimpleWrapper implements Quest{
    public QuestImp(@NotNull Wrapper wrapper) {
        super(wrapper);
    }

    public QuestImp(Scope scope) {
        super(null);
    }
}
