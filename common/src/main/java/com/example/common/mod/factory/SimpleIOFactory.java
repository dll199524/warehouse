package com.example.common.mod.factory;

/**
 * 简单工厂
 */
public class SimpleIOFactory {

    enum IOType {
        DISK, MEMORY, PREFERENCES
    }
    // IOHandler ioHandler = SimpleIOFactory.createIOHandler(IOType.MEMORY);

    public static IOHandler createIOHandler (IOType ioType) {
        switch (ioType) {
            case DISK:
                return new DiskHandler();
            case MEMORY:
                return new MemoryHandler();
            case PREFERENCES:
                return new PreferencesHandler();
            default:
                throw  new UnsupportedOperationException();
        }
    }
}
