package forestry.core.gui.elements;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.Gui;

import forestry.api.core.IGuiElement;

public class GuiElement extends Gui implements IGuiElement {
	protected final int xPos;
	protected final int yPos;
	protected final List<String> tooltip = new ArrayList<>();
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected int width;
	protected int height;

	public GuiElement(int width, int height) {
		this(0, 0, width, height);
	}

	public GuiElement(int xPos, int yPos, int width, int height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}

	@Override
	public int getX() {
		return xPos + xOffset;
	}

	@Override
	public int getY() {
		return yPos + yOffset;
	}

	@Override
	public int getOriginalX() {
		return xPos;
	}

	@Override
	public int getOriginalY() {
		return yPos;
	}

	@Override
	public void draw(int startX, int startY) {
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
		return false;
	}

	@Override
	public boolean mouseReleased(int x, int y, int mouseEvent) {
		return false;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	@Override
	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		int xPos = getX();
		int yPos = getY();
		return mouseX >= xPos && mouseX <= xPos + getWidth() && mouseY >= yPos && mouseY <= yPos + getHeight();
	}

	@Nullable
	@Override
	public IGuiElement getParent() {
		return null;
	}

	@Override
	public void setParent(@Nullable IGuiElement parent) {

	}

	@Override
	public List<String> getToolTip(int mouseX, int mouseY) {
		return tooltip;
	}

	@Override
	public IGuiElement addTooltip(String line) {
		tooltip.add(line);
		return this;
	}

	@Override
	public IGuiElement addTooltip(Collection<String> lines) {
		tooltip.addAll(lines);
		return this;
	}

	@Override
	public void clearTooltip() {
		tooltip.clear();
	}

	@Override
	public List<String> getTooltip() {
		return tooltip;
	}
}
