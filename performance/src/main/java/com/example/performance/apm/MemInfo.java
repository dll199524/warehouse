package com.example.performance.apm;

public class MemInfo {
    public String procName;
    public AppMemory appMemory;
    public SystemMemory systemMemory;

    public static class AppMemory {
        private long dalvikPss; //java占用内存大小
        private long nativePss;
        private long totalPss;
    }

    public static class SystemMemory {
        public long availMem;
        public long lowMemory;
        public long threshold;
        public long totalMem;
    }

}
