package mariri.effectivereactions;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class CreativeEffectiveReactions extends CreativeTabs {

	public CreativeEffectiveReactions(String label)
	{
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return new ItemStack(EffectiveReactions.blockSafeColorStone).getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "Effective Reactions";
	}
}
