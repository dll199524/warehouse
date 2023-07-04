package com.example.customview.view.floatview;

public class FloatActionController {

    private FloatActionController(){}
    public static FloatActionController getInstance() {return ProviderHolder.instance;}
    public static class ProviderHolder {private static final FloatActionController instance = new FloatActionController();}

    public void startServer() {

    }

}
