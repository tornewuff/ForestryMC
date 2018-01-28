package forestry.book;

import net.minecraft.item.ItemStack;

import forestry.api.book.IBookEntry;
import forestry.api.book.IBookPageFactory;
import forestry.book.pages.TextPageParser;
import forestry.core.utils.Translator;

public class BookEntry implements IBookEntry {
	private final String name;
	private IBookPageFactory loader = TextPageParser.INSTANCE;
	private ItemStack stack = ItemStack.EMPTY;

	public BookEntry(String name) {
		this.name = name;
	}

	@Override
	public void setLoader(IBookPageFactory loader) {
		this.loader = loader;
	}

	@Override
	public IBookPageFactory getPageFactory() {
		return loader;
	}

	@Override
	public ItemStack getStack() {
		return stack;
	}

	@Override
	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public String getLocalizedName() {
		return Translator.translateToLocal("for.gui.book.entry." + name + ".title");
	}

	@Override
	public String getLocalizedPages() {
		return Translator.translateToLocal("for.gui.book.entry." + name + ".pages");
	}

	@Override
	public String getName() {
		return name;
	}
}
