package me.boyakabrodyaka.operation.npc.registration.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.boyakabrodyaka.operation.npc.registration.skin.NpcSkin;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.util.UUID;

public class NmsEntityFactory {

    private static final float YAW_SOUTH = 180.0F;
    private static final float PITCH_DEFAULT = 0.0F;

    public EntityPlayer create(String name, UUID uuid, World world, double x, double y, double z, NpcSkin skin) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();

        GameProfile profile = new GameProfile(uuid, name);

        if (skin != null) profile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));

        EntityPlayer entity = new EntityPlayer(server, nmsWorld, profile, new PlayerInteractManager(nmsWorld));
        entity.setLocation(x, y, z, YAW_SOUTH, PITCH_DEFAULT);

        return entity;
    }
}