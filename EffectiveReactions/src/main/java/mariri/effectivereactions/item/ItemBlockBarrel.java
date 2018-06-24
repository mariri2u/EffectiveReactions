package mariri.effectivereactions.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBarrel extends ItemBlock{

	public ItemBlockBarrel(Block block){
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
}
