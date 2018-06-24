package mariri.effectivereactions.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class MaterialPotion extends MaterialLiquid {

    public MaterialPotion()
    {
        super(MapColor.WATER);
        this.setNoPushMobility();
    }


    public MaterialPotion(MapColor par1MapColor)
    {
        super(par1MapColor);
        this.setNoPushMobility();
    }
}
