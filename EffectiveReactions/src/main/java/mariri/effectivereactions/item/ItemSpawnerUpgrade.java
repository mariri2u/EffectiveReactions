package mariri.effectivereactions.item;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.helper.CustomSpawnerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpawnerUpgrade extends Item{

	public static final int SUB_BLOCK_VALUES = 8;

	public ItemSpawnerUpgrade(){
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName("spawner_upgrade");
		GameRegistry.register(this, new ResourceLocation(EffectiveReactions.MODID, "spawner_upgrade"));

    	for(int i = 0; i < 4; i++){
    		GameRegistry.addShapelessRecipe(
    			new ItemStack(this, 1, i),
    			(i == 0) ? new ItemStack(Items.PAPER) : new ItemStack(this, 1, i - 1),
    			new ItemStack(Blocks.QUARTZ_ORE),
    			(i == 0) ? new ItemStack(Items.BLAZE_POWDER) : new ItemStack(Items.MAGMA_CREAM),
    			(i < 2) ? new ItemStack(Items.REDSTONE) : new ItemStack(Blocks.REDSTONE_BLOCK),
    			(i < 3) ? new ItemStack(Items.DYE) : new ItemStack(Blocks.COAL_ORE)
	    	);
    	}

    	for(int i = 0; i < 4; i++){
    		GameRegistry.addShapelessRecipe(
	    		new ItemStack(this, 1, i + 4),
	    		(i == 0) ? new ItemStack(Items.PAPER) : new ItemStack(this, 1, i + 3),
    			new ItemStack(Items.NETHER_STAR),
    			(i < 1) ? new ItemStack(Items.ENDER_PEARL) : new ItemStack(Items.ENDER_EYE),
	    		(i < 2) ? new ItemStack(Items.GLOWSTONE_DUST) : new ItemStack(Blocks.GLOWSTONE),
	    		(i < 3) ? new ItemStack(Items.EMERALD) : new ItemStack(Blocks.EMERALD_ORE)
	    	);
    	}
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer player, EnumHand hand){
		RayTraceResult ray = this.rayTrace(world, player, true);
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK){
        	BlockPos pos = ray.getBlockPos();

            if (world.getBlockState(pos).getBlock() == Blocks.MOB_SPAWNER){
            	boolean result = false;
            	int meta = itemstack.getItemDamage();
            	TileEntity tile = world.getTileEntity(pos);
            	NBTTagCompound tag = new NBTTagCompound();
            	tile.writeToNBT(tag);
            	CustomSpawnerHelper helper = CustomSpawnerHelper.getInstanceFromNBTTag(tag);

            	if(		(meta == 0 && helper.getPowerCode() == 0) ||
            			(meta == 1 && helper.getPowerCode() == 1) ||
            			(meta == 2 && helper.getPowerCode() == 2) ||
            			(meta == 3 && helper.getPowerCode() == 3)){
            		helper.incrementPower();
                	player.addChatComponentMessage(new TextComponentString("Power Upgrade Successfull"));
                	player.addChatComponentMessage(new TextComponentString("- Current Power: " + helper.getPowerCode()));
                	result = true;
            	}else if(	(meta == 4 && helper.getSpeedCode() == 0) ||
            				(meta == 5 && helper.getSpeedCode() == 1) ||
            				(meta == 6 && helper.getSpeedCode() == 2) ||
            				(meta == 7 && helper.getSpeedCode() == 3)){
            		helper.incrementSpeed();
                	player.addChatComponentMessage(new TextComponentString("Speed Upgrade Successfull"));
                	player.addChatComponentMessage(new TextComponentString("- Current Speed: " + helper.getSpeedCode()));
                	result = true;
            	}

            	if(result){
	            	helper.writeToNBTTag(tag);
	            	tile.readFromNBT(tag);
	        		world.spawnEntityInWorld(new EntityLightningBolt(world, pos.getX(), pos.getY() + 1, pos.getZ(), true));
	            	--itemstack.stackSize;
            	}
            }
        }
		return new ActionResult(EnumActionResult.PASS, itemstack);
	}

    public String getUnlocalizedName(ItemStack itemstack)
    {
    	String name = "item.upgrade_power_i";
    	switch(itemstack.getItemDamage()){
    		case 0:
    			name = "item.upgrade_power_i";
    			break;
    		case 1:
    			name = "item.upgrade_power_ii";
    			break;
    		case 2:
    			name = "item.upgrade_power_iii";
    			break;
    		case 3:
    			name = "item.upgrade_power_iv";
    			break;
    		case 4:
    			name = "item.upgrade_speed_i";
    			break;
    		case 5:
    			name = "item.upgrade_speed_ii";
    			break;
    		case 6:
    			name = "item.upgrade_speed_iii";
    			break;
    		case 7:
    			name = "item.upgrade_speed_iv";
    			break;
    	}
        return name;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
    	for(int i = 0; i < SUB_BLOCK_VALUES; i++){
    		list.add(new ItemStack(item, 1, i));
    	}
    }

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation[] jsons = new ResourceLocation[SUB_BLOCK_VALUES];
    	jsons[0] = new ResourceLocation("effectivereactions:upgrade_power_i");
    	jsons[1] = new ResourceLocation("effectivereactions:upgrade_power_ii");
    	jsons[2] = new ResourceLocation("effectivereactions:upgrade_power_iii");
		jsons[3] = new ResourceLocation("effectivereactions:upgrade_power_iv");
		jsons[4] = new ResourceLocation("effectivereactions:upgrade_speed_i");
		jsons[5] = new ResourceLocation("effectivereactions:upgrade_speed_ii");
		jsons[6] = new ResourceLocation("effectivereactions:upgrade_speed_iii");
		jsons[7] = new ResourceLocation("effectivereactions:upgrade_speed_iv");

        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        ModelBakery.registerItemVariants(this, jsons);
        //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        for(int i = 0; i < jsons.length; i++){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(jsons[i], "inventory"));
        }
    }
}
