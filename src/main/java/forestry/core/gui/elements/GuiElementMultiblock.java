package forestry.core.gui.elements;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;

import forestry.api.multiblock.IMultiblockBlueprint;
import org.lwjgl.opengl.GL11;

public class GuiElementMultiblock extends GuiElement {
	private final MultiblockData data;
	private final MultiblockBlockAccess blockAccess;
	private boolean canTick = true;
	private int tick = 0;
	private int fullStructureSteps = 5;

	public GuiElementMultiblock(int xPos, int yPos, IMultiblockBlueprint bluePrint) {
		super(xPos, yPos, 100, 1000);
		this.data = new MultiblockData(bluePrint.getBlockStates(), bluePrint.getXSize(), bluePrint.getYSize(), bluePrint.getZSize());
		this.blockAccess = new MultiblockBlockAccess(data);
	}

	@Override
	public void draw(int startX, int startY) {
		super.draw(startX, startY);
		if (canTick) {
			if (++tick % 20 == 0) {
				if (data.canStep() || ++fullStructureSteps >= 5) {
					data.step();
					fullStructureSteps = 0;
				}
			}
		} else {
			data.reset();
		}

		int structureLength = data.length;
		int structureWidth = data.width;
		int structureHeight = data.height;

		int xHalf = (structureWidth * 5 - structureLength * 5);
		int yOffPartial = (structureHeight - 1) * 16 + structureWidth * 8 + structureLength * 8;
		int yOffTotal = Math.max(52, yOffPartial + 16);

		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		final BlockRendererDispatcher blockRender = Minecraft.getMinecraft().getBlockRendererDispatcher();

		float f = (float) Math.sqrt(structureHeight * structureHeight + structureWidth * structureWidth + structureLength * structureLength);
		yOffTotal = 10 + Math.max(10 + (structureHeight > 1 ? 36 : 0), (int) (f * 50.0F));
		//GlStateManager.translate(x + 60, y + 10 + f / 2 * scale, Math.max(structureHeight, Math.max(structureWidth, structureLength)));
		GlStateManager.translate(getX() + startX + 108 / 2 + structureWidth / 2, getY() + startY + 165 / 2 + (float) Math.sqrt(structureHeight * structureHeight + structureWidth * structureWidth + structureLength * structureLength) / 2, Math.max(structureHeight, Math.max(structureWidth, structureLength)));
		// todo: translate where it actually needs to be and to counter z-layer of the book
		GlStateManager.scale(20.0F, -20.0F, 1);
		GlStateManager.rotate(25, 1, 0, 0);
		GlStateManager.rotate(-45, 0, 1, 0);

		GlStateManager.translate((float) structureLength / -2f, (float) structureHeight / -2f, (float) structureWidth / -2f);

		GlStateManager.disableLighting();

		if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
		} else {
			GlStateManager.shadeModel(GL11.GL_FLAT);
		}

		if (structureWidth % 2 == 1) {
			//GlStateManager.translate(-.5f, 0, 0);
		}
		int iterator = 0;

		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		for (int h = 0; h < data.height; h++) {
			for (int l = 0; l < data.length; l++) {
				for (int w = 0; w < data.width; w++) {
					BlockPos pos = new BlockPos(l, h, w);
					if (!blockAccess.isAirBlock(pos)) {
						IBlockState state = blockAccess.getBlockState(pos);
						Tessellator tessellator = Tessellator.getInstance();
						BufferBuilder buffer = tessellator.getBuffer();
						buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
						blockRender.renderBlock(state, pos, blockAccess, buffer);
						tessellator.draw();
					}
				}
			}
		}
		//			GL11.glTranslated(0, 0, -i);
		GlStateManager.popMatrix();

		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.enableBlend();
		RenderHelper.disableStandardItemLighting();
	}
}
