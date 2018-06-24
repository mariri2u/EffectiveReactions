package mariri.effectivereactions.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BarrelRecipeManager {
	private static  BarrelRecipeManager INSTANCE = new BarrelRecipeManager();
	private List<BarrelRecipe> barrelRecipes;

	private BarrelRecipeManager(){
		barrelRecipes = new ArrayList<BarrelRecipe>();
	}

	public static BarrelRecipeManager getInstance(){
		return INSTANCE;
	}

	public void add(BarrelRecipe recipe){
		barrelRecipes.add(recipe);
	}

	public List<BarrelRecipe> getList(){
		return barrelRecipes;
	}

	public BarrelRecipe match(ItemStack input, IBlockState current){
		BarrelRecipe recipe = null;
		for(BarrelRecipe r : barrelRecipes){
			if(r.match(input, current)){
				recipe = r;
			}
		}
		return recipe;
	}
}
