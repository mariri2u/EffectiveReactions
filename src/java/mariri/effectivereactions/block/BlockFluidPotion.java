package mariri.effectivereactions.block;

import java.util.Random;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.helper.CustomPotionHelper;
import mariri.effectivereactions.helper.SpawnHelper;
import mariri.effectivereactions.model.FluidPotionItemMeshDefinition;
import mariri.effectivereactions.model.FluidPotionStateMapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluidPotion extends BlockFluidClassic {

	protected Potion potionEffect;
	protected boolean explode = true;
	protected int explosionPower = 3;
	protected boolean spawn = true;
	protected boolean infinity = true;

	public BlockFluidPotion(Fluid fluid, Material material, Potion effect) {
		super(fluid, material);

		int id = Potion.getIdFromPotion(effect);
		ResourceLocation res = new ResourceLocation(EffectiveReactions.MODID.toLowerCase(), "fluid_potion_" + id);

		this.setPotionEffect(effect);
		this.setExplode(EffectiveReactions.ENABLE_REACT_EXPLOSION);
		this.setExplosionPower(EffectiveReactions.REACT_EXPLOSION_POWER);
		this.setSpawn(EffectiveReactions.ENABLE_REACT_SPAWN);
		this.setInfinity(EffectiveReactions.ENABLE_INFINITY_SOURCE);
//		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName(res.getResourcePath());
//		GameRegistry.registerBlock(this, res.getResourcePath());
		GameRegistry.register(this, res);
		GameRegistry.register(new ItemBlock(this), res);
	}

    //ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	meta = (meta % 16 + 16) % 16;
        return this.getDefaultState().withProperty(LEVEL, meta);
    }

    //IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return (Integer)state.getValue(LEVEL);
//    }

    //初期BlockStateの生成。
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, LEVEL);
//    }

	public BlockFluidPotion setPotionEffect(Potion potionEffect){
		this.potionEffect = potionEffect;
		return this;
	}

	public Potion getPotionEffect(){
		return potionEffect;
	}

	public BlockFluidPotion setExplode(boolean value){
		this.explode = value;
		return this;
	}

	public BlockFluidPotion setExplosionPower(int value){
		this.explosionPower = value;
		return this;
	}

	public BlockFluidPotion setSpawn(boolean value){
		this.spawn = value;
		return this;
	}

	public BlockFluidPotion setInfinity(boolean value){
		this.infinity = value;
		return this;
	}

//    protected void flowIntoBlock(World world, int x, int y, int z, int meta)
//    {
//    	Block block = world.getBlock(x, y, z);
//    	if(block == Blocks.water){
//
//    	}else if(block == Blocks.lava){
//
//    	}
//    	world.setBlockToAir(x, y, z);
//    	super.flowIntoBlock(world, x, y, z, meta);
//    }

	@Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
		if(CustomPotionHelper.isSupport(potionEffect)){
	        world.setBlockToAir(pos);
	        onBlockDestroyedByExplosion(world, pos, explosion);
		}else if(CustomPotionHelper.isNeutral(potionEffect)){
			if(world.provider.doesWaterVaporize()){
				spawnCreature(world, pos, new EntityBlaze(world), 1.0);
			}else{
				spawnCreature(world, pos, new EntityVillager(world), 1.0);
			}
		}else{
			if(world.provider.doesWaterVaporize()){
				spawnCreature(world, pos, new EntityMagmaCube(world), 1.0);
			}else{
				spawnCreature(world, pos, new EntitySlime(world), 1.0);
			}
		}
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
//    	checkExplosion(world, x, y, z);
    	reactBlock(world, pos, world.getBlockState(pos.east()));
    	reactBlock(world, pos, world.getBlockState(pos.west()));
    	reactBlock(world, pos, world.getBlockState(pos.north()));
    	reactBlock(world, pos, world.getBlockState(pos.south()));
    	reactBlock(world, pos, world.getBlockState(pos.up()));
    	super.onBlockAdded(world, pos, state);
    }

    @Override
    public void onNeighborChange(IBlockAccess worldIn, BlockPos pos, BlockPos neighbor)
    {
    	World world = (World)worldIn;
//    	checkExplosion(world, x, y, z);
    	reactBlock(world, pos, world.getBlockState(pos.east()));
    	reactBlock(world, pos, world.getBlockState(pos.west()));
    	reactBlock(world, pos, world.getBlockState(pos.north()));
    	reactBlock(world, pos, world.getBlockState(pos.south()));
    	reactBlock(world, pos, world.getBlockState(pos.up()));
    	super.onNeighborChange(world, pos, neighbor);
    }

