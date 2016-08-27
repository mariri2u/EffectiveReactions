package mariri.effectivereactions.helper;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class CustomPotionHelper {

	private Potion effect;
	private int duration;
	private int amplifier;

	public final static int[] DURATION_TABLE = new int[]{ 2400, 4800, 9600, 19200 };

	private final static Potion[] INSTANT_IDS = new Potion[] {
			MobEffects.INSTANT_HEALTH,
			MobEffects.INSTANT_DAMAGE};

	public final static int INSTANT_DURATION = 1;

	private final static Potion[] NO_AMPLIFIER_IDS = new Potion[] {
			MobEffects.NAUSEA,
			MobEffects.FIRE_RESISTANCE,
			MobEffects.WATER_BREATHING,
			MobEffects.INVISIBILITY,
			MobEffects.BLINDNESS,
			MobEffects.NIGHT_VISION
		};

//	public static final int MIN_SPLASH_METADATA = 16000;

	public static final int MAX_AMPLIFIER = 3;

	public final static Potion[] SUPPORT_IDS = new Potion[] {
			MobEffects.SPEED,
			MobEffects.HASTE,
			MobEffects.STRENGTH,
			MobEffects.INSTANT_HEALTH,
			MobEffects.JUMP_BOOST,
			MobEffects.REGENERATION,
			MobEffects.RESISTANCE,
			MobEffects.FIRE_RESISTANCE,
			MobEffects.WATER_BREATHING,
			MobEffects.NIGHT_VISION,
			MobEffects.HEALTH_BOOST,
			MobEffects.ABSORPTION,
			MobEffects.SATURATION,
			MobEffects.LUCK
		};

	public final static Potion[] NEUTRAL_IDS = new Potion[]{
			MobEffects.INVISIBILITY,
			MobEffects.GLOWING
	};

//	public static final int SWIFTNESS = 1;
//	public static final int SLOWNESS = 2;
//	public static final int HASTE = 3;
//	public static final int DULLNESS = 4;
//	public static final int STRENGTH = 5;
//	public static final int HEALING = 6;
//	public static final int HARMING = 7;
//	public static final int LEAPING = 8;
//	public static final int NAUSEA = 9;
//	public static final int REGENATION = 10;
//	public static final int RESISTANCE = 11;
//	public static final int FIRE_RESISTANCE = 12;
//	public static final int WATER_BREATHING = 13;
//	public static final int INVISIBILITY = 14;
//	public static final int BLINDNESS = 15;
//	public static final int NIGHT_VISION = 16;
//	public static final int HUNGER = 17;
//	public static final int WEAKNESS = 18;
//	public static final int POISON = 19;
//	public static final int DECAY = 20;
//	public static final int HEALTH_BOOST = 21;
//	public static final int ABSORPTION = 22;
//	public static final int SATURATION = 23;
//
//	public static final int EFFECT_VALUE = 23;
//
//	public static final int EFFECT_SHIFT = 0;
//	public static final int AMPLIFIER_SHIFT = 5;
//	public static final int SPLASH_SHIFT = 14;
//
//	public static final int EFFECT_MASK =		0x0000001F << EFFECT_SHIFT;
//	public static final int AMPLIFIER_MASK = 	0x00000003 << AMPLIFIER_SHIFT;
//	public static final int SPLASH_MASK =		0x00000001 << SPLASH_SHIFT;

	public CustomPotionHelper(){
		effect = MobEffects.SPEED;
		duration = this.isInstant() ? INSTANT_DURATION : DURATION_TABLE[0];
		amplifier = 0;
	}

	public CustomPotionHelper(Potion id){
		this.effect = id;
		duration = this.isInstant() ? INSTANT_DURATION : DURATION_TABLE[0];
		amplifier = 0;
	}

	public CustomPotionHelper(Potion id, int duration, int amplifier){
		this.effect = id;
		this.duration = this.isInstant() ? INSTANT_DURATION : duration;
		this.amplifier = this.isNoAmplifier() ? 0 : amplifier;
	}

	public CustomPotionHelper setId(Potion value){
		this.effect = value;
		return this;
	}

	public CustomPotionHelper setDuration(int value){
		this.duration = this.isInstant() ? INSTANT_DURATION : value;
		return this;
	}

	public CustomPotionHelper setAmplifier(int value){
		this.amplifier = this.isNoAmplifier() ? 0 : value;
		return this;
	}

	public Potion getId(){
		return effect;
	}

	public int getDuration(){
		return duration;
	}

	public int getAmplifier(){
		return amplifier;
	}

	public int getDurationCode(){
		for(int i = 0; i < DURATION_TABLE.length; i++){
			if(duration <= DURATION_TABLE[i]){
				return i;
			}
		}
		return 0;
	}

	public static int getMaxDuration(){
		return DURATION_TABLE.length - 1;
	}

	public CustomPotionHelper incrementDurationCode(){
		return this.setDurationCode(this.getDurationCode() + 1);
	}

	public CustomPotionHelper incrementAmplifier(){
		if(isMaxAmplifier()){ return this; }
		return this.setAmplifier(this.getAmplifier() + 1);
	}

	public boolean isSupport(){
		return isSupport(effect);
	}

	public boolean isNeutral(){
		return isNeutral(effect);
	}

	public boolean isBad(){
		return isBad(effect);
	}

	public static boolean isSupport(Potion id){
		for(Potion p : SUPPORT_IDS){
			if(p == id){ return true; }
		}
		return false;
	}

	public static boolean isNeutral(Potion id){
		for(Potion p : NEUTRAL_IDS){
			if(p == id){ return true; }
		}
		return false;
	}

	public static boolean isBad(Potion id){
		return !isSupport(id) && !isNeutral(id);
	}

	public boolean isInstant(){
		return isInstant(effect);
	}

	public static boolean isInstant(Potion id){
		for(Potion p : INSTANT_IDS){
			if(p == id){ return true; }
		}
		return false;
	}

	public boolean isNoAmplifier(){
		return isNoAmplifier(effect);
	}

	public static boolean isNoAmplifier(Potion id){
		for(Potion p : NO_AMPLIFIER_IDS){
			if(p == id){ return true; }
		}
		return false;
	}

	public boolean isMaxDuration(){
		if(isInstant()) { return true; }
		if(duration == DURATION_TABLE[DURATION_TABLE.length - 1]){ return true; }
		return false;
	}

	public boolean isMaxAmplifier(){
		if(isNoAmplifier()) { return true; }
		if(amplifier < MAX_AMPLIFIER){ return false; }
		return true;
	}

	public CustomPotionHelper setDurationCode(int code){
		if(code >= DURATION_TABLE.length){ return this; }
		return this.setDuration(DURATION_TABLE[code]);
	}

	public void writeToNBTTag(ItemStack itemstack){
		NBTTagCompound tag = itemstack.getTagCompound();
		if(tag == null){
			tag = new NBTTagCompound();
			itemstack.setTagCompound(tag);
		}
//		String name = PotionType.getPotionTypeForID(Potion.getIdFromPotion(id)).getNamePrefixed("");
		String name = potionId[Potion.getIdFromPotion(effect) - 1];
		tag.setString("Potion", name);
//		System.out.println(name);

		if(name.equals("awkward")){
			NBTTagCompound effect = new NBTTagCompound();
	        NBTTagList customPotionEffect = new NBTTagList();
	        customPotionEffect.appendTag(effect);
	        tag.setTag("CustomPotionEffects", customPotionEffect);

	        effect.setByte("Id", (byte)Potion.getIdFromPotion(this.effect));
	        effect.setByte("Amplifier", (byte)amplifier);
	        effect.setInteger("Duration", duration);
		}
	}

//	public void writeToNBTTag(NBTTagCompound tag){
//		tag.setByte("Id", (byte)Potion.getIdFromPotion(effect));
//		tag.setByte("Amplifier", (byte)amplifier);
//		tag.setInteger("Duration", duration);
//	}

//	public static NBTTagCompound createVoidNBTTag(){
//		NBTTagCompound effect = new NBTTagCompound();
//        NBTTagList customPotionEffect = new NBTTagList();
//        NBTTagCompound tag = new NBTTagCompound();
//        customPotionEffect.appendTag(effect);
//        tag.setTag("CustomPotionEffects", customPotionEffect);
//        return tag;
//	}

	public PotionEffect getPotionEffect(){
		return new PotionEffect(effect, duration, amplifier);
	}

	public static CustomPotionHelper getInstanceFromNBTTag(NBTTagCompound tag){
		CustomPotionHelper inst = new CustomPotionHelper();
		if(tag == null) { return inst; }
		inst.setId(Potion.getPotionById(tag.getByte("Id"))).setAmplifier(tag.getByte("Amplifier")).setDuration(tag.getInteger("Duration"));
		return inst;
	}

//	public static NBTTagCompound findPotionNBT(ItemStack itemstack){
//		try{
//			return itemstack.getTagCompound().getTagList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(0);
//		}catch(NullPointerException e){
//			return new NBTTagCompound();
//		}
//	}
//
//	public static NBTTagCompound findPotionNBT(NBTTagCompound tag){
//		try{
//			return tag.getTagList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(0);
//		}catch(NullPointerException e){
//			return new NBTTagCompound();
//		}
//	}

	public ItemStack getSampleItem(boolean splash){
		return getSampleItem(effect, getDurationCode(), amplifier, splash);
	}

	public static ItemStack getSampleItem(Potion id, int durationCode, int amplifier, boolean splash){
		ItemStack itemstack = new ItemStack(splash ? Items.SPLASH_POTION : Items.POTIONITEM);
		CustomPotionHelper potion = new CustomPotionHelper(id, DURATION_TABLE[durationCode], amplifier);
//		itemstack.setTagCompound(createVoidNBTTag());
		potion.writeToNBTTag(itemstack);
//		potion.writeToNBTTag(findPotionNBT(itemstack));
//		itemstack.setItemDamage(vanillaMetadataTable[Potion.getIdFromPotion(id) - 1][splash ? 2 : 0]);
		return itemstack;
	}

	public static ItemStack getSampleItem(Potion id, int durationCode, int amplifier, boolean splash, int stacksize){
		ItemStack itemstack = getSampleItem(id, durationCode, amplifier, splash);
		itemstack.stackSize = stacksize;
		return itemstack;
	}

//	public CustomPotionHelper decodeFromCustomMetadata(int meta){
//		this.effect = Potion.getPotionById((meta & EFFECT_MASK) >> EFFECT_SHIFT);
//		this.amplifier = (meta & AMPLIFIER_MASK) >> AMPLIFIER_SHIFT;
//		return this;
//	}

//	public static CustomPotionHelper createFromCustomMetadata(int meta){
//		CustomPotionHelper inst = new CustomPotionHelper();
//		inst.decodeFromCustomMetadata(meta);
//		return inst;
//	}

//	public int encodeToCustomMetadata(boolean splash){
//		return encodeToCustomMetadata(this.effect, this.amplifier, splash);
//	}

//	public static int encodeToCustomMetadata(Potion id, int amplifier, boolean splash){
//		int meta = 0;
//		meta |= Potion.getIdFromPotion(id) << EFFECT_SHIFT;
//		meta |= amplifier << AMPLIFIER_SHIFT;
//		meta |= splash ? SPLASH_MASK : 0;
//		return meta;
//	}

//	public static final int[][] vanillaMetadataTable = new int[][]{
//		new int[] {8194, 8226, 16386, 8258}, // swiftness
//		new int[] {8234, 8266, 16426, 3}, // slow
//		new int[] {1, 2, 16384, 3}, // haste
//		new int[] {1, 2, 16384, 3}, // dullness
//		new int[] {8201, 8233, 16393, 3}, // strength
//		new int[] {8261, 8299, 16453, 3}, // healing
//		new int[] {8268, 8236, 16460, 3}, // harming
//		new int[] {1, 2, 16384, 3}, // leaping
//		new int[] {1, 2, 16384, 3}, // nausea
//		new int[] {8193, 8225, 16385, 3}, // regenation
//		new int[] {1, 2, 16384, 3}, // resistance
//		new int[] {8227, 8259, 16419, 3}, // fire resistance
//		new int[] {8237, 8269, 16429, 3}, // water breathing
//		new int[] {8238, 8270, 16430, 3}, // invisibility
//		new int[] {1, 2, 16384, 3}, // blindness
//		new int[] {8230, 8262, 16422, 3}, // night vision
//		new int[] {1, 2, 16384, 3}, // hunger
//		new int[] {8232, 8264, 16424, 3}, // weakness
//		new int[] {8196, 8228, 16388, 3}, // poison
//		new int[] {1, 2, 16384, 3}, // decay
//		new int[] {1, 2, 16384, 3}, // health boost
//		new int[] {1, 2, 16384, 3}, // absorption
//		new int[] {1, 2, 16384, 3}, // saturation
//		new int[] {1, 2, 16384, 3}, // glowing
//		new int[] {1, 2, 16384, 3}, // levitation
//		new int[] {1, 2, 16384, 3}, // luck
//		new int[] {1, 2, 16384, 3} // bad luck
//	};

	public static final String[] potionId = new String[]{
			"swiftness",
			"slowness",
			"awkward",
			"awkward",
			"strength",
			"healing",
			"harming",
			"leaping",
			"awkward",
			"regeneration",
			"awkward",
			"fire_resistance",
			"water_breathing",
			"invisibility",
			"awkward",
			"night_vision",
			"awkward",
			"weakness",
			"poison",
			"awkward",
			"awkward",
			"awkward",
			"awkward",
			"awkward",
			"awkward",
			"luck",
			"awkward"
	};
}
