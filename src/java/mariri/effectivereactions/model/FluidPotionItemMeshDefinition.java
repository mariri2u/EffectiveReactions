package mariri.effectivereactions.model;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;;

public class FluidPotionItemMeshDefinition implements ItemMeshDefinition {

	ModelResourceLocation model;

	public FluidPotionItemMeshDefinition(ModelResourceLocation model) {
		this.model = model;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModelLocation(ItemStack stack) {
		return model;
	}

}
