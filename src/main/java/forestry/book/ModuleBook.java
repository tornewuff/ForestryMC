package forestry.book;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import forestry.api.modules.ForestryModule;
import forestry.book.items.ItemRegistryBook;
import forestry.core.config.Constants;
import forestry.core.recipes.RecipeUtil;
import forestry.core.utils.OreDictUtil;
import forestry.modules.BlankForestryModule;
import forestry.modules.ForestryModuleUids;
import forestry.modules.ModuleManager;

@ForestryModule(containerID = Constants.MOD_ID, moduleID = ForestryModuleUids.BOOK, name = "Book", author = "Nedelosk", url = Constants.URL, unlocalizedDescription = "for.module.book.description")
public class ModuleBook extends BlankForestryModule {
	@Nullable
	private static ItemRegistryBook items;

	public static ItemRegistryBook getItems() {
		Preconditions.checkState(items != null);
		return items;
	}


	@Override
	public void registerItemsAndBlocks() {
		items = new ItemRegistryBook();
	}

	@Override
	public void registerRecipes() {
		RecipeUtil.addShapelessRecipe("book", new ItemStack(getItems().book), Items.BOOK, OreDictUtil.DROP_HONEYDEW);
		RecipeUtil.addShapelessRecipe("book", new ItemStack(getItems().book), Items.BOOK, OreDictUtil.TREE_SAPLING);
	}

	@Override
	public void postInit() {
		ModuleManager.getInternalHandler().runBookInit();
	}
}
