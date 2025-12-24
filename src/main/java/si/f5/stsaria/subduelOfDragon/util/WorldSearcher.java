package si.f5.stsaria.subduelOfDragon.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;
import si.f5.stsaria.subduelOfDragon.Settings;

public class WorldSearcher {
    private static boolean isDanger(Material m) {
        return m == Material.LAVA
            || m == Material.MAGMA_BLOCK
            || m == Material.FIRE
            || m == Material.SOUL_FIRE
            || m == Material.CAMPFIRE
            || m == Material.SOUL_CAMPFIRE
            || m == Material.CACTUS
            || m == Material.SWEET_BERRY_BUSH;
    }
    @Nullable
    public static Location getSafeGroundByAroundXAndZ(World world, int x, int z) {
        Chunk chunk = world.getChunkAt(x, z);
        int chunkStartX = chunk.getX();
        int chunkStartZ = chunk.getZ();
        int[][] highestY = new int[16][16];

        for (int cx = 0; cx < 16; cx++) {
            for (int cz = 0; cz < 16; cz++) {
                highestY[cx][cz] = world.getHighestBlockYAt(chunkStartX + cx, chunkStartZ + cz);
            }
        }
        int sideLength = Settings.getInt("gateTeleportSquareLocationLengthOfSide");
        int stepBetweenNeighbor = Settings.getInt("gateTeleportSquareLocationStepBetweenNeighbor");
        int dz, ex, ez;
        boolean wasDanger;
        for (int dx = 0; dx < highestY.length - sideLength; dx += sideLength) {
            for (dz = 0; dz < highestY[dx].length - sideLength; dz += sideLength) {
                wasDanger = false;
                int previousY = highestY[dx][dz];
                for (ex = dx; ex < dx + sideLength; ex++) {
                    for (ez = dz; ez < dz + sideLength; ez++) {
                        if (
                            isDanger(world.getType(chunkStartX + ex, highestY[ex][ez] - 1, chunkStartZ + ez))
                            || isDanger(world.getType(chunkStartX + ex, highestY[ex][ez], chunkStartZ + ez))
                            || isDanger(world.getType(chunkStartX + ex, highestY[ex][ez] + 1, chunkStartZ + ez))
                            || Math.abs(previousY - highestY[ex][ez]) > stepBetweenNeighbor
                        ){
                            wasDanger = true;
                            break;
                        }
                    }
                }
                if (!wasDanger) return new Location(world, chunkStartX + (dx / sideLength), highestY[dx / sideLength][dz / sideLength]+1, chunkStartZ + (dz / sideLength));
            }
        }
        return null;
    }
}
