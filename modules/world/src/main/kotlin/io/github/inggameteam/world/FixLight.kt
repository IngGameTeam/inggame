package io.github.inggameteam.world

import net.openhft.compiler.CompilerUtils

class FixLight {
    fun fixLight() {
        // dynamically you can call

        // dynamically you can call
        val className = "io.github.inggameteam.world.FixLightClass"
//        val javaCode = """package mypackage;
//public class MyClass implements Runnable {
//    public void run() {
//        CraftPlayer player = (CraftPlayer) sender;
//        ServerPlayer handle = player.getHandle();
//        ServerLevel world = (ServerLevel) handle.level;
//        ThreadedLevelLightEngine lightengine = world.getChunkSource().getLightEngine();
//
//        net.minecraft.core.BlockPos center = MCUtil.toBlockPosition(player.getLocation());
//        Deque<ChunkPos> queue = new ArrayDeque<>(MCUtil.getSpiralOutChunks(center, radius));
//        updateLight(sender, world, lightengine, queue, post);
//    }
//    private void updateLight(
//        final CommandSender sender,
//        final ServerLevel world,
//        final ThreadedLevelLightEngine lightengine,
//        final Deque<ChunkPos> queue,
//        final @Nullable Runnable done
//    ) {
//        @Nullable ChunkPos coord = queue.poll();
//        if (coord == null) {
//            sender.sendMessage(text("All Chunks Light updated", GREEN));
//            if (done != null) {
//                done.run();
//            }
//            return;
//        }
//        world.getChunkSource().getChunkAtAsynchronously(coord.x, coord.z, false, false).whenCompleteAsync((either, ex) -> {
//            if (ex != null) {
//                sender.sendMessage(text("Error loading chunk " + coord, RED));
//                updateLight(sender, world, lightengine, queue, done);
//                return;
//            }
//            @Nullable LevelChunk chunk = (net.minecraft.world.level.chunk.LevelChunk) either.left().orElse(null);
//            if (chunk == null) {
//                updateLight(sender, world, lightengine, queue, done);
//                return;
//            }
//            lightengine.setTaskPerBatch(world.paperConfig().misc.lightQueueSize + 16 * 256); // ensure full chunk can fit into queue
//            sender.sendMessage(text("Updating Light " + coord));
//            int cx = chunk.getPos().x << 4;
//            int cz = chunk.getPos().z << 4;
//            for (int y = 0; y < world.getHeight(); y++) {
//                for (int x = 0; x < 16; x++) {
//                    for (int z = 0; z < 16; z++) {
//                        net.minecraft.core.BlockPos pos = new net.minecraft.core.BlockPos(cx + x, y, cz + z);
//                        lightengine.checkBlock(pos);
//                    }
//                }
//            }
//            lightengine.tryScheduleUpdate();
//            @Nullable ChunkHolder visibleChunk = world.getChunkSource().chunkMap.getVisibleChunkIfPresent(chunk.coordinateKey);
//            if (visibleChunk != null) {
//                world.getChunkSource().chunkMap.addLightTask(visibleChunk, () -> {
//                    MinecraftServer.getServer().processQueue.add(() -> {
//                        visibleChunk.broadcast(new net.minecraft.network.protocol.game.ClientboundLightUpdatePacket(chunk.getPos(), lightengine, null, null, true), false);
//                        updateLight(sender, world, lightengine, queue, done);
//                    });
//                });
//            } else {
//                updateLight(sender, world, lightengine, queue, done);
//            }
//            lightengine.setTaskPerBatch(world.paperConfig().misc.lightQueueSize);
//        }, MinecraftServer.getServer());
//    }
//}
//"""
        val javaCode = """
            package io.github.inggameteam.world;
            public class FixLightClass implements Runnable {
                public void run() {
                    System.out.println("Hello World");
                }
            }
""";
        val aClass: Class<*> = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode)
        val runner = aClass.newInstance() as Runnable
        runner.run()
    }
}