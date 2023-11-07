package com.debug2016.ocr.ofline;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.Pointer;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

@Slf4j
@Getter
public class MyReference extends PhantomReference<Pointer> {
    private static final ReferenceQueue<Pointer> referenceQueue = new ReferenceQueue<>();
    private String sessionId;
    private String type;
    private Long address;

    static final Thread deallocatorThread;

    MyReference(Pointer p, String sessionId,String type) {
        super(p, referenceQueue);
        this.sessionId = sessionId;
        this.address = p.address();
        this.type = type;
    }

    static {
        deallocatorThread = new DeallocatorThread();
    }

    static class DeallocatorThread extends Thread {
        DeallocatorThread() {
            super("JavaCPP Deallocator123");
            setPriority(Thread.MAX_PRIORITY);
            setDaemon(true);
            setContextClassLoader(null); // as required by containers
            start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    MyReference r = (MyReference) referenceQueue.remove();
                    log.info("MyReference run clzName={}, sessionId {},address={}", r.type, r.sessionId, Long.toHexString(r.address));
                }
            } catch (InterruptedException ex) {
                // reset interrupt to be nice
                log.warn("MyReference run e", ex);

            }
        }
    }
}

