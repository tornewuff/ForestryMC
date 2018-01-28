package forestry.core.gui.elements;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class MultiblockData {
	private final IBlockState[][][] blockStates;
	public final int width;
	public final int height;
	public final int length;
	private final int maxBlockIndex;
	private int blockIndex;

	public MultiblockData(IBlockState[][][] blockStates, int width, int height, int length) {
		this.blockStates = blockStates;
		this.width = width;
		this.height = height;
		this.length = length;
		this.maxBlockIndex = blockIndex = width * height * length;
	}

	public void reset() {
		blockIndex = maxBlockIndex;
	}

	public void step() {
		int start = blockIndex;
		do {
			if (++blockIndex >= maxBlockIndex) {
				blockIndex = 0;
			}
		}
		while (isEmpty(blockIndex) && blockIndex != start);
	}

	public void setShowLayer(int layer) {
		//showLayer = layer;
		blockIndex = (layer + 1) * (length * width) - 1;
	}

	public boolean canStep() {
		int index = blockIndex;
		do {
			if (++index >= maxBlockIndex) {
				return false;
			}
		} while (isEmpty(index));
		return true;
	}

	private boolean isEmpty(int index) {
		int y = index / (length * width);
		int r = index % (length * width);
		int x = r / width;
		int z = r % width;

		IBlockState state = blockStates[y][x][z];
		return state == null || state.getBlock() == Blocks.AIR;
	}

	public IBlockState[][][] getBlockStates() {
		return blockStates;
	}

	public int getLimiter() {
		return blockIndex;
	}
}
