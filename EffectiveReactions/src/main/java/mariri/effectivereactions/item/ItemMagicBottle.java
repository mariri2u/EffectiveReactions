package mariri.effectivereactions.item;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.block.BlockFluidPotion;
import mariri.effectivereactions.helper.CustomPotionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagicBottle extends Item {

	public ItemMagicBottle(int stackSize){
		this.setMaxStackSize(stackSize);
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName("magic_bottle");
		GameRegistry.register(this, new ResourceLocation(EffectiveReactions.MODID, "magic_bottle"));

		GameRegistry.addShapelessRecipe(new ItemStack(this), Items.GLASS_BOTTLE, Items.REDSTONE, Items.DYE);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand){
		RayTraceResult ray = this.rayTrace(world, player, true);
        if (ray == null) { return new ActionResult(EnumActionResult.PASS, itemStack); }
        else{
            if (ray.typeOfHit == RayTraceResult.Type.BLOCK){
            	BlockPos pos = ray.getBlockPos();

                if (!world.canMineBlockBody(player, pos)){ return new ActionResult(EnumActionResult.PASS, itemStack); }
                if (!player.canPlayerEdit(pos, ray.sideHit, itemStack)){ return new ActionResult(EnumActionResult.PASS, itemStack); }
                if (world.getBlockState(pos).getBlock() instanceof BlockFluidPotion){
                	--itemStack.stackSize;

                	BlockFluidPotion fluid = (BlockFluidPotion)world.getBlockState(pos).getBlock();

                	ItemStack potion = CustomPotionHelper.getSampleItem(fluid.getPotionEffect(), 0, 0, false);

//                	CustomPotionHelper helper = new CustomPotionHelper(fluid.getPotionEffect());
//                    ItemStack potion = new ItemStack(Items.potionitem, 1, CustomPotionHelper.metadataTable[fluid.getPotionEffect() - 1][0]);
//
//                    NBTTagCompound tag = CustomPotionHelper.createVoidNBTTag();
//                    helper.writeNBTTag(CustomPotionHelper.findPotionNBT(tag));
//                    potion.setTagCompound(tag);

//                    NBTTagCompound effect = new NBTTagCompound();
//                    NBTTagList customPotionEffect = new NBTTagList();
//                    NBTTagCompound tag = new NBTTagCompound();
//                    new PotionEffect(fluid.getPotionEffect(), 1200, 0, false).writeCustomPotionEffectToNBT(effect);
//                    customPotionEffect.appendTag(effect);
//                    tag.setTag("CustomPotionEffects", customPotionEffect);
//                    potion.setTagCompound(tag);

                    world.setBlockToAir(pos);

                    if (itemStack.stackSize <= 0){ return new ActionResult(EnumActionResult.PASS, potion); }

                    if (!player.inventory.addItemStackToInventory(potion)){
                        player.dropItem(potion, false);
                    }
                }
            }
            return new ActionResult(EnumActionResult.PASS, itemStack);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation json = new ResourceLocation("effectivereactions:magic_bottle");
        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        ModelBakery.registerItemVariants(this, json);
        //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(json, "inventory"));
    }
}
