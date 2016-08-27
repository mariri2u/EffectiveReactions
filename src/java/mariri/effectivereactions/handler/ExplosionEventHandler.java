package mariri.effectivereactions.handler;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExplosionEventHandler {
	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Start e){
		World world = e.getWorld();
		BlockPos water = null;
		int count = 0;
		Entity explode = e.getExplosion().getExplosivePlacedBy();
		int x = (int)explode.posX;
		int y = (int)explode.posY;
		int z = (int)explode.posZ;

		System.out.println(x + ", " + y + ", " + z);

		for(int xi = -2; xi <= 3; xi++){
			for(int yi = -1; yi <= 2; yi++){
				for(int zi = -2; zi <= 3; zi++){
					BlockPos pos = new BlockPos(x + xi, y + yi, z + zi);
					if(world.getBlockState(pos).getBlock() == Blocks.ICE){
						water = pos;
					}
				}
			}
		}
		if(water != null){
			List<EntityItem> entityList =
					world.getEntitiesWithinAABB(EntityItem.class,
					new AxisAlignedBB(water.getX() - 2, water.getY() - 1, water.getZ() - 2, water.getX() + 3, water.getY() + 2, water.getZ() + 3));
			for(EntityItem item : entityList){
				if(item.getEntityItem().getItem() == Items.ROTTEN_FLESH){
					count += item.getEntityItem().stackSize;
				}
			}
			if(count >= 8){
				world.setBlockState(water, EffectiveReactions.blockFluidPotions[0].getDefaultState());
			}
			e.setCanceled(true);
		}
		System.out.println(count);
	}
}
