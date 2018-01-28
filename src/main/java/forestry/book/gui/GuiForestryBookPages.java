package forestry.book.gui;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;

import forestry.api.book.IBookCategory;
import forestry.api.book.IBookEntry;
import forestry.api.book.IBookPage;
import forestry.api.book.IForesterBook;
import forestry.book.gui.buttons.GuiButtonBack;
import forestry.book.gui.buttons.GuiButtonPage;

public class GuiForestryBookPages extends GuiForesterBook {
	private final IBookCategory category;
	private final IBookEntry entry;
	private List<IBookPage> pages;
	private int pageIndex = 0;
	@Nullable
	private IBookPage leftPage;
	@Nullable
	private IBookPage rightPage;

	public GuiForestryBookPages(IForesterBook book, IBookCategory category, IBookEntry entry) {
		super(book);
		this.category = category;
		this.entry = entry;
		this.pages = Collections.emptyList();
		initPages();
	}

	private void initPages(){
		pages = ImmutableList.copyOf(entry.getPageFactory().load(entry));
		setPages(0);
	}

	public void setPages(int index){
		if(index < 0 || index >= pages.size()){
			leftPage = null;
			rightPage = null;
			pageIndex = 0;
			return;
		}
		leftPage = pages.get(index);
		if(pages.size() > index + 1) {
			rightPage = pages.get(index + 1);
		}else{
			rightPage = null;
		}
		pageIndex = index;
	}

	@Override
	protected void initButtons(GuiButtonPage leftButton, GuiButtonPage rightButton, GuiButtonBack backButton) {
		leftButton.visible = pageIndex > 0;
		rightButton.visible = pages.size() > (pageIndex + 2);
	}

	@Override
	public void initGui() {
		super.initGui();
		if(leftPage != null) {
			leftPage.initPage(this);
			if (rightPage != null) {
				rightPage.initPage(this);
			}
		}
	}

	@Override
	protected void drawPages() {
		if(leftPage != null) {
			leftPage.draw(this, guiLeft + LEFT_PAGE_START_X, guiTop + LEFT_PAGE_START_Y, guiLeft, guiTop);
		}
		if(rightPage != null){
			rightPage.draw(this, guiLeft + RIGHT_PAGE_START_X, guiTop + RIGHT_PAGE_START_Y, guiLeft, guiTop);
		}
	}

	@Override
	protected List<String> getTooltip(int mouseX, int mouseY) {
		List<String> tooltip = new LinkedList<>();
		if(leftPage != null) {
			tooltip.addAll(leftPage.getTooltip(mouseX - guiLeft - LEFT_PAGE_START_X, mouseY - guiTop - LEFT_PAGE_START_Y));
			if (rightPage != null) {
				tooltip.addAll(rightPage.getTooltip(mouseX - guiLeft - RIGHT_PAGE_START_X, mouseY - guiTop - RIGHT_PAGE_START_Y));
			}
		}
		return tooltip;
	}

	@Override
	protected String getTitle() {
		return entry.getLocalizedName();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button instanceof GuiButtonPage){
			GuiButtonPage pageButton = (GuiButtonPage) button;
			if(pageButton.left){
				setPages(pageIndex - 2);
			}else{
				setPages(pageIndex + 2);
			}
			initGui();
		}else if(button instanceof GuiButtonBack || pages.isEmpty()){
			displayEntries();
		}else if(leftPage != null) {
			leftPage.onButtonPressed(this, button);
			if(rightPage != null){
				leftPage.onButtonPressed(this, button);
			}
		}
	}

	private void displayEntries(){
		mc.displayGuiScreen(new GuiForestryBookEntries(book, category));
	}
}
