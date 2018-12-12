package cn.chengchao.helper;

public class Cons {

    public static final String CLIENT_URL;

    static {
        String url = null;
        String token = null;

        String vin = null;

        token = "6F9332A26059412FA9BEC474E4EE5D2C";
        vin = "FMT-CE10000000001";

        token = "CD9A52D28D674FB68888EE73FB1CD360";
        vin = "LKJHD2AZ7JF080216";

        token = "A914191AD5E9491BAF40218A139F8D7A";
        vin = "100";

        url = "ws://127.0.0.1:8808/ctrl/ws-command/app/v1/";
        url = "ws://42.159.92.113/ctrl/ws-command/app/v1/";



        CLIENT_URL = url + token +"/" + vin;
        System.err.println(CLIENT_URL);
    }

}
