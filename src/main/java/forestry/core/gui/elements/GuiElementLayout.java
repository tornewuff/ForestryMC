package forestry.core.gui.elements;

import forestry.api.core.IGuiElementLayout;

public abstract class GuiElementLayout extends GuiElementContainer implements IGuiElementLayout {
	/* The distance between the different elements of this group. */
	public int distance;

	public GuiElementLayout(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
	}

	public GuiElementLayout setDistance(int distance) {
		this.distance = distance;
		return this;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public int getSize() {
		return elements.size();
	}
}
