package forestry.core.gui.elements;

import java.util.Collection;
import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import forestry.api.core.GuiElementAlignment;
import forestry.api.core.IGuiElement;
import forestry.api.core.IGuiElementHelper;
import forestry.api.core.IGuiElementLayout;
import forestry.api.core.IGuiElementLayoutHelper;
import forestry.api.core.IGuiElementLayoutHelper.LayoutFactory;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.gui.Drawable;
import forestry.core.render.ColourProperties;

public class GuiElementHelper implements IGuiElementHelper {
	private final IGuiElementLayout parent;
	private final int defaultColor;

	public GuiElementHelper(IGuiElementLayout parent) {
		this.parent = parent;
		this.defaultColor = ColourProperties.INSTANCE.get("gui.screen");
	}

	@Override
	public GuiElementLayoutHelper layoutHelper(LayoutFactory layoutFactory, int width, int height) {
		return new GuiElementLayoutHelper(layoutFactory, width, height, this);
	}

	public void add(IGuiElementLayoutHelper groupHelper) {
		groupHelper.layouts().forEach(this::add);
	}

	@Override
	public <E extends IGuiElement> E add(E element) {
		parent.add(element);
		return element;
	}

	@Override
	public void addItem(int x, ItemStack itemStack) {
		parent.add(new GuiElementItemStack(x, 0, itemStack));
	}

	@Override
	public final void addAllele(String chromosomeName, IIndividual individual, IChromosomeType chromosome, boolean active) {
		addAllele(chromosomeName, IAllele::getAlleleName, individual, chromosome, active);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <A extends IAllele> void addAllele(String chromosomeName, Function<A, String> toString, IIndividual individual, IChromosomeType chromosome, boolean active) {
		A allele;
		if (active) {
			allele = (A) individual.getGenome().getActiveAllele(chromosome);
		} else {
			allele = (A) individual.getGenome().getInactiveAllele(chromosome);
		}
		addText(TextFormatting.UNDERLINE + chromosomeName, GuiElementAlignment.CENTER);
		addText(toString.apply(allele), GuiElementAlignment.CENTER, factory().getColorCoding(allele.isDominant()));
	}

	@Override
	public void addMutation(int x, int y, int width, int height, IMutation mutation, IAllele species, IBreedingTracker breedingTracker) {
		IGuiElement element = factory().createMutation(x, y, width, height, mutation, species, breedingTracker);
		if (element == null) {
			return;
		}
		add(element);
	}

	@Override
	public void addMutationResultant(int x, int y, int width, int height, IMutation mutation, IBreedingTracker breedingTracker) {
		IGuiElement element = factory().createMutationResultant(x, y, width, height, mutation, breedingTracker);
		if (element == null) {
			return;
		}
		add(element);
	}

	public void addBookMutation(int x, int y, IMutation mutation, Drawable slot, Drawable plus, Drawable arrow) {
		ISpeciesRoot root = mutation.getRoot();
		GuiElementLayout mutationElement = factory().createVertical(x, y, 108);
		mutationElement.text("Bee Breeding", GuiElementAlignment.CENTER, 0);
		//
		GuiElementLayout background = mutationElement.horizontal(18).setDistance(3);
		background.drawable(slot);
		background.drawable(0, 2, plus);
		background.drawable(slot);
		GuiElementContainer conditionArrow = background.panel(28, 18);
		Collection<String> conditions = mutation.getSpecialConditions();
		String text;
		if (!conditions.isEmpty()) {
			text = String.format("[%.0f%%]", mutation.getBaseChance());
		} else {
			text = String.format("%.0f%%", mutation.getBaseChance());
		}
		conditionArrow.text(text, GuiElementAlignment.CENTER, 0);
		conditionArrow.addTooltip(conditions);
		conditionArrow.drawable(5, 6, arrow);
		background.drawable(slot);
		//
		IGuiElementLayout foreground = mutationElement.horizontal(18).setDistance(23);
		foreground.item(1, -17, root.getMemberStack(mutation.getAllele0(), root.getTypeForMutation(0)));
		foreground.item(1, -17, root.getMemberStack(mutation.getAllele1(), root.getTypeForMutation(1)));
		foreground.item(14, -17, root.getMemberStack(mutation.getTemplate(), root.getTypeForMutation(2)));
	}

	@Override
	public void addFertilityInfo(IAlleleInteger fertilityAllele, int x, int texOffset) {
		add(centerElementX(factory().createFertilityInfo(fertilityAllele, x, texOffset)));
	}

	@Override
	public void addToleranceInfo(IAlleleTolerance toleranceAllele, IAlleleSpecies species, String text) {
		add(centerElementX(factory().createToleranceInfo(toleranceAllele, species, text)));
	}

	@Override
	public void addText(String text) {
		addText(0, text, defaultColor);
	}

	@Override
	public void addText(String text, boolean unicode) {
		addText(0, 12, text, GuiElementAlignment.LEFT, defaultColor, unicode);
	}

	public void addText(String text, int color) {
		addText(0, text, color);
	}

	@Override
	public void addText(String text, GuiElementAlignment align) {
		addText(text, align, defaultColor);
	}

	@Override
	public void addText(String text, GuiElementAlignment align, int color) {
		addText(0, text, align, color);
	}

	public void addText(int x, String text, int color) {
		addText(x, text, GuiElementAlignment.LEFT, color);
	}

	public void addText(int x, String text, GuiElementAlignment align, int color) {
		addText(x, 12, text, align, color);
	}

	public void addText(int x, int height, String text, GuiElementAlignment align, int color) {
		addText(x, height, text, align, color, false);
	}

	@Override
	public void addText(int x, int height, String text, GuiElementAlignment align, int color, boolean unicode) {
		parent.add(new GuiElementText(x, 0, parent.getWidth(), height, text, align, color, unicode));
	}

	@Override
	public IGuiElement centerElementX(IGuiElement element) {
		element.setXOffset((parent.getWidth() - element.getWidth()) / 2);
		return element;
	}

	@Override
	public IGuiElement centerElementY(IGuiElement element) {
		element.setXOffset((parent.getHeight() - element.getHeight()) / 2);
		return element;
	}

	@Override
	public IGuiElement centerElement(IGuiElement element) {
		centerElementX(element);
		centerElementY(element);
		return element;
	}

	@Override
	public IGuiElementLayout getParent() {
		return parent;
	}

	@Override
	public int getDefaultColor() {
		return defaultColor;
	}

	@Override
	public GuiElementFactory factory() {
		return GuiElementFactory.INSTANCE;
	}
}
