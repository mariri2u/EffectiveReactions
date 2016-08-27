package mariri.effectivereactions.handler;

import mariri.effectivereactions.block.BlockEnderStone;
import mariri.effectivereactions.block.IBlockSafeStone;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class CheckSpawnEventHandler {
	public static CheckSpawnEventHandler INSTANCE = new CheckSpawnEventHandler();

	private CheckSpawnEventHandler() {}

	@SubscribeEvent
	public void onCheckSpawn(LivingSpawnEvent.CheckSpawn e){
		Block under = e.getWorld().getBlockState(new BlockPos(e.getX(), e.getY() - 1, e.getZ()	)).getBlock();

		if(under instanceof BlockEnderStone){
			if(e.getEntityLiving() instanceof EntityEnderman){
				e.setResult(Event.Result.DEFAULT);
			}else{
				e.setResult(Event.Result.DENY);
				EntityEnderman ender = new EntityEnderman(e.getWorld());
				ender.setPosition(e.getX() + 0.5, e.getY(), e.getZ() + 0.5);
				if(e.getEntityLiving() instanceof IMob && ender.getCanSpawnHere()){
					Entity entity = ItemMonsterPlacer.spawnCreature(e.getWorld(), "Enderman", e.getX() + 0.5, e.getY(), e.getZ() + 0.5);
				}
			}
		}else if(under instanceof IBlockSafeStone){
			e.setResult(Event.Result.DEFAULT);
		}else if(e.getEntityLiving() instanceof IMob && !(e.getEntityLiving() instanceof EntityGuardian)){
			e.setResult(Event.Result.DENY);
		}else{
			e.setResult(Event.Result.DEFAULT);
		}

//		if(under instanceof IBlockSafeStone && e.entityLiving instanceof IMob){
//			System.out.println("- Check Spawn -");
//			System.out.println(under.getClass().getCanonicalName() + " : " + e.entityLiving.getClass().getCanonicalName());
//			System.out.println("Result: " + e.getResult());
//		}
	}
}
