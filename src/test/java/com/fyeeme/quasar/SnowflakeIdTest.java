package com.fyeeme.quasar;

import com.fyeeme.quasar.base.util.SnowflakeId;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SnowflakeIdTest {

    @Test
    void testNextId() {
        for (int i = 0; i < 40; i++) {
            long id = SnowflakeId.nextId();
            System.out.println(id);
        }
    }

    @Test
    void asyncGenerate() {
        //It's not much, but it's a good sanity check I guess.
        final Set<Long> ids = new ConcurrentSkipListSet<>();
        int range = 100000;
        IntStream.rangeClosed(1, range).parallel().forEach(i -> {
            final long id = SnowflakeId.nextId();
            if (!ids.contains(id)) {
                ids.add(id);
            } else {
                fail("Non-unique ID generated: " + id);
            }
        });

        ids.stream().limit(10000).forEach(id -> {
            System.out.println(ids);
        });

        assertEquals(range, ids.size());
    }

    @Test
    void test64KIdIn1Sec() {
        for (int i = 0; i < 65534; i++) {
            SnowflakeId.nextId();
        }
        // nextId: 1111111111111111xxxxx:
        assertEquals(0b1111111111111111_00000L, SnowflakeId.nextId() & 0b1111111111111111_00000L);
        assertEquals(0b01_00000L, SnowflakeId.nextId() & 0b1111111111111111_00000L);
        assertEquals(0b10_00000L, SnowflakeId.nextId() & 0b1111111111111111_00000L);
    }
}
