package forestry.core.gui.elements;

import forestry.api.core.IGuiElement;

public class GuiElementVertical extends GuiElementLayout {
	public GuiElementVertical(int width) {
		this(0, 0, width);
	}

	public GuiElementVertical(int xPos, int yPos, int width) {
		super(xPos, yPos, width, 0);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public <E extends IGuiElement> E add(E element) {
		elements.add(element);
		element.setYOffset(height);
		height += element.getHeight() + distance;
		return element;
	}

	public <E extends IGuiElement> E remove(E element) {
		elements.remove(element);
		height -= element.getHeight() + distance;
		element.setYOffset(0);
		return element;
	}

	@Override
	public int getWidth() {
		if (width > 0) {
			return width;
		}
		int width = 0;
		for (IGuiElement element : elements) {
			int elementWidth = element.getWidth();
			if (elementWidth > width) {
				width = elementWidth;
			}
		}
		return width;
	}
}
