package me.boyakabrodyaka.core.generator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    private static final double SPAWN_X = 0.0D;
    private static final double SPAWN_Y = 65.0D;
    private static final double SPAWN_Z = 0.0D;

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) { return createChunkData(world); }

    @Override
    public boolean canSpawn(World world, int x, int z) { return true; }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) { return new Location(world, SPAWN_X, SPAWN_Y, SPAWN_Z); }
}