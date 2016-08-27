package mariri.effectivereactions.block;

import java.util.Random;

import mariri.effectivereactions.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSafeStoneBase extends Block implements IBlockSafeStone {

	public BlockSafeStoneBase(){
		super(Material.ROCK);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type){
		if(world.getBlockState(pos.down()) == Blocks.MOB_SPAWNER) {
			return false;
		}else if(ModRegistry.INVERT_SPAWN_RULE){
			return super.canCreatureSpawn(state, world, pos, type);
		}else{
			return false;
		}
    }

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		onUpdate(world, pos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		super.updateTick(world, pos, state, rand);
		onUpdate(world, pos);
	}


	public void onUpdate(World world, BlockPos pos){
		if(world.getBlockState(pos.down()).getBlock() == Blocks.MOB_SPAWNER){
			TileEntity tileentity = world.getTileEntity(pos.down());
			if(tileentity instanceof TileEntityMobSpawner){
				MobSpawnerBaseLogic spawner = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
				NBTTagCompound nbt = new NBTTagCompound();
				spawner.writeToNBT(nbt);
				nbt.setShort("Delay", (short)800);
				spawner.readFromNBT(nbt);
			}
			world.scheduleUpdate(pos, this, 100);
		}
	}
}
