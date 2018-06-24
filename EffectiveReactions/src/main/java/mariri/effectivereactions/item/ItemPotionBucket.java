package mariri.effectivereactions.item;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.block.BlockFluidPotion;
import mariri.effectivereactions.helper.CustomPotionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPotionBucket extends ItemBucket {

	private String iconName;
	private BlockFluidPotion fluid;
//	public List<BlockFluidPotion> fluids = new ArrayList<BlockFluidPotion>();
	public ItemPotionBucket(BlockFluidPotion block){
		super(block);
		fluid = block;
		int id = Potion.getIdFromPotion(block.getPotionEffect());

		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName("bucket_potion_" + id);
		this.setContainerItem(Items.BUCKET);
		GameRegistry.register(this, new ResourceLocation(EffectiveReactions.MODID, "bucket_potion_" + Potion.getIdFromPotion(block.getPotionEffect())));

        GameRegistry.addShapelessRecipe(new ItemStack(this), new ItemStack(Items.WATER_BUCKET), inputItems[id - 1][0], inputItems[id - 1][1], inputItems[id - 1][2]);
	}

	public Potion getPotionEffect(){
		return fluid.getPotionEffect();
	}

//	@Override
//	public boolean tryPlaceContainedLiquid(World world, int x, int y, int z){
//		boolean result = super.tryPlaceContainedLiquid(world, x, y, z);
//		if(result){
//			for(BlockFluidPotion fluid : fluids){
////				if(fluid.getPotionEffect() == )
//			}
//		}
////		world.setBlockMetadataWithNotify(x, y, z, 3, 4);
//		return result;
//	}

    @SideOnly(Side.CLIENT)
    public void registerTextures(int i){
    	ResourceLocation json = new ResourceLocation("effectivereactions:bucket_potion_" + i);
        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        ModelBakery.registerItemVariants(this, json);
        //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(json, "inventory"));
    }

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List tooltip, boolean par4) {
		if(fluid instanceof BlockFluidPotion){
			String name = I18n.translateToLocal(fluid.getPotionEffect().getName());
			tooltip.add(name);
			tooltip.add(
					CustomPotionHelper.isSupport(fluid.getPotionEffect()) ? " - Good Status" :
					CustomPotionHelper.isNeutral(fluid.getPotionEffect()) ? " - Neutral Status"  : " - Bad Status");
		}
	}

	public static final ItemStack[][] inputItems = new ItemStack[][]{
		new ItemStack[]{ new ItemStack(Items.SUGAR), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.SUGAR), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.IRON_INGOT), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) },
		new ItemStack[]{ new ItemStack(Blocks.PUMPKIN), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.PORKCHOP), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.FEATHER), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.NETHER_WART), new ItemStack(Items.COOKIE) },
		new ItemStack[]{ new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.NETHER_WART), new ItemStack(Items.FERMENTED_SPIDER_EYE) }
	};
}
