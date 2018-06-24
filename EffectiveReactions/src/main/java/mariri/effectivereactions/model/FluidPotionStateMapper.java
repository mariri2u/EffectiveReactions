package mariri.effectivereactions.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class FluidPotionStateMapper extends StateMapperBase {

	ModelResourceLocation model;

	public FluidPotionStateMapper(ModelResourceLocation model) {
		this.model = model;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return model;
	}

}
