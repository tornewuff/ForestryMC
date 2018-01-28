package forestry.core.gui.elements;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;

import forestry.api.core.IGuiElement;
import forestry.core.gui.IGuiSizable;

public class GuiElementManager<G extends GuiScreen & IGuiSizable> extends GuiElementContainer {
	private final G gui;

	public GuiElementManager(G gui) {
		super(0, 0, 0, 0);
		this.gui = gui;
	}

	@Override
	public int getX() {
		return gui.getGuiLeft();
	}

	@Override
	public int getY() {
		return gui.getGuiTop();
	}

	@Override
	public int getOriginalX() {
		return gui.getGuiLeft();
	}

	@Override
	public int getOriginalY() {
		return gui.getGuiTop();
	}

	@Override
	public int getWidth() {
		return gui.getSizeX();
	}

	@Override
	public int getHeight() {
		return gui.getSizeY();
	}

	@Override
	public void setXOffset(int xOffset) {
	}

	@Override
	public void setYOffset(int yOffset) {
	}

	@Nullable
	@Override
	public IGuiElement getParent() {
		return null;
	}

	@Override
	public void setParent(@Nullable IGuiElement parent) {
	}
}
