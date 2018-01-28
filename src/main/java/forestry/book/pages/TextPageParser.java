package forestry.book.pages;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import forestry.api.book.IBookEntry;
import forestry.api.book.IBookPage;
import forestry.api.book.IBookPageFactory;
import forestry.api.core.GuiElementAlignment;
import forestry.api.core.IGuiElement;
import forestry.api.core.IGuiElementHelper;
import forestry.api.core.IGuiElementLayout;
import forestry.api.multiblock.IMultiblockBlueprint;
import forestry.api.multiblock.MultiblockBlueprints;
import forestry.core.config.Constants;
import forestry.core.gui.Drawable;
import forestry.core.gui.elements.GuiElement;
import forestry.core.gui.elements.GuiElementDrawable;
import forestry.core.gui.elements.GuiElementHelper;
import forestry.core.gui.elements.GuiElementIngredient;
import forestry.core.gui.elements.GuiElementMultiblock;
import forestry.core.gui.elements.GuiElementPanel;
import forestry.core.gui.elements.GuiElementText;
import forestry.core.gui.elements.GuiElementVertical;
import forestry.core.utils.Translator;

public class TextPageParser implements IBookPageFactory {
	public static final TextPageParser INSTANCE = new TextPageParser();

	private TextPageParser() {
	}

	@Override
	public Collection<IBookPage> load(IBookEntry entry) {
		String text = entry.getLocalizedPages();
		if (text.isEmpty()) {
			return Collections.emptySet();
		}
		text = text.replaceAll("<BR>", "\n");
		text = text.replaceAll("<BR/>", "\n");
		text = text.replaceAll("<p>", "\n<6>\n");
		text = text.replaceAll("<p/>", "\n<6>\n");
		text = text.replaceAll("<PAGE/>", "<3>");
		text = text.replaceAll("<PAGE>", "<3>");
		text = text.replaceAll("<CENTER>", "<4>");
		text = text.replaceAll("<CENTER/>", "<4>");
		List<Image> images = new ArrayList<>();
		List<IRecipe> recipes = new ArrayList<>();
		List<IMultiblockBlueprint> blueprints = new ArrayList<>();
		//Parse all crafting recipes and replace the old position of it with <1>
		String[] recipeArray = text.split("<crafting>");
		for (int i = 1; i < recipeArray.length; i++) {
			String rawText = recipeArray[i];
			int endIndex = rawText.indexOf("</crafting>");
			if (endIndex < 0) {
				continue;
			}
			String rawRecipe = rawText.substring(0, endIndex);
			IRecipe recipe = ForgeRegistries.RECIPES.getValue(new ResourceLocation(rawRecipe));
			if (recipe != null) {
				recipes.add(recipe);
				text = text.replaceFirst(rawRecipe, "<0>\n");
			} else {
				text = text.replaceFirst(rawRecipe, "\n");
			}
		}
		text = text.replaceAll("<crafting>", "");
		text = text.replaceAll("</crafting>", "");
		//Parse all images and replace the old position of it with <2>
		String[] imageArray = text.split("<img>");
		for (int i = 1; i < imageArray.length; i++) {
			String rawText = imageArray[i];
			int endIndex = rawText.indexOf("</img>");
			if (endIndex < 0) {
				continue;
			}
			String rawImage = rawText.substring(0, endIndex);
			Image image = Image.parse(rawImage);
			if (image != null) {
				images.add(image);
				text = text.replaceFirst(rawImage, "<1>\n");
			} else {
				text = text.replaceFirst(rawImage, "\n");
			}
		}
		text = text.replaceAll("<img>", "");
		text = text.replaceAll("</img>", "");

		//Parse all images and replace the old position of it with <2>
		String[] blueprintArray = text.split("<blueprint>");
		for (int i = 1; i < blueprintArray.length; i++) {
			String rawText = blueprintArray[i];
			int endIndex = rawText.indexOf("</blueprint>");
			if (endIndex < 0) {
				continue;
			}
			String rawBlueprint = rawText.substring(0, endIndex);
			IMultiblockBlueprint blueprint = MultiblockBlueprints.blueprints.get(rawBlueprint);
			if (blueprint != null) {
				blueprints.add(blueprint);
				text = text.replaceFirst("<blueprint>" + rawBlueprint + "</blueprint>", "<5>");
			} else {
				text = text.replaceFirst("<blueprint>" + rawBlueprint + "</blueprint>", "\n");
			}
		}

		PageBuilder builder = new PageBuilder();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(true);

		List<String> lines = fontRenderer.listFormattedStringToWidth(text, 108);
		fontRenderer.setUnicodeFlag(unicode);
		for (String line : lines) {
			if (line.contains("<0>")) {
				String[] t = line.split("<0>");
				if (t.length > 0) {
					builder.add(t[0]);
				}
				IRecipe recipe = recipes.remove(0);
				builder.add(recipe);
				if (t.length > 1) {
					builder.add(t[1]);
				}
				continue;
			} else if (line.contains("<1>")) {
				String[] t = line.split("<1>");
				if (t.length > 0) {
					builder.add(t[0]);
				}
				Image image = images.remove(0);
				builder.add(image);
				if (t.length > 1) {
					builder.add(t[1]);
				}
				continue;
			} else if (line.contains("<3>")) {
				String[] t = line.split("<3>");
				if (t.length > 0) {
					builder.add(t[0]);
				}
				builder.page(true, 0);
				if (t.length > 1) {
					builder.add(t[1]);
				}
				continue;
			} else if (line.contains("<4>")) {
				builder.add(line.replace("<4>", ""), true);
				continue;
			} else if (line.contains("<5>")) {
				String[] t = line.split("<5>");
				if (t.length > 0) {
					builder.add(t[0]);
				}
				IMultiblockBlueprint blueprint = blueprints.remove(0);
				builder.add(blueprint);
				if (t.length > 1) {
					builder.add(t[1]);
				}
				continue;
			} else if (line.endsWith("\n")) {
				builder.empty();
				continue;
			} else if (line.endsWith("<6>")) {
				builder.paragraph();
				continue;
			}
			builder.add(line);
		}
		return builder.getPages();
	}

