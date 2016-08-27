package mariri.effectivereactions.recipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BarrelRecipe {
	private ItemStack input;
	private IBlockState currentBarrel;
	private ItemStack output;
	private IBlockState nextBarrel;

	public BarrelRecipe(ItemStack input, IBlockState current, IBlockState next, ItemStack output){
		this.input = input;
		this.currentBarrel = current;
		this.nextBarrel = next;
		this.output = output;
	}

	public boolean match(ItemStack input, IBlockState current){
		boolean result =
				input.getItem() == this.input.getItem() &&
				input.getItemDamage() == this.input.getItemDamage() &&
				current.getBlock() == this.currentBarrel.getBlock() &&
				current.getBlock().getMetaFromState(current) == this.currentBarrel.getBlock().getMetaFromState(this.currentBarrel);
		return result;
	}

	public ItemStack getOutput(){
		return output;
	}

	public IBlockState getNext(){
		return nextBarrel;
	}
}
