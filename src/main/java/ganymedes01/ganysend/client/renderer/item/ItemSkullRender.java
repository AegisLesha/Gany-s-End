package ganymedes01.ganysend.client.renderer.item;

import ganymedes01.ganysend.client.renderer.tileentity.TileEntityBlockSkullRender;
import ganymedes01.ganysend.lib.SkullTypes;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's End
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ItemSkullRender implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		int skullType = stack.getItemDamage();
		GameProfile name = null;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner"))
			name = NBTUtil.func_152459_a(stack.getTagCompound().getCompoundTag("SkullOwner"));

		boolean isVanilla = stack.getItem() == Items.skull;
		if (!isVanilla)
			if (skullType == SkullTypes.witch.ordinal() || skullType == SkullTypes.wildDeer.ordinal() || skullType == SkullTypes.witch.ordinal()) {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glTranslated(0, -0.45, 0);
			} else if (skullType == SkullTypes.enderDragon.ordinal() || skullType == SkullTypes.pinchBeetle.ordinal())
				GL11.glScaled(0.75, 0.75, 0.75);

		switch (type) {
			case ENTITY: {
				renderSkull(-0.25F, -0.5F, -0.5F, skullType, name, isVanilla);
				break;
			}
			case EQUIPPED: {
				renderSkull(0.5F, 0.0F, 0.0F, skullType, name, isVanilla);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderSkull(0.75F, 0.25F, 0.4F, skullType, name, isVanilla);
				break;
			}
			case INVENTORY: {
				GL11.glScaled(1.5, 1.5, 1.5);
				renderSkull(0.75F, 0.30F, 0.5F, skullType, name, isVanilla);
				break;
			}
			default:
				break;
		}
	}

	private void renderSkull(float x, float y, float z, int type, GameProfile name, boolean isVanilla) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		if (isVanilla)
			TileEntitySkullRenderer.field_147536_b.func_152674_a(0, 0, 0, 0, 0, type, name);
		else
			TileEntityBlockSkullRender.instance.renderHead(0, 0, 0, 0, 0, type, name);
		GL11.glPopMatrix();
	}
}