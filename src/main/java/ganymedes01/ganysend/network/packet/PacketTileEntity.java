package ganymedes01.ganysend.network.packet;

import ganymedes01.ganysend.core.utils.Utils;
import ganymedes01.ganysend.lib.IPacketHandlingTile;
import ganymedes01.ganysend.network.PacketHandler.PacketType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketTileEntity extends CustomPacket {

	private int x, y, z;
	private TileData dataWriter;
	private ByteBuf buffer;

	public interface TileData {

		public void writeData(ByteBuf buffer);
	}

	public PacketTileEntity() {
		super(PacketType.TILE_ENTITY);
	}

	public PacketTileEntity(TileEntity tile, TileData dataWriter) {
		this(tile.xCoord, tile.yCoord, tile.zCoord, dataWriter);
	}

	public PacketTileEntity(int x, int y, int z, TileData dataWriter) {
		super(PacketType.TILE_ENTITY);
		this.x = x;
		this.y = y;
		this.z = z;
		this.dataWriter = dataWriter;
	}

	@Override
	public void writeData(ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
		dataWriter.writeData(buffer);
	}

	@Override
	public void readData(ByteBuf buffer) {
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		this.buffer = buffer;
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		IPacketHandlingTile tile = Utils.getTileEntity(player.worldObj, x, y, z, IPacketHandlingTile.class);
		if (tile != null)
			tile.readPacketData(buffer);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		IPacketHandlingTile tile = Utils.getTileEntity(player.worldObj, x, y, z, IPacketHandlingTile.class);
		if (tile != null)
			tile.readPacketData(buffer);
	}
}