//    private boolean checkExplosion(World world, int x, int y, int z){
//        boolean flag = false;
//        if(!CustomPotionHelper.isSupport(potionEffect)) { return false; }
//        if(world.getBlock(x, y, z) == this){
//            flag |= world.getBlock(x, y, z - 1).getMaterial() == Material.lava;
//            flag |= world.getBlock(x, y, z + 1).getMaterial() == Material.lava;
//            flag |= world.getBlock(x - 1, y, z).getMaterial() == Material.lava;
//            flag |= world.getBlock(x + 1, y, z).getMaterial() == Material.lava;
//            flag |= world.getBlock(x, y + 1, z).getMaterial() == Material.lava;
//            if(world.getBlockMetadata(x, y, z) == 0){
//	           	flag |= world.canBlockSeeTheSky(x, y, z) && world.getBlockLightValue(x, y, z) == 15;
//	           	flag |= world.provider.isHellWorld;
//            }
//            if(flag){
//            	world.setBlockToAir(x, y, z);
//            	world.newExplosion(null, x, y, z, 4, true, false);
////            	world.createExplosion(null, x, y, z, 4, true);
//            }
//        }
//        return flag;
//    }

//    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
//    	if(world.getBlockMetadata(x, y, z) == 0){
//    		world.createExplosion(null, x, y, z, 4, true);
//    	}
//    }

    private void reactBlock(World world, BlockPos pos, IBlockState state){
    	Block infusion = state.getBlock();
    	boolean support = CustomPotionHelper.isSupport(this.potionEffect);
    	boolean neutral = CustomPotionHelper.isNeutral(this.potionEffect);
    	boolean bad = CustomPotionHelper.isBad(this.potionEffect);
    	boolean instant = CustomPotionHelper.isInstant(this.potionEffect);
    	Fluid fluid = FluidRegistry.lookupFluidForBlock(infusion);
//    	if(world.getBlockMetadata(x, y, z) != 0){ return; }
    	if(this == infusion){ return; }
    	if(infusion instanceof BlockFluidPotion){
    		Potion effect = ((BlockFluidPotion)infusion).getPotionEffect();
    		if(support != CustomPotionHelper.isSupport(effect)){
            	createExplosion(world, pos);
    		}else if((neutral && CustomPotionHelper.isBad(effect)) || (bad && CustomPotionHelper.isNeutral(effect))){
    			world.setBlockState(pos, Blocks.SEA_LANTERN.getStateFromMeta(0));
    		}else if(support){
    			// Block
    			if(effect == MobEffects.SPEED || effect == MobEffects.HASTE){
        			world.setBlockState(pos, Blocks.SAND.getStateFromMeta(0));
    			}else if(effect == MobEffects.STRENGTH){
        			world.setBlockState(pos, Blocks.DIRT.getStateFromMeta(2));
    			}else if(effect == MobEffects.INSTANT_HEALTH || effect == MobEffects.REGENERATION){
        			world.setBlockState(pos, Blocks.GRAVEL.getStateFromMeta(0));
    			}else if(effect == MobEffects.JUMP_BOOST){
        			world.setBlockState(pos, Blocks.SLIME_BLOCK.getStateFromMeta(0));
    			}else if(effect == MobEffects.RESISTANCE || effect == MobEffects.FIRE_RESISTANCE){
        			world.setBlockState(pos, Blocks.SAND.getStateFromMeta(1));
    			}else if(effect == MobEffects.WATER_BREATHING || effect == MobEffects.SATURATION){
        			world.setBlockState(pos, Blocks.PRISMARINE.getStateFromMeta(0));
    			}else if(effect == MobEffects.NIGHT_VISION){
        			world.setBlockState(pos, Blocks.PRISMARINE.getStateFromMeta(2));
    			}else if(effect == MobEffects.HEALTH_BOOST || effect == MobEffects.ABSORPTION){
        			world.setBlockState(pos, Blocks.HARDENED_CLAY.getStateFromMeta(0));
    			}else if(effect == MobEffects.LUCK){
        			world.setBlockState(pos, Blocks.PRISMARINE.getStateFromMeta(1));
    			}
    			// Creature
//				if(this.potionEffect == MobEffects.STRENGTH || effect == MobEffects.STRENGTH){
//					spawnCreature(world, pos, new EntityEnderman(world), 0.1);
//				}
//				if(this.potionEffect == MobEffects.JUMP_BOOST || effect == MobEffects.JUMP_BOOST){
//					spawnCreature(world, pos, new EntityBat(world), 0.1);
//				}
//				if(this.potionEffect == MobEffects.REGENERATION || effect == MobEffects.REGENERATION){
//					spawnCreature(world, pos, new EntitySlime(world), 0.1);
//				}
//				if(this.potionEffect == MobEffects.WATER_BREATHING || effect == MobEffects.WATER_BREATHING){
//					spawnCreature(world, pos, new EntitySquid(world), 0.1);
//				}
//				if(this.potionEffect == MobEffects.INVISIBILITY || effect == MobEffects.INVISIBILITY){
//					spawnCreature(world, pos, new EntityVillager(world), 0.1);
//				}
//				if(this.potionEffect == MobEffects.NIGHT_VISION || effect == MobEffects.NIGHT_VISION){
//					spawnCreature(world, pos, new EntitySpider(world), 0.1);
//				}
			}else if(neutral){
				world.setBlockState(pos, Blocks.END_STONE.getStateFromMeta(0));
    		}else{
				// Block
    			if(effect == MobEffects.SLOWNESS || effect == MobEffects.MINING_FATIGUE){
	    			world.setBlockState(pos, Blocks.STONE.getStateFromMeta(1));
    			}else if(effect == MobEffects.INSTANT_DAMAGE || effect == MobEffects.WEAKNESS){
	    			world.setBlockState(pos, Blocks.NETHERRACK.getStateFromMeta(0));
    			}else if(effect == MobEffects.NAUSEA || effect == MobEffects.BLINDNESS){
        			world.setBlockState(pos, Blocks.STONE.getStateFromMeta(3));
    			}else if(effect == MobEffects.HUNGER || effect == MobEffects.POISON){
        			world.setBlockState(pos, Blocks.SOUL_SAND.getStateFromMeta(0));
    			}else if(effect == MobEffects.LEVITATION || effect == MobEffects.UNLUCK){
        			world.setBlockState(pos, Blocks.STONE.getStateFromMeta(5));
    			}else if(effect == MobEffects.WITHER){
        			world.setBlockState(pos, Blocks.NETHER_BRICK.getStateFromMeta(0));
				}
				// Creature
//				if(this.potionEffect == MobEffects.SLOWNESS || effect == MobEffects.SLOWNESS){
//					spawnCreature(world, pos, new EntityWitch(world), 1.0);
//				}
//				if(this.potionEffect == MobEffects.MINING_FATIGUE || effect == MobEffects.MINING_FATIGUE){
//					spawnCreature(world, pos, new EntityCreeper(world), 1.0);
//				}
//				if(this.potionEffect == MobEffects.INSTANT_DAMAGE || effect == MobEffects.INSTANT_DAMAGE){
//					// duplicate
//					spawnCreature(world, pos, new EntityWitch(world), 1.0);
//				}
//				if(this.potionEffect == MobEffects.NAUSEA || effect == MobEffects.NAUSEA){
//					// duplicate
//					if(world.provider.doesWaterVaporize()){
//						spawnCreature(world, pos, new EntityPigZombie(world), 1.0);
//					}else{
//						spawnCreature(world, pos, new EntityZombie(world), 1.0);
//					}
//				}
//				if(this.potionEffect == MobEffects.BLINDNESS || effect == MobEffects.BLINDNESS){
//					spawnCreature(world, pos, new EntityBlaze(world), 1.0);
//				}
//				if(this.potionEffect == MobEffects.HUNGER || effect == MobEffects.HUNGER){
//					if(world.provider.doesWaterVaporize()){
//						spawnCreature(world, pos, new EntityPigZombie(world), 1.0);
//					}else{
//						spawnCreature(world, pos, new EntityZombie(world), 1.0);
//					}
//				}
//				if(this.potionEffect == MobEffects.WEAKNESS || effect == MobEffects.WEAKNESS){
//					// duplicate
//					spawnCreature(world, pos, new EntitySkeleton(world), 1.0);
//				}
//				if(this.potionEffect == MobEffects.POISON || effect == MobEffects.POISON){
//					spawnCreature(world, pos, new EntityCaveSpider(world), 1.0);
//				}
//				if(this.potionEffect == MobEffects.WITHER || effect == MobEffects.WITHER){
//					spawnCreature(world, pos, new EntitySkeleton(world), 1.0);
//				}
			}
    	}else if(state.getMaterial() == Material.LAVA){
    		if(support){
    			world.setBlockState(pos, Blocks.SANDSTONE.getStateFromMeta(0));
    		}else if(neutral){
    			world.setBlockState(pos, Blocks.field_189877_df.getStateFromMeta(0));
    		}else{
    			world.setBlockState(pos, Blocks.RED_SANDSTONE.getStateFromMeta(0));
    		}
    	}else if(state.getMaterial() == Material.WATER){
    		if(support){
    			world.setBlockState(pos, Blocks.GRASS.getStateFromMeta(0));
    		}else if(neutral){
    			world.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
    		}else{
    			world.setBlockState(pos, Blocks.CLAY.getStateFromMeta(0));
    		}
    	}else if(infusion == Blocks.ICE){
   			world.setBlockState(pos, EffectiveReactions.blockSemiSolidPotion.getStateFromMeta(0));
    	}
    }

    private void spawnCreature(World world, BlockPos pos, EntityLiving creature, double chance){
    	if(spawn){
    		SpawnHelper.spawnCreature(world, pos, creature, chance, 4);
    	}
    }

    private boolean reactSunlight(World world, BlockPos pos){
    	boolean flag = false;
        if(world.getBlockState(pos).getBlock() == this && world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) == 0 && CustomPotionHelper.isSupport(this.potionEffect)){
           	flag |= world.canBlockSeeSky(pos) && world.getLight(pos) == 15;
//           	flag |= world.canBlockSeeTheSky(x, y, z);
           	flag |= world.provider.doesWaterVaporize();
            if(flag){
            	world.setBlockToAir(pos);
            	createExplosion(world, pos);
            }
        }
        return flag;
    }

    private void createExplosion(World world, BlockPos pos){
    	if(explode){
    		world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), explosionPower, true);
    	}
    }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		super.updateTick(world, pos, state, rand);
		if(reactSunlight(world, pos)) { return; }
