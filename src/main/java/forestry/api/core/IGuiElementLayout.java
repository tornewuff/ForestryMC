/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.core;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IGuiElementLayout extends IGuiElementContainer {
	/**
	 *
	 * @param distance
	 * @return
	 */
	IGuiElementLayout setDistance(int distance);

	/**
	 * @return The distance between the different elements of this layout.
	 */
	int getDistance();

	int getSize();
}
