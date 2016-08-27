package mariri.effectivereactions.handler;

import mariri.effectivereactions.block.BlockFluidPotion;
import mariri.effectivereactions.helper.CustomPotionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviorDispenseMagicGlass extends BehaviorDefaultDispenseItem {
	public final static BehaviorDispenseMagicGlass INSTANCE = new BehaviorDispenseMagicGlass();

	private BehaviorDispenseMagicGlass(){}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack itemstack){
		World world = source.getWorld();
		IPosition ipos = BlockDispenser.getDispensePosition(source);
		BlockPos pos = new BlockPos(ipos.getX(), ipos.getY(), ipos.getZ());
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		int meta = block.getMetaFromState(state);

		if(block instanceof BlockFluidPotion && meta == 0){
			world.setBlockToAir(pos);
			ItemStack potion = CustomPotionHelper.getSampleItem(((BlockFluidPotion)block).getPotionEffect(), 0, 0, false);
			potion.stackSize = itemstack.stackSize;
			itemstack = potion;
		}
		return itemstack;
	}

}
