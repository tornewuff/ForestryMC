package forestry.api.book;

import net.minecraft.item.ItemStack;

public interface IBookEntry {

	ItemStack getStack();

	void setStack(ItemStack stack);

	String getName();

	String getLocalizedName();

	String getLocalizedPages();

	IBookPageFactory getPageFactory();

	void setLoader(IBookPageFactory loader);

}
