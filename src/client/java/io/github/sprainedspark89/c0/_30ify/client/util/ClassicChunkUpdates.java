package io.github.sprainedspark89.c0._30ify.client.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class ClassicChunkUpdates {
    private static final AtomicInteger UPDATES = new AtomicInteger(); // Kah-boom?

    public static int chunkUpdates = 0;

    private ClassicChunkUpdates() {
    }

    public static void increment() {
        UPDATES.incrementAndGet();
    }

    public static int getAndReset() {
        return UPDATES.getAndSet(0);
    }
}