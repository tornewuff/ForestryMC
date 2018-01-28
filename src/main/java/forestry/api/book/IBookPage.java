package forestry.api.book;

import java.util.Collection;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public interface IBookPage {

	void initPage(GuiScreen gui);

	void draw(GuiScreen gui, int startX, int startY, int guiLeft, int guiTop);

	void onButtonPressed(GuiScreen gui, GuiButton button);

	Collection<String> getTooltip(int mouseX, int mouseY);
}
