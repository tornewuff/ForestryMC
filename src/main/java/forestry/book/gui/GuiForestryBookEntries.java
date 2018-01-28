package forestry.book.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.GuiButton;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.book.IBookCategory;
import forestry.api.book.IBookEntry;
import forestry.api.book.IForesterBook;
import forestry.api.core.GuiElementAlignment;
import forestry.api.core.IGuiElementLayout;
import forestry.book.gui.buttons.GuiButtonBack;
import forestry.book.gui.buttons.GuiButtonEntry;
import forestry.book.gui.buttons.GuiButtonPage;
import forestry.core.gui.Drawable;
import forestry.core.gui.elements.GuiElementContainer;
import forestry.core.gui.elements.GuiElementLayout;
import forestry.core.gui.elements.GuiElementManager;

public class GuiForestryBookEntries extends GuiForesterBook {
	private final IBookCategory category;
	private final GuiElementManager<GuiForesterBook> elementManager;
	private int entryIndex = 0;

	public GuiForestryBookEntries(IForesterBook book, IBookCategory category) {
		super(book);
		this.category = category;
		this.elementManager = new GuiElementManager<>(this);
	}

	@Override
	public void initGui() {
		super.initGui();
		int offset = entryIndex * 12;
		int yOffset = 0;
		List<IBookEntry> entries = new ArrayList<>(category.getEntries());
		//entries.sort(ENTRY_COMPARATOR);
		for(IBookEntry entry : entries.subList(offset, entries.size() > offset + 12 ? offset + 12 : entries.size())){
			addButton(new GuiButtonEntry(yOffset, guiLeft + LEFT_PAGE_START_X, guiTop + LEFT_PAGE_START_Y + yOffset * (fontRenderer.FONT_HEIGHT + 2), entry));
			yOffset++;
		}
		elementManager.clear();
		IBeeRoot beeRoot = BeeManager.beeRoot;
		IBeeMutation beeMutation = beeRoot.getMutations(true).get(0);
		GuiElementLayout mutationElement = elementManager.vertical(RIGHT_PAGE_START_X, RIGHT_PAGE_START_Y, 108);
		mutationElement.text("Bee Breeding", GuiElementAlignment.CENTER, 0);
		GuiElementLayout background = mutationElement.horizontal(18).setDistance(3);
		Drawable slot = new Drawable(TEXTURE, 0, 223, 18, 18);
		Drawable plus = new Drawable(TEXTURE, 0, 241, 15, 15);
		Drawable arrow = new Drawable(TEXTURE, 15, 241, 18, 15);
		background.drawable(slot);
		background.drawable(0, 2, plus);
		background.drawable(slot);
		GuiElementContainer conditionArrow = background.panel(28, 18);
		Collection<String> conditions = beeMutation.getSpecialConditions();
		String text;
		if(!conditions.isEmpty()){
			text = String.format("[%.0f%%]", beeMutation.getBaseChance());
		}else{
			text = 	String.format("%.0f%%", beeMutation.getBaseChance());
		}
		conditionArrow.text(text, GuiElementAlignment.CENTER, 0);
		conditionArrow.addTooltip(conditions);
		conditionArrow.drawable(5, 6, arrow);
		background.drawable(slot);
		IGuiElementLayout foreground = mutationElement.horizontal(18).setDistance(23);
		foreground.item(1, -17, beeRoot.getMemberStack(beeMutation.getAllele0(), EnumBeeType.PRINCESS));
		foreground.item(1, -17, beeRoot.getMemberStack(beeMutation.getAllele1(), EnumBeeType.DRONE));
		foreground.item(14, -17, beeRoot.getMemberStack(beeMutation.getTemplate(), EnumBeeType.QUEEN));
	}

	@Override
	protected void initButtons(GuiButtonPage leftButton, GuiButtonPage rightButton, GuiButtonBack backButton) {
		leftButton.visible = entryIndex > 0;
		rightButton.visible = category.getEntries().size() > (entryIndex + 1) * 12;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button instanceof GuiButtonPage){
			GuiButtonPage pageButton = (GuiButtonPage) button;
			if(pageButton.left){
				entryIndex--;
			}else{
				entryIndex++;
			}
			initGui();
		}else if(button instanceof GuiButtonBack){
			displayCategories();
		}else if(button instanceof GuiButtonEntry){
			GuiButtonEntry entry = (GuiButtonEntry) button;
			mc.displayGuiScreen(new GuiForestryBookPages(book, category, entry.entry));
		}
	}

	@Override
	protected List<String> getTooltip(int mouseX, int mouseY) {
		return elementManager.getToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawPages() {
		elementManager.draw(0, 0);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		elementManager.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		elementManager.mouseReleased(mouseX, mouseY, state);
	}

	private void displayCategories(){
		mc.displayGuiScreen(new GuiForestryBookCategories(book));
	}

	@Override
	protected String getTitle() {
		return category.getLocalizedName();
	}
}
