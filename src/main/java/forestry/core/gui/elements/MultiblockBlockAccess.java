package forestry.core.gui.elements;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class MultiblockBlockAccess implements IBlockAccess {
	private final IBlockState[][][] blockStates;
	private final MultiblockData data;

	public MultiblockBlockAccess(MultiblockData data) {
		this.blockStates = data.getBlockStates();
		this.data = data;
	}

	@Nullable
	@Override
	public TileEntity getTileEntity(BlockPos pos) {
		return null;
	}

	@Override
	public int getCombinedLight(BlockPos pos, int lightValue) {
		return 15 << 20 | 15 << 4;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (y >= 0 && y < blockStates.length) {
			if (x >= 0 && x < blockStates[y].length) {
				if (z >= 0 && z < blockStates[y][x].length) {
					int index = y * (data.length * data.width) + x * data.width + z;
					if (index <= data.getLimiter()) {
						return blockStates[y][x][z] != null ? blockStates[y][x][z] : Blocks.AIR.getDefaultState();
					}
				}
			}
		}
		return Blocks.AIR.getDefaultState();
	}

	@Override
	public boolean isAirBlock(BlockPos pos) {
		return getBlockState(pos).getBlock() == Blocks.AIR;
	}

	@Override
	public Biome getBiome(BlockPos pos) {
		return null;
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction) {
		return 0;
	}

	@Override
	public WorldType getWorldType() {
		return null;
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
		return false;
	}
}
