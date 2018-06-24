package mariri.effectivereactions;


import mariri.effectivereactions.block.BlockBarrel;
import mariri.effectivereactions.block.BlockEnderStone;
import mariri.effectivereactions.block.BlockFluidPotion;
import mariri.effectivereactions.block.BlockSafeColorStone;
import mariri.effectivereactions.block.BlockSafeVanillaStone;
import mariri.effectivereactions.block.BlockSemiSolidPotion;
import mariri.effectivereactions.block.MaterialPotion;
import mariri.effectivereactions.handler.BehaviorDispenseMagicGlass;
import mariri.effectivereactions.handler.CheckSpawnEventHandler;
import mariri.effectivereactions.handler.FillBucketHandler;
import mariri.effectivereactions.handler.PlayerClickHandler;
import mariri.effectivereactions.handler.TooltipHandler;
import mariri.effectivereactions.item.ItemMagicBottle;
import mariri.effectivereactions.item.ItemPotionBucket;
import mariri.effectivereactions.item.ItemPotionEssence;
import mariri.effectivereactions.item.ItemSpawnerScanner;
import mariri.effectivereactions.item.ItemSpawnerUpgrade;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = EffectiveReactions.MODID, version = EffectiveReactions.VERSION, useMetadata = true)
public class EffectiveReactions
{
    public static final String MODID = "EffectiveReactions";
    public static final String VERSION = "1.10.2-0.1-dev3";

    public static BlockSafeVanillaStone blockSafeVanillaStone;
    public static BlockSafeColorStone blockSafeColorStone;
    public static BlockEnderStone blockEnderStone;


    public static BlockFluidPotion[] blockFluidPotions;
    public static BlockSemiSolidPotion blockSemiSolidPotion;

    public static BlockBarrel blockBarrel;

    public static Fluid[] fluidPotions;
//    public static ItemPotionBucket[] itemPotionBuckets;
//    public static ItemMagicBucket itemMagicBucket;
    public static ItemMagicBottle itemMagicBottle;
    public static ItemSpawnerUpgrade itemSpawnerUpgrade;

    public static ItemPotionEssence itemPotionEssence;
    public static ItemSpawnerScanner itemSpawnerScanner;

    public static int POTION_COUNT = 27;

    public static CreativeTabs creativeTab;

    public static Material potionMaterial;

    public static boolean ENABLE_REACT_EXPLOSION;
    public static int REACT_EXPLOSION_POWER;
    public static boolean ENABLE_REACT_SPAWN;
    public static boolean ENABLE_INFINITY_SOURCE;
    public static boolean DISPENSE_MAGIC_BUCKET;
    public static int POTION_STACK_SIZE;

    public static boolean SHOW_SPAWNER_DETAILS;

    public EffectiveReactions() {
        FluidRegistry.enableUniversalBucket();
	}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	initConfig(new Configuration(event.getSuggestedConfigurationFile()));
//    	initBlocks();
//        //テクスチャ・モデル指定JSONファイル名の登録。
//        if (event.getSide().isClient()) {
//        	initTexture();
//        }
    	creativeTab = new CreativeEffectiveReactions("Effective Reaction");

    	initFluid(event.getSide().isClient());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	initBlocks();

        //テクスチャ・モデル指定JSONファイル名の登録。
        if (event.getSide().isClient()) {
        	initTexture();
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	initEventHandler();
    }

    private void initConfig(Configuration config){
        config.load();

        ModRegistry.INVERT_SPAWN_RULE = config.get(Configuration.CATEGORY_GENERAL, "trapMode", false).getBoolean(false);
//        INVERT_SPAWNER_RULE = config.get(Configuration.CATEGORY_GENERAL, "invertSpawnerRule", false).getBoolean(false);

        ENABLE_REACT_EXPLOSION = config.get(Configuration.CATEGORY_GENERAL, "enableReactExplosion", true).getBoolean(true);
        REACT_EXPLOSION_POWER = config.get(Configuration.CATEGORY_GENERAL, "reactExplosionPower", 3).getInt();
        ENABLE_REACT_SPAWN = config.get(Configuration.CATEGORY_GENERAL, "enableReactSpawn", true).getBoolean(true);
        ENABLE_INFINITY_SOURCE = config.get(Configuration.CATEGORY_GENERAL, "enableInfinitySource", true).getBoolean(true);
        DISPENSE_MAGIC_BUCKET = config.get(Configuration.CATEGORY_GENERAL, "dispenseMagicBucket", false).getBoolean(false);
        POTION_STACK_SIZE = config.get(Configuration.CATEGORY_GENERAL, "potionStackSize", 8).getInt();

        SHOW_SPAWNER_DETAILS = config.get(Configuration.CATEGORY_GENERAL, "showSpawnerDetails", false).getBoolean(false);

        config.save();
    }

