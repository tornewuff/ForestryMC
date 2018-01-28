package forestry.book.pages;

import java.util.Collection;

import net.minecraft.client.gui.GuiScreen;

import forestry.api.core.IGuiElementLayout;

public class BookPageElements extends BookPage {
	private IGuiElementLayout layout;

	public BookPageElements(IGuiElementLayout layout) {
		this.layout = layout;
	}

	@Override
	public void draw(GuiScreen gui, int startX, int startY, int guiLeft, int guiTop) {
		layout.draw(startX, startY);
	}

	@Override
	public Collection<String> getTooltip(int mouseX, int mouseY) {
		if (layout.isMouseOver(mouseX, mouseY)) {
			return layout.getToolTip(mouseX, mouseY);
		}
		return super.getTooltip(mouseX, mouseY);
	}
}
