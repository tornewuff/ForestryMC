package forestry.api.core;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

public interface IGuiElementContainer extends IGuiElement {
	/**
	 * Adds a element to this layout.
	 */
	<E extends IGuiElement> E add(E element);

	/**
	 * Removes a element from this layout.
	 */
	<E extends IGuiElement> E remove(E element);

	default IGuiElementContainer add(IGuiElement... elements){
		for(IGuiElement element : elements){
			add(element);
		}
		return this;
	}

	default IGuiElementContainer remove(IGuiElement... elements){
		for(IGuiElement element : elements){
			remove(element);
		}
		return this;
	}

	default IGuiElementContainer add(Collection<IGuiElement> elements){
		elements.forEach(this::add);
		return this;
	}

	default IGuiElementContainer remove(Collection<IGuiElement> elements){
		elements.forEach(this::remove);
		return this;
	}

	@Nullable
	IGuiElement getElementAtPosition(int mouseX, int mouseY);

	/**
	 * @return All elements that this layout contains.
	 */
	List<IGuiElement> getElements();

	IGuiElement item(int xPos, int yPos, ItemStack itemStack);

	default IGuiElement item(ItemStack itemStack){
		return item(0, 0, itemStack);
	}

	/**
	 * Adds a text element with the default color,the align {@link GuiElementAlignment#LEFT} and the height 12.
	 */
	IGuiElement text(String text);

	/**
	 * Adds a text element with the align {@link GuiElementAlignment#LEFT} and the height 12.
	 */
	IGuiElement text(String text, int color);

	/**
	 * Adds a text element with the default color and the height 12.
	 */
	IGuiElement text(String text, GuiElementAlignment align);

	/**
	 * Adds a text element with the height 12.
	 */
	IGuiElement text(String text, GuiElementAlignment align, int color);

	/**
	 * Adds a text element.
	 */
	IGuiElement text(int x, int height, String text, GuiElementAlignment align, int color);

	IGuiElement text(int x, int y, int width, int height, String text);

	/**
	 * Adds a text element.
	 */
	IGuiElement text(int x, int height, String text, GuiElementAlignment align, int color, boolean unicode);

	/**
	 * Sets the x offset of the given element to the center of the parent.
	 */
	IGuiElement centerElementX(IGuiElement element);

	/**
	 * Sets the z offset of the given element to the center of the parent.
	 */
	IGuiElement centerElementY(IGuiElement element);

	/**
	 * Sets theoffset of the given element to the center of the parent.
	 */
	IGuiElement centerElement(IGuiElement element);

	default IGuiElementLayout vertical(int width){
		return vertical(0, 0, width);
	}

	IGuiElementLayout vertical(int xPos, int yPos, int width);

	IGuiElementLayout horizontal(int xPos, int yPos, int height);

	default IGuiElementLayout horizontal( int height){
		return horizontal(0, 0, height);
	}

	IGuiElementContainer panel(int xPos, int yPos, int width, int height);

	default IGuiElementContainer panel(int width, int height){
		return panel(0, 0, width, height);
	}
}