	private static class Image {
		Drawable drawable;
		int width;
		int height;

		public Image(Drawable drawable, int width, int height) {
			this.drawable = drawable;
			this.width = width;
			this.height = height;
		}

		@Nullable
		private static Image parse(String text) {
			String[] segments = text.split(";");
			if (segments.length != 9) {
				return null;
			}
			try {
				ResourceLocation location = new ResourceLocation(segments[0]);
				int u = Integer.parseInt(segments[1]);
				int v = Integer.parseInt(segments[2]);
				int uWeight = Integer.parseInt(segments[3]);
				int vHeight = Integer.parseInt(segments[4]);
				int texWeight = Integer.parseInt(segments[5]);
				int texHeight = Integer.parseInt(segments[6]);
				int width = Integer.parseInt(segments[7]);
				int height = Integer.parseInt(segments[8]);
				return new Image(new Drawable(location, u, v, uWeight, vHeight, texWeight, texHeight), width, height);
			} catch (Exception ignored) {
			}
			return null;
		}
	}

	private static class PageBuilder {
		public static final Drawable CRAFTING_GRID = new Drawable(new ResourceLocation(Constants.MOD_ID, Constants.TEXTURE_PATH_GUI + "/atlas.png"), 158, 181, 98, 58);

		private static final int PAGE_HEIGHT = 155;

		private List<IBookPage> pages = new ArrayList<>();
		@Nullable
		private IGuiElementHelper currentPage;
		@Nullable
		private ContentType previousElement = null;

		public void empty() {
			if (isPageEmpty()) {
				return;
			}
			previousElement = null;
			IGuiElementHelper page = page(9);
			page.add(new GuiElement(108, 9));
		}

		public void paragraph() {
			if (isPageEmpty()) {
				return;
			}
			previousElement = null;
			int height = (int) (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 0.66D);
			IGuiElementHelper page = page(height);
			page.add(new GuiElement(108, height));
		}

		public void add(Image image) {
			previousElement = ContentType.IMAGE;
			IGuiElementHelper page = page(image.height);
			page.add(page.centerElementX(new GuiElementDrawable(0, 0, image.width, image.height, image.drawable)));
		}

		public void add(IMultiblockBlueprint blueprint) {
			previousElement = ContentType.BLUEPRINT;
			IGuiElementHelper page = page(100);
			page.add(new GuiElementMultiblock(0, 0, blueprint));
		}

		public void add(IRecipe recipe) {
			NonNullList<Ingredient> ingredients = recipe.getIngredients();
			IGuiElementHelper page = page(72);
			GuiElementPanel panel = new GuiElementPanel(98, 72);
			int height = 0;
			if(previousElement != ContentType.RECIPE) {
				panel.text(TextFormatting.DARK_GRAY + Translator.translateToLocal("for.gui.book.element.crafting"), GuiElementAlignment.CENTER);
				height = 12;
			}
			panel.drawable(0, height, CRAFTING_GRID);
			panel.item(81, height + 21, recipe.getRecipeOutput());
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					int index = y * 3 + x;
					if (ingredients.size() <= index) {
						continue;
					}
					Ingredient ingredient = ingredients.get(index);
					panel.add(new GuiElementIngredient(1 + x * 20, height + 1 + y * 20, ingredient));
				}
			}
			page.add(page.centerElementX(panel));
			previousElement = ContentType.RECIPE;
		}

		public void add(String line) {
			add(line, false);
		}

		public void add(String line, boolean center) {
			IGuiElementHelper page = page(9);
			IGuiElement parent = page.getParent();
			page.add(new GuiElementText(center ? parent.getWidth() : -1, 9, line, center ? GuiElementAlignment.CENTER : GuiElementAlignment.LEFT, 0, true));
		}

		private boolean isPageEmpty() {
			return currentPage != null && currentPage.getParent().getElements().isEmpty();
		}

		private IGuiElementHelper page(int height) {
			return page(false, height);
		}

		private IGuiElementHelper page(boolean forced, int elementHeight) {
			if (currentPage == null) {
				currentPage = createNewPage();
			} else {
				int height = PAGE_HEIGHT;
				if (pages.size() % 2 == 1) {
					//The left page is always smaller because of the title
					height -= 12;
				}
				IGuiElement parent = currentPage.getParent();
				if (forced || parent.getHeight() + elementHeight > height) {
					currentPage = createNewPage();
				}
			}
			return currentPage;
		}

		private GuiElementHelper createNewPage() {
			IGuiElementLayout page = new GuiElementVertical(108);
			pages.add(new BookPageElements(page));
			return new GuiElementHelper(page);
		}

		public List<IBookPage> getPages() {
			return pages;
		}
	}

	private enum ContentType{
		IMAGE, RECIPE, TEXT, BLUEPRINT, MUTATION
	}
}
