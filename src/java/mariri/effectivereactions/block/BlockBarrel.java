package mariri.effectivereactions.block;

import java.util.List;

import javax.annotation.Nullable;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.ModRegistry;
import mariri.effectivereactions.item.ItemBlockBarrel;
import mariri.effectivereactions.recipe.BarrelRecipe;
import mariri.effectivereactions.recipe.BarrelRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockBarrel extends Block{
	public static int SUB_BLOCK_VALUES = 29;
	//BlockState用Property変数。今回はmetadataと同じようなPropertyIntegerを用いる。
	public static PropertyInteger METADATA = PropertyInteger.create("meta", 0, SUB_BLOCK_VALUES - 1);
	protected ResourceLocation name;

	public BlockBarrel() {
		super(Material.WOOD);
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setSoundType(SoundType.WOOD);
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
		this.name = new ResourceLocation(EffectiveReactions.MODID, "barrel");
		this.setUnlocalizedName(name.getResourcePath());

		GameRegistry.register(this, name);
		GameRegistry.register(new ItemBlockBarrel(this), name);

		registerRecipe();

	}

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	int meta = this.getMetaFromState(state);
    	if(heldItem == null){ return false; }

    	BarrelRecipe recipe = BarrelRecipeManager.getInstance().match(heldItem, state);
    	if(recipe == null){ return false; }

    	ItemStack output = recipe.getOutput();
    	IBlockState next = recipe.getNext();

    	world.setBlockState(pos, next);

    	if(recipe.getOutput() == null){
    		heldItem.stackSize--;
    	}else{
    		if(heldItem.stackSize > 1){
    			heldItem.stackSize--;
    			player.inventory.addItemStackToInventory(output);
    		}else{
    			player.inventory.mainInventory[player.inventory.currentItem] = output;
	   		}
		}

        return true;
    }

	//ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(METADATA, meta);
	}

	//IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer)state.getValue(METADATA);
	}

	//初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, METADATA);
	}

	private void registerRecipe(){
		// Block Recipe
        GameRegistry.addRecipe(
        		new ShapedOreRecipe( new ItemStack(this, 1, 1),
        		"X X", "XWX", "XXX",
        		'X', "plankWood",
        		'W',  new ItemStack(Items.WATER_BUCKET)
        	));

        // Potion Recipe
        ModRegistry.addBarrelRecipe(Items.WATER_BUCKET, this.getStateFromMeta(0), this.getStateFromMeta(1), Items.BUCKET);
        ModRegistry.addBarrelRecipe(Items.BUCKET, this.getStateFromMeta(1), this.getStateFromMeta(0), Items.WATER_BUCKET);

        for(int i = 0; i < EffectiveReactions.POTION_COUNT; i++){
        	ItemStack output = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, EffectiveReactions.fluidPotions[i]);
        	ModRegistry.addBarrelRecipe(Items.BUCKET, this.getStateFromMeta(i + 2), this.getStateFromMeta(0), output);

        	ModRegistry.addBarrelRecipe(new ItemStack(EffectiveReactions.itemPotionEssence, 1, i), this.getStateFromMeta(1), this.getStateFromMeta(i + 2), null);
        }
	}

	@SideOnly(Side.CLIENT)
    public void registerTextures(){
		ResourceLocation[] jsons = new ResourceLocation[SUB_BLOCK_VALUES];
		jsons[0] = new ResourceLocation("effectivereactions:barrel_0");
		jsons[1] = new ResourceLocation("effectivereactions:barrel_1");
		jsons[2] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[3] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[4] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[5] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[6] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[7] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[8] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[9] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[10] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[11] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[12] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[13] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[14] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[15] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[16] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[17] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[18] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[19] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[20] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[21] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[22] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[23] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[24] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[25] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[26] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[27] = new ResourceLocation("effectivereactions:barrel_2");
		jsons[28] = new ResourceLocation("effectivereactions:barrel_2");
		//モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
		//ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
		ModelBakery.registerItemVariants(Item.getItemFromBlock(this), jsons);
		//1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
		for(int i = 0; i < jsons.length; i++){
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), i, new ModelResourceLocation(jsons[i], "inventory"));
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		for(int i = 0; i < SUB_BLOCK_VALUES; i++){
			list.add(new ItemStack(item, 1, i));
		}
	}

//	public static class RecipeSet{
//		public Item ITEM;
//		public Potion EFFECT;
//		public RecipeSet(Item item, Potion effect){
//			this.ITEM = item;
//			this.EFFECT = effect;
//		}
//	}
//
//	public static final RecipeSet[] recipeSet = new RecipeSet[] {
//			new RecipeSet(Items.SUGAR, MobEffects.SPEED),
//			new RecipeSet(Items.BOOK, MobEffects.SLOWNESS),
//			new RecipeSet(Items.CARROT, MobEffects.HASTE),
//			new RecipeSet(Items.STICK, MobEffects.MINING_FATIGUE),
//			new RecipeSet(Items.COMPASS, MobEffects.STRENGTH),
//			new RecipeSet(Items.GOLDEN_CARROT, MobEffects.INSTANT_HEALTH),
//			new RecipeSet(Items.GUNPOWDER, MobEffects.INSTANT_DAMAGE),
//			new RecipeSet(Items.RABBIT_FOOT, MobEffects.JUMP_BOOST),
//			new RecipeSet(Items.BONE, MobEffects.NAUSEA),
//			new RecipeSet(Items.MELON, MobEffects.REGENERATION),
//			new RecipeSet(Items.IRON_INGOT, MobEffects.RESISTANCE),
//			new RecipeSet(Items.MAGMA_CREAM, MobEffects.FIRE_RESISTANCE),
//			new RecipeSet(Items.BAKED_POTATO, MobEffects.WATER_BREATHING),
//			new RecipeSet(Items.ARROW, MobEffects.INVISIBILITY),
//			new RecipeSet(Items.FLINT, MobEffects.BLINDNESS),
//			new RecipeSet(Items.EMERALD, MobEffects.NIGHT_VISION),
//			new RecipeSet(Items.ROTTEN_FLESH, MobEffects.HUNGER),
//			new RecipeSet(Items.FERMENTED_SPIDER_EYE, MobEffects.WEAKNESS),
//			new RecipeSet(Items.SPIDER_EYE, MobEffects.POISON),
//			new RecipeSet(Items.COAL, MobEffects.WITHER),
//			new RecipeSet(Items.GOLDEN_APPLE, MobEffects.HEALTH_BOOST),
//			new RecipeSet(Items.GOLD_NUGGET, MobEffects.ABSORPTION),
//			new RecipeSet(Items.BEEF, MobEffects.SATURATION),
//			new RecipeSet(Items.GLOWSTONE_DUST, MobEffects.GLOWING),
//			new RecipeSet(Items.FEATHER, MobEffects.LEVITATION),
//			new RecipeSet(Items.REDSTONE, MobEffects.LUCK),
//			new RecipeSet(Items.BOWL, MobEffects.UNLUCK)
//	};
}
