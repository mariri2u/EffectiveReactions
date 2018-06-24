package mariri.effectivereactions.helper;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class SpawnHelper {

    public static void spawnCreature(World world, BlockPos pos, EntityLiving creature, double chance, int area){
    	boolean isSpawn = false;

    	List<EntityCreature> list = world.getEntitiesWithinAABB(EntityCreature.class,
    			new AxisAlignedBB(pos.getX() - 2 * area, pos.getY() - 4, pos.getZ() - 2 * area, pos.getX() + 2 * area + 1, pos.getY() + 5, pos.getZ() + 2 * area + 1));

		if(list.size() < 10 && world.rand.nextDouble() <= chance){
	        for(int i = 0; i <= area * 3; i++){
				int offsetX = world.rand.nextInt(2 * area + 1) - area - 1;
				int offsetY = world.rand.nextInt(5) - 3;
				int offsetZ = world.rand.nextInt(2 * area + 1) - area - 1;
				if(canSpawn(world, pos.add(offsetX, offsetY, offsetZ), creature)){
			        creature.setLocationAndAngles(pos.getX() + offsetX + 0.5, pos.getY() + offsetY, pos.getZ() + offsetZ + 0.5, 0.0F, 0.0F);
			        isSpawn = true;
			        break;
//				}else if(i == area * 2){
//			        creature.setLocationAndAngles(x + 0.5, y + 2, z + 0.5, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
	        	}
	        }

	        if(isSpawn){
	        	creature.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
	        	creature.rotationYawHead = creature.rotationYaw;
	        	creature.renderYawOffset = creature.rotationYaw;
	        	creature.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(creature)), (IEntityLivingData)null);
	        	world.spawnEntityInWorld(creature);
                creature.playLivingSound();

				if(creature instanceof EntityZombie && world.rand.nextDouble() < 0.7){
					((EntityZombie)creature).setChild(true);
//				}else if(creature instanceof EntityCreeper && world.rand.nextDouble() < 0.7){
//					creature.getDataManager().set(DataParameter POWERED, Boolean.valueOf(true));
//					creature.getDataWatcher().updateObject(17, Byte.valueOf((byte)1));
				}
				if(creature instanceof EntityZombie || creature instanceof EntitySkeleton){
					if(creature instanceof EntitySkeleton && world.rand.nextDouble() < 0.8){
						creature.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
					}else{
						creature.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
						creature.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
					}
					double r = world.rand.nextDouble();
					if(r < 0.05){
						creature.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Blocks.PUMPKIN));
					}else if(r < 0.6){
						creature.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
					}
					if(world.rand.nextDouble() < 0.6){
						creature.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
					}
					if(world.rand.nextDouble() < 0.6){
						creature.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
					}
					if(world.rand.nextDouble() < 0.6){
						creature.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
					}
				}

		        world.spawnEntityInWorld(creature);
		        creature.playLivingSound();
	        }
		}
    }

    private static boolean canSpawn(World world, BlockPos pos, EntityLiving creature){
    	if(creature instanceof EntitySquid || creature instanceof EntityBat || creature instanceof EntityCaveSpider){
    		return canSpawnInBlock(world, pos, 1, 1, false);
    	}else if(creature instanceof EntityEnderman){
    		return canSpawnInBlock(world, pos, 1, 3, false);
    	}else if(creature instanceof EntitySpider){
    		return canSpawnInBlock(world, pos, 2, 1, false);
    	}else if(creature instanceof EntitySlime){
    		return canSpawnInBlock(world, pos, 3, 3, false);
    	}else{
    		return canSpawnInBlock(world, pos, 1, 2, false);
    	}
    }

    private static boolean canSpawnInBlock(World world, BlockPos pos, int width, int height, boolean checkNoFluid){
    	boolean flag = true;
    	for(int i = 0; i < width; i++){
        	for(int j = 0; j < width; j++){
        		flag &= isAir(world.getBlockState(pos.add(-i, 0, -j)).getBlock());
        	}
        }
    	for(int i = 0; i < height; i++){
    		flag &= isAir(world.getBlockState(pos.up()).getBlock());
    	}
    	if(checkNoFluid){
    		flag &= !isFluid(world.getBlockState(pos.down()).getBlock());
    		flag &= !isFluid(world.getBlockState(pos.down(2)).getBlock());
    	}
    	return flag;
    }

    private static boolean isFluid(Block block){
		return block instanceof IFluidBlock || block instanceof BlockLiquid;
    }

    private static boolean isAir(Block block){
    	return block == Blocks.AIR;
    }
}
