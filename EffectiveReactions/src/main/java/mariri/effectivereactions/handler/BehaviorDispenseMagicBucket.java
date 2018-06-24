package mariri.effectivereactions.handler;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public class BehaviorDispenseMagicBucket extends BehaviorDefaultDispenseItem {
	public final static BehaviorDispenseMagicBucket INSTANCE = new BehaviorDispenseMagicBucket();

	private BehaviorDispenseMagicBucket(){}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack itemstack){
//		World world = source.getWorld();
//		IPosition ipos = BlockDispenser.getDispensePosition(source);
//		BlockPos pos = new BlockPos(ipos.getX(), ipos.getY(), ipos.getZ());
//		IBlockState state = world.getBlockState(pos);
//		Block block = state.getBlock();
//		int meta = block.getMetaFromState(state);
//		Item item;
//
//		if(block instanceof BlockFluidPotion && meta == 0){
//			item = EffectiveReactions.itemPotionBuckets[Potion.getIdFromPotion(((BlockFluidPotion)block).getPotionEffect()) - 1];
//		}else if(state.getMaterial() == Material.WATER){
//			item = Items.WATER_BUCKET;
//		}else if(state.getMaterial() == Material.LAVA){
//			item = Items.LAVA_BUCKET;
//		}else{
//			return super.dispenseStack(source, itemstack);
//		}
//
//		world.setBlockToAir(pos);
//
//		if(--itemstack.stackSize == 0){
//			itemstack.setItem(item);
//			itemstack.stackSize = 1;
//		}else if(((TileEntityDispenser)source.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0){
//			super.dispenseStack(source, new ItemStack(item));
//		}

		return itemstack;
	}
}
