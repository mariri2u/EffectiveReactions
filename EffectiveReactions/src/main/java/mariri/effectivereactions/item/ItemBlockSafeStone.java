package mariri.effectivereactions.item;

import java.util.List;

import mariri.effectivereactions.ModRegistry;
import mariri.effectivereactions.block.IBlockSafeStone;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

public class ItemBlockSafeStone extends ItemBlock {

	public ItemBlockSafeStone(Block block){
		super(block);
		this.setHasSubtypes(true);
	}

    @Override
    public int getMetadata(int meta){
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack){
    	return this.block.getUnlocalizedName() + "." + itemStack.getItemDamage();
    }

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List tooltip, boolean par4) {
		Block block = this.block;
		if (block != null && block instanceof IBlockSafeStone && ModRegistry.INVERT_SPAWN_RULE){
			tooltip.add(I18n.translateToLocal("effectivereactions.tooltip_invert"));
		}else{
			tooltip.add(I18n.translateToLocal("effectivereactions.tooltip"));
		}
	}
}
