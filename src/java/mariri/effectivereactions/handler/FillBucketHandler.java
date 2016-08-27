package mariri.effectivereactions.handler;


import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FillBucketHandler {

    public static FillBucketHandler INSTANCE = new FillBucketHandler();
    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    private FillBucketHandler() {}

	@SubscribeEvent
	public void onFillBucket(FillBucketEvent e){
		try{
	        IBlockState state = e.getWorld().getBlockState(e.getTarget().getBlockPos());
	        Item bucket = buckets.get(state.getBlock());

	        if(	bucket != null && state.getBlock().getMetaFromState(state) == 0){
	            e.getWorld().setBlockToAir(e.getTarget().getBlockPos());
	            e.setFilledBucket(new ItemStack(bucket));
	            e.setResult(Result.ALLOW);
	        }
		}catch(NullPointerException exception){

		}
	}
}
