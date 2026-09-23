package me.boyakabrodyaka.core.manager;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.core.generator.VoidGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class WorldManager {

    private static final String LOBBY_WORLD = "tvrn_lobby";
    private static final String MAP_BUILD_WORLD = "tvrn_mapbuild";
    private static final String MAP_SUFFIX = "_map";

    private static final int LOBBY_SPAWN_Y = 66;
    private static final int MAP_SPAWN_Y = 65;

    private static final String UID_FILE = "uid.dat";
    private static final String SESSION_LOCK_FILE = "session.lock";

    private static final String PLAYERDATA_FOLDER = "playerdata";
    private static final String STATS_FOLDER = "stats";
    private static final String ADVANCEMENTS_FOLDER = "advancements";

    private final JavaPlugin plugin;
    private final ConcurrentHashMap<String, World> worlds = new ConcurrentHashMap<>();

    public void initializeWorlds() {
        createWorld(LOBBY_WORLD);
        createWorld(MAP_BUILD_WORLD);
        loadAllPlayerWorlds();
    }

    public World createPlayerMap(String uuid, int number) {
        String worldName = uuid + MAP_SUFFIX + number;

        World existing = Bukkit.getWorld(worldName);
        if (existing != null) {
            this.worlds.put(worldName, existing);
            return existing;
        }

        File sourceFolder = getWorldFolder(MAP_BUILD_WORLD);
        File targetFolder = getWorldFolder(worldName);

        if (!sourceFolder.exists()) {
            this.plugin.getLogger().severe("Шаблон " + MAP_BUILD_WORLD + " не найден");
            return null;
        }

        if (!targetFolder.exists()) {
            try {
                copyWorld(sourceFolder, targetFolder);
            } catch (IOException exception) {
                this.plugin.getLogger().severe("Ошибка копирования мира: " + exception.getMessage());
                return null;
            }
        }

        cleanCopiedWorld(targetFolder);

        World world = new WorldCreator(worldName)
                .environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .createWorld();

        if (world == null) return null;

        applySettings(world, worldName);
        world.setSpawnLocation(0, MAP_SPAWN_Y, 0);

        this.worlds.put(worldName, world);
        return world;
    }

    public void deletePlayerMap(String uuid, int number) {
        String worldName = uuid + MAP_SUFFIX + number;

        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            world.save();
            Bukkit.unloadWorld(world, true);
            this.worlds.remove(worldName);
        }

        File worldFolder = getWorldFolder(worldName);
        if (!worldFolder.exists()) return;

        deleteFolder(worldFolder);
    }

    public World getWorld(String name) {
        World world = this.worlds.get(name);
        if (world != null) return world;

        world = Bukkit.getWorld(name);
        if (world != null) this.worlds.put(name, world);

        return world;
    }

    public boolean worldExists(String name) {
        if (this.worlds.containsKey(name)) return true;
        return Bukkit.getWorld(name) != null;
    }

    public void cleanup() {
        for (World world : this.worlds.values()) {
            if (world == null) continue;
            world.save();
        }

        this.worlds.clear();
    }

    private void createWorld(String name) {
        World existing = Bukkit.getWorld(name);
        if (existing != null) {
            this.worlds.put(name, existing);
            return;
        }

        File worldFolder = getWorldFolder(name);
        WorldCreator creator = new WorldCreator(name)
                .environment(World.Environment.NORMAL)
                .type(WorldType.FLAT);

        if (!worldFolder.exists()) creator.generator(new VoidGenerator());

        World world = creator.createWorld();
        if (world == null) return;

        applySettings(world, name);
        this.worlds.put(name, world);
    }

    private void loadAllPlayerWorlds() {
        File container = Bukkit.getWorldContainer();
        File[] files = container.listFiles();

        if (files == null) return;

        for (File file : files) {
            if (!file.isDirectory()) continue;
            if (!file.getName().contains(MAP_SUFFIX)) continue;

            String worldName = file.getName();
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                world = new WorldCreator(worldName)
                        .environment(World.Environment.NORMAL)
                        .type(WorldType.FLAT)
                        .createWorld();
            }

            if (world == null) continue;

            applySettings(world, worldName);
            this.worlds.put(worldName, world);
        }
    }

    private void applySettings(World world, String name) {
        world.setAutoSave(true);
        world.setKeepSpawnInMemory(true);
        world.setMonsterSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setWaterAnimalSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setPVP(false);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);

        int spawnY = name.equals(LOBBY_WORLD) ? LOBBY_SPAWN_Y : MAP_SPAWN_Y;
        world.setSpawnLocation(0, spawnY, 0);
    }

    private void cleanCopiedWorld(File worldFolder) {
        deleteFile(new File(worldFolder, UID_FILE));
        deleteFile(new File(worldFolder, SESSION_LOCK_FILE));
        deleteFolder(new File(worldFolder, PLAYERDATA_FOLDER));
        deleteFolder(new File(worldFolder, STATS_FOLDER));
        deleteFolder(new File(worldFolder, ADVANCEMENTS_FOLDER));
    }

    private File getWorldFolder(String worldName) {
        return new File(Bukkit.getWorldContainer(), worldName);
    }

    private void deleteFile(File file) {
        if (!file.exists()) return;
        if (file.isDirectory()) return;

        file.delete();
    }

    private void deleteFolder(File folder) {
        if (!folder.exists()) return;

        try (Stream<Path> walk = Files.walk(folder.toPath())) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException exception) {
            this.plugin.getLogger().severe("Ошибка удаления папки: " + exception.getMessage());
        }
    }

    private void copyWorld(File source, File target) throws IOException {
        Path sourcePath = source.toPath();
        Path targetPath = target.toPath();

        try (Stream<Path> walk = Files.walk(sourcePath)) {
            walk.forEach(sourceFile -> copyEntry(sourcePath, targetPath, sourceFile));
        }
    }

    private void copyEntry(Path sourceRoot, Path targetRoot, Path sourceFile) {
        try {
            Path targetFile = targetRoot.resolve(sourceRoot.relativize(sourceFile));

            if (Files.isDirectory(sourceFile)) {
                Files.createDirectories(targetFile);
                return;
            }

            String fileName = sourceFile.getFileName().toString();
            if (fileName.equals(UID_FILE) || fileName.equals(SESSION_LOCK_FILE)) return;

            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}