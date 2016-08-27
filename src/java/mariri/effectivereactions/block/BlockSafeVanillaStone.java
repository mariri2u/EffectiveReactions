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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BlockSafeVanillaStone extends BlockSafeStoneBase implements IBlockSafeStone {

	public static int SUB_BLOCK_VALUES = 15;

    //BlockState用Property変数。今回はmetadataと同じようなPropertyIntegerを用いる。
    public static final PropertyInteger METADATA = PropertyInteger.create("meta", 0, SUB_BLOCK_VALUES - 1);;

//	protected String name;
	protected ResourceLocation name;

	public BlockSafeVanillaStone(){
		super();
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setSoundType(SoundType.STONE);
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
    	this.name = new ResourceLocation(EffectiveReactions.MODID, "safe_vanilla_stone");
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

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation[] jsons = new ResourceLocation[SUB_BLOCK_VALUES];
    	jsons[0] = new ResourceLocation("cobblestone");
    	jsons[1] = new ResourceLocation("stone");
    	jsons[2] = new ResourceLocation("stonebrick");
    	jsons[3] = new ResourceLocation("brick_block");
    	jsons[4] = new ResourceLocation("nether_brick");
    	jsons[5] = new ResourceLocation("sandstone");
    	jsons[6] = new ResourceLocation("smooth_sandstone");
    	jsons[7] = new ResourceLocation("red_sandstone");
    	jsons[8] = new ResourceLocation("smooth_red_sandstone");
    	jsons[9] = new ResourceLocation("granite");
    	jsons[10] = new ResourceLocation("granite_smooth");
    	jsons[11] = new ResourceLocation("diorite");
    	jsons[12] = new ResourceLocation("diorite_smooth");
    	jsons[13] = new ResourceLocation("andesite");
    	jsons[14] = new ResourceLocation("andesite_smooth");
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

//        OreDictionary.registerOre("cobblestone", new ItemStack(this, 1, 0));
//        OreDictionary.registerOre("stone", new ItemStack(this, 1, 1));
//        OreDictionary.registerOre("stonebrick", new ItemStack(this, 1, 2));
	}

	private void registerRecipe(){
		// cobblestone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 0),
        		"XXX", "XYX", "XXX",
        		'X', "cobblestone",
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 0),
        		"XXX", "XYX", "XXX",
        		'X', "safeCobblestone",
        		'Y', "nuggetGold"
        	));

        // stone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 1),
        		"XXX", "XYX", "XXX",
        		'X', "stone",
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 1),
        		"XXX", "XYX", "XXX",
        		'X', "safeStone",
        		'Y', "nuggetGold"
        	));

        GameRegistry.addSmelting(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 0);

        // stonebrick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 2),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONEBRICK),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 2),
        		"XXX", "XYX", "XXX",
        		'X', "safeStonebrick",
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 2),
        	new Object[] { "XX", "XX",
        		'X', new ItemStack(this, 1, 1)
        	});

        // brick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 3),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.BRICK_BLOCK),
        		'Y', "nuggetGold"
        	));

        // nether brick
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 4),
        		"XXX", "XYX", "XXX",
            	'X', new ItemStack(Blocks.NETHER_BRICK),
            	'Y', "nuggetGold"
            ));

		// sandstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 5),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.SANDSTONE),
        		'Y', "nuggetGold"
        	));

		// smooth sandstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 6),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.SANDSTONE, 1, 2),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 6),
            	new Object[] { "XX", "XX",
            		'X', new ItemStack(this, 1, 5)
            	});

		// red sandstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 7),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.RED_SANDSTONE),
        		'Y', "nuggetGold"
        	));

		// smooth red sandstone
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 8),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.RED_SANDSTONE, 1, 2),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 8),
            	new Object[] { "XX", "XX",
            		'X', new ItemStack(this, 1, 7)
            	});

		// granite
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 9),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONE, 1, 1),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ShapelessOreRecipe( new ItemStack(this, 1, 9),
        		new ItemStack(this, 1, 11),
        		"gemQuartz"
        	));

		// polished granite
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 10),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONE, 1, 2),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 10),
            	new Object[] { "XX", "XX",
            		'X', new ItemStack(this, 1, 9)
            	});

		// diorite
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 11),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONE, 1, 3),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 2, 11),
        		"YX", "XY",
        		'X', "safeCobblestone",
        		'Y', "gemQuartz"
        	));

		// polished diorite
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 12),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONE, 1, 4),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 12),
            	new Object[] { "XX", "XX",
            		'X', new ItemStack(this, 1, 11)
            	});

		// andesite
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 13),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONE, 1, 5),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ShapelessOreRecipe( new ItemStack(this, 2, 13),
        		"safeStone",
        		"safeCobblestone"
        	));

		// polished andesite
        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(this, 8, 14),
        		"XXX", "XYX", "XXX",
        		'X', new ItemStack(Blocks.STONE, 1, 6),
        		'Y', "nuggetGold"
        	));

        GameRegistry.addRecipe( new ItemStack(this, 4, 14),
            	new Object[] { "XX", "XX",
            		'X', new ItemStack(this, 1, 13)
            	});
    }
}
