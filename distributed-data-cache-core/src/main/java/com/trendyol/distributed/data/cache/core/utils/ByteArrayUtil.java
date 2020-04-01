package com.trendyol.distributed.data.cache.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ByteArrayUtil {

    public static byte[] toByteArray(final InputStream input) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(input, output);
            return output.toByteArray();
        }
    }

    private static int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    private static long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, 1024 * 4);
    }

    private static long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    private static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
