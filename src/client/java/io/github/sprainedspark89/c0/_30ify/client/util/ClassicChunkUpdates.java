package io.github.sprainedspark89.c0._30ify.client.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class ClassicChunkUpdates {
    public static int chunkUpdates = 0;

    private static final AtomicInteger UPDATES = new AtomicInteger(); // Kah-boom?

    public static int getAndReset() {
        return UPDATES.getAndSet(0);
    }

    public static void increment() {
        UPDATES.incrementAndGet();
    }

    private ClassicChunkUpdates() {
    }
}