package cn.chengchao.helper;

public class Cons {

    private static String token = "A914191AD5E9491BAF40218A139F8D7A";
    private static String vin = "101";

    public static final String CLIENT_URL = "ws://42.159.92.113/ctrl/ws-command/app/v1/"
            + token
            +"/"
            + vin;
}
