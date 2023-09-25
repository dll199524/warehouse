package com.example.common.mod.factory;

/**
 * 抽象工厂
 */
public class AbstractIOFactory {

    public static IOHandler createIOHandler(Class<? extends IOHandler> clazz) {
        try {
            clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;

    }
    // IOHandler ioHandler = AbstractIOFactory.getMemoryIOHandler();


    public static IOHandler getMemoryIOHandler() {return createIOHandler(MemoryHandler.class);}

    public static IOHandler getDiskIOHandler() {return  createIOHandler(DiskHandler.class);}

    public static IOHandler getPreferencesIOHandler() {return  createIOHandler(PreferencesHandler.class);}

    public static IOHandler getDefaultIOHandler() {return createIOHandler(MemoryHandler.class);}

}
