package forestry.core.gui.elements;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import forestry.api.core.GuiElementAlignment;
import forestry.api.core.IGuiElement;
import forestry.api.core.IGuiElementContainer;
import forestry.core.gui.Drawable;

public abstract class GuiElementContainer extends GuiElement implements IGuiElementContainer {
	protected final List<IGuiElement> elements = new ArrayList<>();

	protected GuiElementContainer(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
	}

	public <E extends IGuiElement> E add(E element) {
		elements.add(element);
		return element;
	}

	public <E extends IGuiElement> E remove(E element) {
		elements.remove(element);
		return element;
	}

	public void clear() {
		elements.clear();
	}

	@Override
	public List<IGuiElement> getElements() {
		return elements;
	}

	@Override
	public void draw(int startX, int startY) {
		startX += getX();
		startY += getY();
		for (IGuiElement element : elements) {
			element.draw(startX, startY);
		}
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.mouseClicked(mouseX, mouseY, mouseEvent)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int mouseEvent) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.mouseReleased(mouseX, mouseY, mouseEvent)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getToolTip(int mouseX, int mouseY) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY)) {
				List<String> tooltip = element.getToolTip(mouseX, mouseY);
				if (!tooltip.isEmpty()) {
					return tooltip;
				}
			}
		}
		return tooltip;
	}

	@Nullable
	@Override
	public IGuiElement getElementAtPosition(int mouseX, int mouseY) {
		mouseX -= getX();
		mouseY -= getY();
		for (IGuiElement element : elements) {
			if (element.isMouseOver(mouseX, mouseY)) {
				return element;
			}
		}
		return null;
	}

	public void drawable(Drawable drawable) {
		add(new GuiElementDrawable(drawable));
	}

	public void drawable(int x, int y, Drawable drawable) {
		add(new GuiElementDrawable(x, y, drawable));
	}

	@Override
	public IGuiElement item(int xPos, int yPos, ItemStack itemStack) {
		IGuiElement element = new GuiElementItemStack(xPos, yPos, itemStack);
		add(element);
		return element;
	}

	@Override
	public IGuiElement text(String text) {
		return text(0, text, 0xFFFFFF);
	}

	@Override
	public IGuiElement text(String text, int color) {
		return text(0, text, color);
	}

	@Override
	public IGuiElement text(String text, GuiElementAlignment align) {
		return text(text, align, 0xFFFFFF);
	}

	@Override
	public IGuiElement text(String text, GuiElementAlignment align, int color) {
		return text(0, text, align, color);
	}

	public IGuiElement text(int x, String text, int color) {
		return text(x, text, GuiElementAlignment.LEFT, color);
	}

	public IGuiElement text(int x, String text, GuiElementAlignment align, int color) {
		return text(x, 12, text, align, color);
	}

	@Override
	public IGuiElement text(int x, int height, String text, GuiElementAlignment align, int color) {
		return text(x, height, text, align, color, false);
	}

	@Override
	public IGuiElement text(int x, int y, int width, int height, String text) {
		return add(new GuiElementText(x, y, width, height, text, GuiElementAlignment.LEFT, 0xFFFFFF, false));
	}

	@Override
	public IGuiElement text(int x, int height, String text, GuiElementAlignment align, int color, boolean unicode) {
		return add(new GuiElementText(x, 0, getWidth(), height, text, align, color, unicode));
	}

	@Override
	public IGuiElement centerElementX(IGuiElement element) {
		element.setXOffset((getWidth() - element.getWidth()) / 2);
		return element;
	}

	@Override
	public IGuiElement centerElementY(IGuiElement element) {
		element.setXOffset((getHeight() - element.getHeight()) / 2);
		return element;
	}

	@Override
	public IGuiElement centerElement(IGuiElement element) {
		centerElementX(element);
		centerElementY(element);
		return element;
	}

	@Override
	public GuiElementLayout vertical(int xPos, int yPos, int width) {
		return add(new GuiElementVertical(xPos, yPos, width));
	}

	@Override
	public GuiElementLayout vertical(int width) {
		return add(new GuiElementVertical(0, 0, width));
	}

	@Override
	public GuiElementLayout horizontal(int xPos, int yPos, int height) {
		return add(new GuiElementHorizontal(xPos, yPos, height));
	}

	@Override
	public GuiElementLayout horizontal(int height) {
		return add(new GuiElementHorizontal(0, 0, height));
	}

	@Override
	public GuiElementContainer panel(int xPos, int yPos, int width, int height) {
		return add(new GuiElementPanel(xPos, yPos, width, height));
	}

	@Override
	public GuiElementContainer panel(int width, int height) {
		return add(new GuiElementPanel(0, 0, width, height));
	}
}
