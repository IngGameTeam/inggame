import io.github.bruce0203.gui.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class GuiTest {
    {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Test");
        ItemStack itemStack = new ItemStack(Material.APPLE);
        Gui.frame(plugin, 3, "Test Title! Click Apple!")
                .slot(1, 4, itemStack, event -> {
                    Player player = (Player) event.getWhoClicked();
                    player.sendMessage("You Clicked Apple!");
                    player.closeInventory();
                });
    }
}