//		Block aboveBlock = world.getBlock(x, y + 1, z);
		int count = 0;
//		int waterCount = 0;
//		int lavaCount = 0;
		for(int i = 0; i < 4; i++){
			int offsetX = 0;
			int offsetZ = 0;
			switch(i){
			case 0:
				offsetX = -1;
				break;
			case 1:
				offsetX = 1;
				break;
			case 2:
				offsetZ = -1;
				break;
			case 3:
				offsetZ = 1;
				break;
			}
			Block block = world.getBlockState(pos.add(offsetX, 0, offsetZ)).getBlock();
			if(block == this && block.getMetaFromState(world.getBlockState(pos.add(offsetX, 0, offsetZ))) == 0){
				count++;
			}
//			if(block == Blocks.water){
//				waterCount++;
//			}
//			if(block == Blocks.lava){
//				lavaCount++;
//			}
		}
//		block = world.getBlock(x - 1, y, z);
//		if(block == this && world.getBlockMetadata(x - 1, y, z) == 0){
//			count++;
//		}
//		block = world.getBlock(x, y, z + 1);
//		if(block == this && world.getBlockMetadata(x, y, z + 1) == 0){
//			count++;
//		}
//		block = world.getBlock(x, y, z - 1);
//		if(block == this && world.getBlockMetadata(x, y, z - 1) == 0){
//			count++;
//		}
//		if(count >= 2){

