package forestry.core.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import forestry.api.core.GuiElementAlignment;

public class GuiElementText extends GuiElement {
	public static final FontRenderer FONT_RENDERER = Minecraft.getMinecraft().fontRenderer;

	protected final String text;
	protected final int color;
	protected final GuiElementAlignment align;
	protected final boolean unicode;

	public GuiElementText(int width, int height, String text, GuiElementAlignment align, int color, boolean unicode) {
		this(0, 0, width, height, text, align, color, unicode);
	}

	public GuiElementText(int xPos, int yPos, int width, int height, String text, GuiElementAlignment align, int color, boolean unicode) {
		super(xPos, yPos, width, height);
		this.text = text;
		this.align = align;
		this.color = color;
		this.unicode = unicode;
		if (width < 0) {
			boolean uni = FONT_RENDERER.getUnicodeFlag();
			FONT_RENDERER.setUnicodeFlag(this.unicode);
			this.width = FONT_RENDERER.getStringWidth(text);
			FONT_RENDERER.setUnicodeFlag(uni);
		}
	}

	public String getText() {
		return text;
	}

	@Override
	public void draw(int startX, int startY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		boolean unicode = FONT_RENDERER.getUnicodeFlag();
		FONT_RENDERER.setUnicodeFlag(this.unicode);
		FONT_RENDERER.drawString(text, getX() + getOffset() + startX, getY() + startY, color);
		FONT_RENDERER.setUnicodeFlag(unicode);
	}

	public int getOffset() {
		switch (align) {
			case RIGHT:
				return width - FONT_RENDERER.getStringWidth(text);
			case CENTER:
				return (width - FONT_RENDERER.getStringWidth(text)) / 2;
			default:
				return 0;
		}
	}
}
