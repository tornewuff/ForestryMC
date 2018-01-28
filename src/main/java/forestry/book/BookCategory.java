package forestry.book;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import forestry.api.book.IBookCategory;
import forestry.api.book.IBookEntry;
import forestry.core.utils.Translator;

public class BookCategory implements IBookCategory {
	private final String name;
	private final Map<String, IBookEntry> entries = new LinkedHashMap<>();
	private ItemStack stack = ItemStack.EMPTY;

	public BookCategory(String name) {
		this.name = name;
	}

	@Override
	public ItemStack getStack() {
		return stack;
	}

	@Override
	public IBookCategory setStack(ItemStack stack) {
		this.stack = stack;
		return this;
	}

	@Override
	public IBookCategory addEntry(IBookEntry entry) {
		entries.put(entry.getName(), entry);
		return this;
	}

	@Override
	public IBookCategory addEntry(String name) {
		entries.put(name, new BookEntry(name));
		return this;
	}

	@Override
	public IBookCategory addEntry(String name, ItemStack stack) {
		IBookEntry entry = new BookEntry(name);
		entry.setStack(stack);
		entries.put(name, entry);
		return this;
	}

	@Override
	public Collection<IBookEntry> getEntries() {
		return entries.values();
	}

	@Nullable
	@Override
	public IBookEntry getEntry(String name) {
		return entries.get(name);
	}

	@Override
	public String getLocalizedName() {
		return Translator.translateToLocal("for.gui.book.category." + name + ".title");
	}

	@Override
	public String getTooltip() {
		return "for.gui.book.category." + name + ".tooltip";
	}

	@Override
	public String getName() {
		return name;
	}
}
