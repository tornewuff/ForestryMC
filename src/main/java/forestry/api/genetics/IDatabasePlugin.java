/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.genetics;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * An ISpeciesPlugin provides methods that are used in the alyzer and database to display information about an
 * individual.
 */
@SideOnly(Side.CLIENT)
public interface IDatabasePlugin<I extends IIndividual> {

	/**
	 * A map that contains a {@link ItemStack} for every possible {@link IAlleleSpecies}.
	 * This item stack represents the species in the database and alyzer gui.
	 *
	 * The key of the map is the uid of the species.
	 */
	Map<String, ItemStack> getIndividualStacks();

	/* ALYZER */
	List<String> getHints();

	/* DATABASE */
	/**
	 * Provides a instance of a {@link IDatabaseTab} that is used to display information about the species of a individual in the
	 * database.
	 *
	 * @param active True if this tab is used to display the active species of the individual.
	 *
	 * @return null if this plugin does not support the database.
	 */
	default IDatabaseTab<I> getSpeciesTab(boolean active){
		return null;
	}

	/**
	 * Can be used to provide a custom products tab for the database.
	 * If this is Null forestry uses the default mutation tab to display the products.
	 */
	@Nullable
	default IDatabaseTab<I> getProductsTab(){
		return null;
	}

	/**
	 * Can be used to provide a custom mutation tab for the database.
	 * If this is Null forestry uses the default mutation tab to display the mutations.
	 */
	@Nullable
	default IDatabaseTab<I> getMutationTab(){
		return null;
	}

	/**
	 * A item stack that represents the database tab in the database gui.
	 */
	ItemStack getTabDatabaseIconItem(EnumDatabaseTab tab);
}
