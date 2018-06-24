package mariri.effectivereactions;

import java.util.List;

import mariri.effectivereactions.recipe.BarrelRecipe;
import mariri.effectivereactions.recipe.BarrelRecipeManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModRegistry {

	private static ModRegistry INSTANCE = new ModRegistry();

	private ModRegistry(){	}

	public static boolean INVERT_SPAWN_RULE;

	public static ModRegistry getInstance(){
		return INSTANCE;
	}

	public static void addBarrelRecipe(Item input, IBlockState current, IBlockState next, Item output){
		addBarrelRecipe(new ItemStack(input), current, next, new ItemStack(output));
	}

	public static void addBarrelRecipe(Item input, IBlockState current, IBlockState next, ItemStack output){
		addBarrelRecipe(new ItemStack(input), current, next, output);
	}

	public static void addBarrelRecipe(ItemStack input, IBlockState current, IBlockState next, ItemStack output){
		BarrelRecipeManager.getInstance().add(new BarrelRecipe(input, current, next, output));
	}

	public static List<BarrelRecipe> getBarrelRecipeList(){
		return BarrelRecipeManager.getInstance().getList();
	}
}