//		if(aboveBlock == Blocks.water || waterCount > 0){
//			world.setBlock(x, y, z, Blocks.water, 1, 2);
//            world.scheduleBlockUpdate(x, y, z, Blocks.water, 0);
//            world.notifyBlocksOfNeighborChange(x, y, z, Blocks.water);
//		}else if(aboveBlock == Blocks.lava || lavaCount > 0){
//			if(world.getBlockMetadata(x, y, z) == 0){
//				world.createExplosion(null, x, y, z, 4, true);
//				world.setBlockToAir(x, y, z);
//	            world.scheduleBlockUpdate(x, y, z, Blocks.air, 0);
//	            world.notifyBlocksOfNeighborChange(x, y, z, Blocks.air);
//			}else{
//				world.setBlock(x, y, z, Blocks.lava, 1, 2);
//	            world.scheduleBlockUpdate(x, y, z, Blocks.lava, 0);
//	            world.notifyBlocksOfNeighborChange(x, y, z, Blocks.lava);
//			}
//		}else if(potCount >= 2 && world.getBlock(x, y - 1, z).isNormalCube()){
		if(infinity && count >= 2 && world.getBlockState(pos.down()).getBlock().isNormalCube(world.getBlockState(pos.down()))){
			world.setBlockState(pos, this.getDefaultState(), 2);
            world.scheduleBlockUpdate(pos, this, 0, 0);
            world.notifyNeighborsOfStateChange(pos, this);
		}

