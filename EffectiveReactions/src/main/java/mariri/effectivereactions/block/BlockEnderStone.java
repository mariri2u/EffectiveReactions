package mariri.effectivereactions.block;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.item.ItemBlockSafeStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockEnderStone extends BlockSafeStoneBase implements IBlockSafeStone {
	public static int SUB_BLOCK_VALUES = 4;
	//BlockState用Property変数。今回はmetadataと同じようなPropertyIntegerを用いる。
	public static PropertyInteger METADATA = PropertyInteger.create("meta", 0, SUB_BLOCK_VALUES - 1);
	protected ResourceLocation name;

	public BlockEnderStone(){
		super();
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setSoundType(SoundType.STONE)	;
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
		this.name = new ResourceLocation(EffectiveReactions.MODID, "ender_stone");
//		this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
		this.setUnlocalizedName(name.getResourcePath());

//		GameRegistry.registerBlock(this, ItemBlockSafeStone.class, name);
		GameRegistry.register(this, name);
		GameRegistry.register(new ItemBlockSafeStone(this), name);

		registerOreDictionary();
		registerRecipe();
	}

    @Override
	public int damageDropped(IBlockState state){
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == 1){
			return 0;
		}else{
			return meta;
		}
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

	protected void registerRecipe(){
		// black
		// cobblestone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 0),
        		"XXX", "XYX", "XXX",
        		'X', "cobblestone",
        		'Y', new ItemStack(Items.ENDER_PEARL, 1)
        	));

        // stone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 1),
        		"XXX", "XYX", "XXX",
        		'X', "stone",
        		'Y', new ItemStack(Items.ENDER_PEARL, 1)
        	));

        GameRegistry.addSmelting(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 0);

        // stonebrick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 2),
        		"XXX", "XYX", "XXX",
        		'X', "stonebrick",
        		'Y', new ItemStack(Items.ENDER_PEARL, 1)
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 2),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 1)
        	});

        // smoothstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 3),
        		"XXX", "XYX", "XXX",
        		'X', "smoothstone",
        		'Y', new ItemStack(Items.ENDER_PEARL, 1)
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 3),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 2)
        	});
	}

	protected void registerOreDictionary(){
        OreDictionary.registerOre("cobblestone", new ItemStack(this, 1, 0));
        OreDictionary.registerOre("stone", new ItemStack(this, 1, 1));
        OreDictionary.registerOre("stonebrick", new ItemStack(this, 1, 2));
        OreDictionary.registerOre("smoothstone", new ItemStack(this, 1, 15));
	}

	@SideOnly(Side.CLIENT)
    public void registerTextures(){
		ResourceLocation[] jsons = new ResourceLocation[SUB_BLOCK_VALUES];
		jsons[0] = new ResourceLocation("effectivereactions:ender_cobblestone");
		jsons[1] = new ResourceLocation("effectivereactions:ender_stone");
		jsons[2] = new ResourceLocation("effectivereactions:ender_stonebrick");
		jsons[3] = new ResourceLocation("effectivereactions:ender_smoothstone");
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
}
