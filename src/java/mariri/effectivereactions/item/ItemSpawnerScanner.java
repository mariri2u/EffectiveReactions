package mariri.effectivereactions.item;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.helper.CustomSpawnerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpawnerScanner extends Item {

	public ItemSpawnerScanner(){
		super();
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName("spawner_scanner");
		GameRegistry.register(this, new ResourceLocation(EffectiveReactions.MODID, "spawner_scanner"));

		registerRecipe();
	}

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		int meta = block.getMetaFromState(state);

		if(block == Blocks.MOB_SPAWNER){
			CustomSpawnerHelper.showSpawnerLevel(world, pos, player);
		}

        return EnumActionResult.SUCCESS;
    }

    private void registerRecipe(){
		GameRegistry.addShapedRecipe(
				new ItemStack(this, 1, 0), "XXX", "XXX", "XXX",
				'X', new ItemStack(Items.MUSHROOM_STEW)
				);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
    	list.add(new ItemStack(item));
    }

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation json = new ResourceLocation("effectivereactions:spawner_scanner");

        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        ModelBakery.registerItemVariants(this, json);
        //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation(json, "inventory"));
    }
}