//		if(potCount >= 2 && world.getBlock(x, y - 1, z).isNormalCube()){
//			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
//            world.scheduleBlockUpdate(x, y, z, this, 0);
//            world.notifyBlocksOfNeighborChange(x, y, z, this);
//		}
	}

	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if(!world.isRemote){
			if(entity instanceof EntityLivingBase){
				if(CustomPotionHelper.isInstant(potionEffect)){
					if(world.getWorldTime() % 60 == 0){
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(potionEffect, CustomPotionHelper.INSTANT_DURATION, 0));
						if(potionEffect == MobEffects.INSTANT_DAMAGE && entity instanceof EntityVillager){
							if(world.rand.nextDouble() < 0.05){
								spawnItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.EMERALD));
							}
						}
					}
				}else{
					if(world.getWorldTime() % 20 == 0){
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(potionEffect, 100, 0));
	//					if(potionEffect == CustomPotionHelper.NAUSEA && entity instanceof EntityCreature){
	//						if(world.rand.nextDouble() <= 1){
	//							spawnItem(world, x, y, z, new ItemStack(Items.spawn_egg, 1, entity.getEntityId()));
	//						}
	//					}
					}
				}
			}else if(entity instanceof EntityTNTPrimed && world.getWorldTime() % 20 == 0){
				if(CustomPotionHelper.isNeutral(potionEffect)){
//					entity.setDead();
					if(world.provider.doesWaterVaporize()){
						spawnCreature(world, pos, new EntityBlaze(world), 1.0);
					}else{
						spawnCreature(world, pos, new EntityVillager(world), 1.0);
					}
				}else if(CustomPotionHelper.isBad(potionEffect) && world.getWorldTime() % 20 == 0){
//					entity.setDead();
					if(world.provider.doesWaterVaporize()){
						spawnCreature(world, pos, new EntityMagmaCube(world), 1.0);
					}else{
						spawnCreature(world, pos, new EntitySlime(world), 1.0);
					}
				}
			}
		}
	}

	private void spawnItem(World world, double x, double y, double z, ItemStack itemstack){
	    float f = 0.7F;
	    double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	    double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	    double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	    EntityItem entityitem =
	    		new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, itemstack);
	    entityitem.setPickupDelay(10);
	    world.spawnEntityInWorld(entityitem);
	}
//
//	@Override
//    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float p_149746_6_) {
//		if(entity instanceof EntityLiving && !CustomPotionHelper.isInstant(potionEffect)){
//			((EntityLiving)entity).addPotionEffect(new PotionEffect(potionEffect, 100, 0));
//		}
//
//	}



	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, pos);
	}

    @SideOnly(Side.CLIENT)
    public void registerTextures(ModelResourceLocation model){
    	ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this), new FluidPotionItemMeshDefinition(model));
    	ModelLoader.setCustomStateMapper(this, new FluidPotionStateMapper(model));
//    	ModelLoader.setCustomModelResourceLocation(item, metadata, model);
    }
}
