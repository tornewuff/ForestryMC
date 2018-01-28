package forestry.core.gui.elements;

import forestry.core.gui.Drawable;

public class GuiElementDrawable extends GuiElement {
	private final Drawable drawable;

	public GuiElementDrawable(Drawable drawable) {
		this(0, 0, drawable);
	}

	public GuiElementDrawable(int xPos, int yPos, Drawable drawable) {
		super(xPos, yPos, drawable.uWidth, drawable.vHeight);
		this.drawable = drawable;
	}

	public GuiElementDrawable(int xPos, int yPos, int width, int height, Drawable drawable) {
		super(xPos, yPos, width, height);
		this.drawable = drawable;
	}

	@Override
	public void draw(int startX, int startY) {
		drawable.draw(getX() + startX, getY() + startY, width, height);
	}
}
