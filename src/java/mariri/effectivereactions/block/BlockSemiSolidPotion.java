package mariri.effectivereactions.block;

import java.util.List;
import java.util.Random;

import mariri.effectivereactions.EffectiveReactions;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockSemiSolidPotion extends BlockFalling{

	public BlockSemiSolidPotion(){
		super();
		this.setCreativeTab(EffectiveReactions.creativeTab);
		this.setUnlocalizedName("semiSolidPotion");
		ResourceLocation res = new ResourceLocation(EffectiveReactions.MODID, "semi_solid_potion");
		GameRegistry.register(this, res);
		GameRegistry.register(new ItemBlock(this), res);
	}

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){
//    	checkExplosion(world, x, y, z);
    	react((World)world, pos);
    	super.onNeighborChange(world, pos, neighbor);
    }


	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){

//		List<EntityCreature> list = world.getEntitiesWithinAABB(
//				EntityItem.class,
//				AxisAlignedBB.getBoundingBox(x - 10, y - 5, z - 10, x + 11, y + 5, z + 11));
//		if(list.size() < 10){
//		    EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(this));
//		    entityitem.delayBeforeCanPickup = 10;
//		    world.spawnEntityInWorld(entityitem);
//
//		    world.setBlockToAir(x, y, z);
//		}
		react(world, pos);
		super.updateTick(world, pos, state, rand);
	}

	private boolean react(World world, BlockPos pos){
		int count = 0;

    	List<EntityCreature> list = world.getEntitiesWithinAABB(EntityCreature.class,
    			new AxisAlignedBB(pos.getX() - 2, pos.getY() - 1, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 1, pos.getZ() + 3));
		if(list.size() < 10){
			for(int xi = -1; xi <= 1; xi++){
				for(int yi = -1; yi <= 1; yi++){
					for(int zi = -1; zi <= 1; zi++){
						if(!(xi == 0 && yi == 0 && zi == 0) && world.getBlockState(pos.add(xi, yi, zi)).getBlock() == this){
							world.setBlockToAir(pos.add(xi, yi, zi));
							count++;
						}
					}
				}
			}

			if(count > 0){
				count++;
				world.setBlockToAir(pos);
				EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this, count));
				entityitem.setDefaultPickupDelay();
				world.spawnEntityInWorld(entityitem);
			}
		}
		return count > 0;
	}

    @SideOnly(Side.CLIENT)
    public void registerTextures(){
    	ResourceLocation json = new ResourceLocation("effectivereactions:semi_solid_potion");
        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        ModelBakery.registerItemVariants(Item.getItemFromBlock(this), json);
        //1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(json, "inventory"));
    }
}
