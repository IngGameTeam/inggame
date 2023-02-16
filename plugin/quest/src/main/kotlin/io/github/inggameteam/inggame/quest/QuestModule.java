package io.github.inggameteam.inggame.quest;

import io.github.inggameteam.inggame.component.ClassOfKt;
import io.github.inggameteam.inggame.component.event.ComponentLoadEvent;
import io.github.inggameteam.inggame.component.wrapper.Wrapper;
import io.github.inggameteam.inggame.utils.IngGamePlugin;
import io.github.inggameteam.inggame.utils.Listener;
import lombok.experimental.ExtensionMethod;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

@ExtensionMethod(ClassOfKt.class)
public class QuestModule extends Listener {

    public QuestModule(@NotNull IngGamePlugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onRegister(ComponentLoadEvent event) {
        event.registerClass(
                Quest.class

        );
        event.registerClass(module -> {
            module.classOf(s -> new QuestImp(s.get(Wrapper.class)));
            return null;
        });
    }


}
