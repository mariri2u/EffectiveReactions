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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BlockSafeColorStone extends BlockSafeStoneBase implements IBlockSafeStone {

	public static final int SUB_BLOCK_VALUES = 16;

    //BlockState用Property変数。今回はmetadataと同じようなPropertyIntegerを用いる。
    public static final PropertyInteger METADATA = PropertyInteger.create("meta", 0, SUB_BLOCK_VALUES - 1);

//	protected String name;
	protected ResourceLocation name;

	public BlockSafeColorStone(){
		super();
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setSoundType(SoundType.STONE);
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
		this.name = new ResourceLocation(EffectiveReactions.MODID, "safe_color_stone");
//    	this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
    	this.setUnlocalizedName(name.getResourcePath());

//        GameRegistry.registerBlock(this, ItemBlockSafeStone.class, name);
		GameRegistry.register(this, name);
		GameRegistry.register(new ItemBlockSafeStone(this), name);

		registerOreDictionary();
        registerRecipe();
    }

    @Override
    public int damageDropped(IBlockState state){
    	int meta = state.getBlock().getMetaFromState(state);
    	if(meta % 4 == 1){
    		return meta - 1;
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

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation[] jsons = new ResourceLocation[SUB_BLOCK_VALUES];
    	jsons[0] = new ResourceLocation("effectivereactions:black_cobblestone");
    	jsons[1] = new ResourceLocation("effectivereactions:black_stone");
    	jsons[2] = new ResourceLocation("effectivereactions:black_stonebrick");
    	jsons[3] = new ResourceLocation("effectivereactions:black_smoothstone");
    	jsons[4] = new ResourceLocation("effectivereactions:red_cobblestone");
    	jsons[5] = new ResourceLocation("effectivereactions:red_stone");
    	jsons[6] = new ResourceLocation("effectivereactions:red_stonebrick");
    	jsons[7] = new ResourceLocation("effectivereactions:red_smoothstone");
    	jsons[8] = new ResourceLocation("effectivereactions:blue_cobblestone");
    	jsons[9] = new ResourceLocation("effectivereactions:blue_stone");
    	jsons[10] = new ResourceLocation("effectivereactions:blue_stonebrick");
    	jsons[11] = new ResourceLocation("effectivereactions:blue_smoothstone");
    	jsons[12] = new ResourceLocation("effectivereactions:white_cobblestone");
    	jsons[13] = new ResourceLocation("effectivereactions:white_stone");
    	jsons[14] = new ResourceLocation("effectivereactions:white_stonebrick");
    	jsons[15] = new ResourceLocation("effectivereactions:white_smoothstone");
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

	private void registerOreDictionary(){
        OreDictionary.registerOre("safeCobblestone", new ItemStack(this, 1, 0));
        OreDictionary.registerOre("safeStone", new ItemStack(this, 1, 1));
        OreDictionary.registerOre("safeStonebrick", new ItemStack(this, 1, 2));
        OreDictionary.registerOre("safeSmoothstone", new ItemStack(this, 1, 3));
        OreDictionary.registerOre("safeCobblestone", new ItemStack(this, 1, 4));
        OreDictionary.registerOre("safeStone", new ItemStack(this, 1, 5));
        OreDictionary.registerOre("safeStonebrick", new ItemStack(this, 1, 6));
        OreDictionary.registerOre("safeSmoothstone", new ItemStack(this, 1, 7));
        OreDictionary.registerOre("safeCobblestone", new ItemStack(this, 1, 8));
        OreDictionary.registerOre("safeStone", new ItemStack(this, 1, 9));
        OreDictionary.registerOre("safeStonebrick", new ItemStack(this, 1, 10));
        OreDictionary.registerOre("safeSmoothstone", new ItemStack(this, 1, 11));
        OreDictionary.registerOre("safeCobblestone", new ItemStack(this, 1, 12));
        OreDictionary.registerOre("safeStone", new ItemStack(this, 1, 13));
        OreDictionary.registerOre("safeStonebrick", new ItemStack(this, 1, 14));
        OreDictionary.registerOre("safeSmoothstone", new ItemStack(this, 1, 15));

//        OreDictionary.registerOre("cobblestone", new ItemStack(this, 1, 0));
//        OreDictionary.registerOre("stone", new ItemStack(this, 1, 1));
//        OreDictionary.registerOre("stonebrick", new ItemStack(this, 1, 2));
//        OreDictionary.registerOre("smoothstone", new ItemStack(this, 1, 3));
//        OreDictionary.registerOre("cobblestone", new ItemStack(this, 1, 4));
//        OreDictionary.registerOre("stone", new ItemStack(this, 1, 5));
//        OreDictionary.registerOre("stonebrick", new ItemStack(this, 1, 6));
//        OreDictionary.registerOre("smoothstone", new ItemStack(this, 1, 7));
//        OreDictionary.registerOre("cobblestone", new ItemStack(this, 1, 8));
//        OreDictionary.registerOre("stone", new ItemStack(this, 1, 9));
//        OreDictionary.registerOre("stonebrick", new ItemStack(this, 1, 10));
//        OreDictionary.registerOre("smoothstone", new ItemStack(this, 1, 11));
//        OreDictionary.registerOre("cobblestone", new ItemStack(this, 1, 12));
//        OreDictionary.registerOre("stone", new ItemStack(this, 1, 13));
//        OreDictionary.registerOre("stonebrick", new ItemStack(this, 1, 14));
//        OreDictionary.registerOre("smoothstone", new ItemStack(this, 1, 15));
	}

	private void registerRecipe(){
		// black
		// cobblestone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 0),
        		"XXX", "XYX", "XXX",
        		'X', "cobblestone",
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 0),
        		"XXX", "XYX", "XXX",
        		'X', "safeCobblestone",
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

        // stone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 1),
        		"XXX", "XYX", "XXX",
        		'X', "safeStone",
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

         GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 1),
        		"XXX", "XYX", "XXX",
        		'X', "stone",
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

        GameRegistry.addSmelting(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 0);

        // stonebrick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 2),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONEBRICK),
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 2),
        		"XXX", "XYX", "XXX",
        		'X', "safeStonebrick",
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 2),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 1)
        	});

        // smoothstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 3),
        		"XXX", "XYX", "XXX",
        		'X', "safeSmoothstone",
        		'Y', new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE)
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 3),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 2)
        	});

		// red
		// cobblestone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 4),
        		"XXX", "XYX", "XXX",
        		'X', "cobblestone",
        		'Y', "dustRedstone"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 4),
        		"XXX", "XYX", "XXX",
        		'X', "safeCobblestone",
        		'Y', "dustRedstone"
        	));

        // stone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 5),
        		"XXX", "XYX", "XXX",
        		'X', "stone",
        		'Y', "dustRedstone"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 5),
        		"XXX", "XYX", "XXX",
        		'X', "safeStone",
        		'Y', "dustRedstone"
        	));

        GameRegistry.addSmelting(new ItemStack(this, 1, 4), new ItemStack(this, 1, 5), 0);

        // stonebrick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 6),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONEBRICK),
        		'Y', "dustRedstone"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 6),
        		"XXX", "XYX", "XXX",
        		'X', "safeStonebrick",
        		'Y', "dustRedstone"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 6),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 5)
        	});

        // smoothstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 7),
        		"XXX", "XYX", "XXX",
        		'X', "safeSmoothstone",
        		'Y', "dustRedstone"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 7),
        	new Object[] { "XX", "XX",
				'X', new ItemStack(this, 1, 6)
        	});

		// blue
		// cobblestone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 8),
        		"XXX", "XYX", "XXX",
        		'X', "cobblestone",
        		'Y', "gemLapis"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 8),
        		"XXX", "XYX", "XXX",
        		'X', "safeCobblestone",
        		'Y', "gemLapis"
        	));

        // stone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 9),
        		"XXX", "XYX", "XXX",
        		'X', "stone",
        		'Y', "gemLapis"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 9),
        		"XXX", "XYX", "XXX",
        		'X', "safeStone",
        		'Y', "gemLapis"
        	));

        GameRegistry.addSmelting(new ItemStack(this, 1, 8), new ItemStack(this, 1, 9), 0);

        // stonebrick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 10),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONEBRICK),
        		'Y', "gemLapis"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 10),
        		"XXX", "XYX", "XXX",
        		'X', "safeStonebrick",
        		'Y', "gemLapis"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 10),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 9)
        	});

        // smoothstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 11),
        		"XXX", "XYX", "XXX",
        		'X', "safeSmoothstone",
        		'Y', "gemLapis"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 11),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 10)
        	});

		// white
		// cobblestone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 12),
        		"XXX", "XYX", "XXX",
        		'X', "cobblestone",
        		'Y', "gemQuartz"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 12),
        		"XXX", "XYX", "XXX",
        		'X', "safeCobblestone",
        		'Y', "gemQuartz"
        	));

        // stone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 13),
        		"XXX", "XYX", "XXX",
        		'X', "stone",
        		'Y', "gemQuartz"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 13),
        		"XXX", "XYX", "XXX",
        		'X', "safeStone",
        		'Y', "gemQuartz"
        	));

        GameRegistry.addSmelting(new ItemStack(this, 1, 12), new ItemStack(this, 1, 13), 0);

        // stonebrick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 14),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONEBRICK),
        		'Y', "gemQuartz"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 14),
        		"XXX", "XYX", "XXX",
        		'X', "safeStonebrick",
        		'Y', "gemQuartz"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 14),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 13)
        	});

        // smoothstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 15),
        		"XXX", "XYX", "XXX",
        		'X', "safeSmoothstone",
        		'Y', "gemQuartz"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 15),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 14)
        	});
	}
}