    private void initFluid(boolean client){
        fluidPotions = new Fluid[POTION_COUNT];
        potionMaterial = new MaterialPotion();
        blockFluidPotions = new BlockFluidPotion[POTION_COUNT];
//        itemPotionBuckets = new ItemPotionBucket[POTION_COUNT];

        for(int i = 0; i < POTION_COUNT; i++){
        	fluidPotions[i] = new Fluid(
        				"fluid_potion_" + (i + 1),
        				new ResourceLocation(EffectiveReactions.MODID, "blocks/fluid_potion_" + (i + 1) + "_still"),
        				new ResourceLocation(EffectiveReactions.MODID, "blocks/fluid_potion_" + (i + 1) + "_flow")
//        				new ResourceLocation("blocks/water_still"),
//        				new ResourceLocation("blocks/water_flow")
        			).setDensity(800).setViscosity(1000);
        	FluidRegistry.registerFluid(fluidPotions[i]);
    		FluidRegistry.addBucketForFluid(fluidPotions[i]);

        	blockFluidPotions[i] = new BlockFluidPotion(fluidPotions[i], Material.WATER, Potion.getPotionById(i + 1));
//        	itemPotionBuckets[i] = new ItemPotionBucket(blockFluidPotions[i]);

//        	FluidContainerRegistry.registerFluidContainer(fluidPotions[i], new ItemStack(itemPotionBuckets[i]), FluidContainerRegistry.EMPTY_BUCKET);

//        	FillBucketHandler.INSTANCE.buckets.put(blockFluidPotions[i], itemPotionBuckets[i]);
        }

        if(client){
            // FluidPotion
            for(int i = 0; i < blockFluidPotions.length; i++){
            	ModelResourceLocation model = new ModelResourceLocation(EffectiveReactions.MODID + ":fluid_potion_" + (i + 1), "fluid");
            	blockFluidPotions[i].registerTextures(model);
            }
        }
    }

    private void initBlocks(){
    	blockSafeVanillaStone = new BlockSafeVanillaStone();
        blockSafeColorStone = new BlockSafeColorStone();
        blockEnderStone = new BlockEnderStone();

//        OreDictionary.registerOre("stonebrick", new ItemStack(Blocks.stonebrick));

       	Items.POTIONITEM.setMaxStackSize(POTION_STACK_SIZE);

    	itemMagicBottle = new ItemMagicBottle(POTION_STACK_SIZE);
        itemSpawnerUpgrade = new ItemSpawnerUpgrade();
        blockSemiSolidPotion = new BlockSemiSolidPotion();

        itemPotionEssence = new ItemPotionEssence();
        itemSpawnerScanner = new ItemSpawnerScanner();
        blockBarrel = new BlockBarrel();

//        itemPotionBuckets = new ItemPotionBucket[POTION_COUNT];
//        for(int i = 0; i < POTION_COUNT; i++){
//        	itemPotionBuckets[i] = new ItemPotionBucket(blockFluidPotions[i]);
//
////        	FluidContainerRegistry.registerFluidContainer(fluidPotions[i], new ItemStack(itemPotionBuckets[i]), FluidContainerRegistry.EMPTY_BUCKET);
//
//        	FillBucketHandler.INSTANCE.buckets.put(blockFluidPotions[i], itemPotionBuckets[i]);
//        }
//
//        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(itemPotionBuckets[0], 1, 0),
//        		"XXX", "XYX", "XXX",
//        		'X', new ItemStack(Items.ROTTEN_FLESH),
//        		'Y', new ItemStack(Items.WATER_BUCKET)
//        	));
//
//    	itemMagicBucket = new ItemMagicBucket();

        GameRegistry.addRecipe( new ShapedOreRecipe(
        	UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidPotions[0]),
			"XXX", "XYX", "XXX",
			'X', new ItemStack(Items.ROTTEN_FLESH),
			'Y', new ItemStack(Items.WATER_BUCKET)
		));

        for(int i = 0; i < fluidPotions.length; i++){
	        GameRegistry.addShapelessRecipe(
	        		UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidPotions[i]),
	        		new ItemStack(Items.WATER_BUCKET),
	        		ItemPotionBucket.inputItems[i][0],
	        		ItemPotionBucket.inputItems[i][1],
	        		ItemPotionBucket.inputItems[i][2]);
        }
    }

    private void initTexture(){
    	// SafeStones
    	blockSafeVanillaStone.registerTextures();
    	blockSafeColorStone.registerTextures();
        blockEnderStone.registerTextures();

        // PotionItem
        itemMagicBottle.registerTextures();
//        itemMagicBucket.registerTextures();
//
//        for(int i = 0; i < itemPotionBuckets.length; i++){
//        	itemPotionBuckets[i].registerTextures(i + 1);
//        }

        itemSpawnerUpgrade.registerTextures();

        // PotionBlock
        blockSemiSolidPotion.registerTextures();

        itemPotionEssence.registerTextures();
        itemSpawnerScanner.registerTextures();
        blockBarrel.registerTextures();

    }

    private void initEventHandler(){
    	MinecraftForge.EVENT_BUS.register(FillBucketHandler.INSTANCE);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemMagicBottle, BehaviorDispenseMagicGlass.INSTANCE);
//		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemMagicBucket, BehaviorDispenseMagicBucket.INSTANCE);
//		if(!DISPENSE_MAGIC_BUCKET){
//			BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BUCKET, BehaviorDispenseMagicBucket.INSTANCE);
//		}
//		BehaviorDispencePotionBucket.DISPENSE_MAGIC_BUCKET = DISPENSE_MAGIC_BUCKET;
//		for(int i = 0; i < POTION_COUNT; i++){
//    		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemPotionBuckets[i], BehaviorDispencePotionBucket.INSTANCE);
//    	}

        if(ModRegistry.INVERT_SPAWN_RULE){
            MinecraftForge.EVENT_BUS.register(CheckSpawnEventHandler.INSTANCE);
        }

		MinecraftForge.EVENT_BUS.register(new PlayerClickHandler());
		MinecraftForge.EVENT_BUS.register(new TooltipHandler());
//		MinecraftForge.EVENT_BUS.register(new ExplosionEventHandler());
	}
}
