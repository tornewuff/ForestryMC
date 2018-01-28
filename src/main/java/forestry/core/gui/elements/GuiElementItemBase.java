/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.gui.elements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.gui.GuiUtil;

public abstract class GuiElementItemBase extends GuiElement {

	public GuiElementItemBase(int xPos, int yPos) {
		super(xPos, yPos, 16, 16);
	}

	@Override
	public void draw(int startX, int startY) {
		ItemStack itemStack = getItemStack();
		if (!itemStack.isEmpty()) {
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableRescaleNormal();
			GuiUtil.drawItemStack(Minecraft.getMinecraft().fontRenderer, itemStack, getX() + startX, getY() + startY);
			RenderHelper.disableStandardItemLighting();
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<String> getToolTip(int mouseX, int mouseY) {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer player = minecraft.player;
		ItemStack itemStack = getItemStack();
		List<String> tip = new ArrayList<>();
		if (!itemStack.isEmpty()) {
			List<String> tooltip = itemStack.getTooltip(player, minecraft.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
			for (int i = 0; i < tooltip.size(); ++i) {
				if (i == 0) {
					tooltip.set(i, itemStack.getRarity().rarityColor + tooltip.get(i));
				} else {
					tooltip.set(i, TextFormatting.GRAY + tooltip.get(i));
				}
			}
			tip.addAll(tooltip);
		}
		return tip;
	}

	protected abstract ItemStack getItemStack();
}
