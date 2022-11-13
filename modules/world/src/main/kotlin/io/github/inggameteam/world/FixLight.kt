package io.github.inggameteam.world

import org.bukkit.Bukkit

class FixLight {
    fun fixLight() {
        println(Bukkit.getVersion())
        println(Bukkit.getBukkitVersion())
        val craftPlayerClass = Class.forName("org.bukkit.craftbukkit.entity.CraftPlayer")
        println(craftPlayerClass.simpleName)
    }
//                CraftPlayer player = (CraftPlayer) sender;
//                ServerPlayer handle = player.getHandle();
//                ServerLevel world = (ServerLevel) handle.level;
//                ThreadedLevelLightEngine lightengine = world.getChunkSource().getLightEngine();
//
//                net.minecraft.core.BlockPos center = MCUtil.toBlockPosition(player.getLocation());
//                Deque<ChunkPos> queue = new ArrayDeque<>(MCUtil.getSpiralOutChunks(center, radius));
//                updateLight(sender, world, lightengine, queue, post);
//
//                world.getChunkSource().getChunkAtAsynchronously(coord.x, coord.z, false, false).whenCompleteAsync((either, ex) -> {
//                        LevelChunk chunk = (net.minecraft.world.level.chunk.LevelChunk) either.left().orElse(null);
//                        lightengine.setTaskPerBatch(world.paperConfig().misc.lightQueueSize + 16 * 256); // ensure full chunk can fit into queue
//                        int cx = chunk.getPos().x << 4;
//                        int cz = chunk.getPos().z << 4;
//                        for (int y = 0; y < world.getHeight(); y++) {
//                            for (int x = 0; x < 16; x++) {
//                                for (int z = 0; z < 16; z++) {
//                                    net.minecraft.core.BlockPos pos = new net.minecraft.core.BlockPos(cx + x, y, cz + z);
//                                    lightengine.checkBlock(pos);
//                                }
//                            }
//                        }
//                        lightengine.tryScheduleUpdate();
//                        ChunkHolder visibleChunk = world.getChunkSource().chunkMap.getVisibleChunkIfPresent(chunk.coordinateKey);
//                        if (visibleChunk != null) {
//                                world.getChunkSource().chunkMap.addLightTask(visibleChunk, () -> {
//                                        MinecraftServer.getServer().processQueue.add(() -> {
//                                            visibleChunk.broadcast(new net.minecraft.network.protocol.game.ClientboundLightUpdatePacket(chunk.getPos(), lightengine, null, null, true), false);
//                                            updateLight(sender, world, lightengine, queue, done);
//                                        });
//                                    });
//                            } else {
//                                updateLight(sender, world, lightengine, queue, done);
//                            }
//                        lightengine.setTaskPerBatch(world.paperConfig().misc.lightQueueSize);
//                    }, MinecraftServer.getServer());
//
//
//        }
}