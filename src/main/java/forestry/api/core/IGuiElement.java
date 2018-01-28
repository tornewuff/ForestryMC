/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.core;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IGuiElement {
	/**
	 * @return the x position of this element relative to the position of the top widget.
	 */
	int getX();

	/**
	 * @return the y position of this element relative to the position of the top widget.
	 */
	int getY();

	/**
	 * @return the x position of this element without the offset.
	 */
	int getOriginalX();

	/**
	 * @return the y position of this element without the offset.
	 */
	int getOriginalY();

	/**
	 * @return the size of the element on the x-axis.
	 */
	int getWidth();

	/**
	 * @return the size of the element on the y-axis.
	 */
	int getHeight();

	/**
	 * Sets the x-axis offset to the original x-axis position.
	 */
	void setXOffset(int xOffset);

	/**
	 * Sets the y-axis offset to the original y-axis position.
	 */
	void setYOffset(int yOffset);

	/**
	 * @return the element that contains this element and handles all events.
	 */
	@Nullable
	IGuiElement getParent();

	void setParent(@Nullable IGuiElement parent);

	/**
	 * Draws this element.
	 */
	void draw(int startX, int startY);

	boolean mouseClicked(int mouseX, int mouseY, int mouseEvent);

	boolean mouseReleased(int mouseX, int mouseY, int mouseEvent);

	IGuiElement addTooltip(String line);

	IGuiElement addTooltip(Collection<String> lines);

	void clearTooltip();

	/**
	 * Returns the tooltip that this element provides at the given mouse position,
	 */
	@SideOnly(Side.CLIENT)
	default List<String> getToolTip(int mouseX, int mouseY){
		return Collections.emptyList();
	}

	List<String> getTooltip();

	/**
	 * @return True if the mouse is currently over the element.
	 */
	@SideOnly(Side.CLIENT)
	boolean isMouseOver(int mouseX, int mouseY);
}
