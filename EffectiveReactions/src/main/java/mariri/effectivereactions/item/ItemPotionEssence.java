package mariri.effectivereactions.item;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPotionEssence extends Item {
	public ItemPotionEssence(){
		super();
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName("potion_essence");
		GameRegistry.register(this, new ResourceLocation(EffectiveReactions.MODID, "potion_essence"));

		registerRecipe();
	}

    public String getUnlocalizedName(ItemStack itemstack)
    {
    	return "item.potion_essence." + itemstack.getItemDamage();
    }

    private void registerRecipe(){
		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 0), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR),
				new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 1),	new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR), new ItemStack(Items.SUGAR),
				new ItemStack(Items.SUGAR), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 2), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT),
				new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE),
				new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 3), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT),
				new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 4), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER),
				new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 5), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.SPECKLED_MELON),
				new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.SPECKLED_MELON),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 6), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.SPECKLED_MELON),
				new ItemStack(Items.SPECKLED_MELON), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 7), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.RABBIT_FOOT),
				new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.RABBIT_FOOT), new ItemStack(Items.RABBIT_FOOT),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 8), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FISH, 1, 3),
				new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 9), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.GHAST_TEAR),
				new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.GHAST_TEAR),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 10), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT),
				new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE),
				new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 11), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM),
				new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.MAGMA_CREAM),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 12), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FISH, 1, 3),
				new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.FISH, 1, 3),
				new ItemStack(Items.NETHER_WART), new ItemStack(Items.NETHER_WART));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 13), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.GOLDEN_CARROT),
				new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.EGG), new ItemStack(Items.EGG),
				new ItemStack(Items.EGG), new ItemStack(Items.EGG));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 14), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0),
				new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 15), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.GOLDEN_CARROT),
				new ItemStack(Items.GOLDEN_CARROT), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 16), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH),
				new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH),
				new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.ROTTEN_FLESH));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 17), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER),
				new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 18), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.POISONOUS_POTATO),
				new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SPIDER_EYE),
				new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 19), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Blocks.COAL_BLOCK),
				new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 20), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Blocks.PUMPKIN), new ItemStack(Blocks.PUMPKIN), new ItemStack(Blocks.PUMPKIN),
				new ItemStack(Blocks.PUMPKIN), new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE),
				new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 21), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.GOLDEN_APPLE),
				new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE),
				new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 22), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.PORKCHOP), new ItemStack(Items.PORKCHOP), new ItemStack(Items.BEEF),
				new ItemStack(Items.BEEF), new ItemStack(Items.CHICKEN), new ItemStack(Items.CHICKEN),
				new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 23), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.GLOWSTONE_DUST),
				new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.EGG), new ItemStack(Items.EGG),
				new ItemStack(Items.EGG), new ItemStack(Items.EGG));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 24), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.FEATHER), new ItemStack(Items.FEATHER), new ItemStack(Items.FEATHER),
				new ItemStack(Items.FEATHER), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 25), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.DRAGON_BREATH),
				new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE),
				new ItemStack(Items.COOKIE), new ItemStack(Items.COOKIE));

		GameRegistry.addShapelessRecipe(
				new ItemStack(this, 1, 26), new ItemStack(Items.MUSHROOM_STEW),
				new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.DRAGON_BREATH),
				new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE),
				new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE));

    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
    	for(int i = 0; i < EffectiveReactions.POTION_COUNT; i++){
    		list.add(new ItemStack(item, 1, i));
    	}
    }

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation[] jsons = new ResourceLocation[EffectiveReactions.POTION_COUNT];
    	for(int i = 0; i < jsons.length; i++){
        	jsons[i] = new ResourceLocation("effectivereactions:potion_essence_" + i);
    	}

        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        ModelBakery.registerItemVariants(this, jsons);
        //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        for(int i = 0; i < jsons.length; i++){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, new ModelResourceLocation(jsons[i], "inventory"));
        }
    }
}
