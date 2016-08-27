package mariri.effectivereactions.handler;

import java.util.List;

import mariri.effectivereactions.EffectiveReactions;
import mariri.effectivereactions.helper.CustomPotionHelper;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TooltipHandler {

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e){
		Item item = e.getItemStack().getItem();
		List<String> tooltip = e.getToolTip();
		if(item instanceof UniversalBucket){
			UniversalBucket bucket = (UniversalBucket)item;
			for(int i = 0; i < EffectiveReactions.fluidPotions.length; i++){
				if(bucket.getFluid(e.getItemStack()).getFluid() == EffectiveReactions.fluidPotions[i]){
					Potion effect = Potion.getPotionById(i + 1);

					String name = I18n.translateToLocal(effect.getName());
					tooltip.add(name);
					tooltip.add(
							CustomPotionHelper.isSupport(effect) ? " - Good Status" :
							CustomPotionHelper.isNeutral(effect) ? " - Neutral Status"  : " - Bad Status");
				}
			}
		}
	}
}
