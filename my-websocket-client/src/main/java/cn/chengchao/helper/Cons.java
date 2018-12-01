package cn.chengchao.helper;

public class Cons {

    public static final String CLIENT_URL;

    static {
        String url = "ws://42.159.92.113/ctrl/ws-command/app/v1/";
        String token = "CD9A52D28D674FB68888EE73FB1CD360";
        String vin = "LKJHD2AZ7JF080216";

        token = "A914191AD5E9491BAF40218A139F8D7A";
        vin = "100";

        token = "6F9332A26059412FA9BEC474E4EE5D2C";
        vin = "FMT-CE10000000001";

        url = "ws://127.0.0.1:8080/ctrl/ws-command/app/v1/";


        CLIENT_URL = url + token +"/" + vin;
    }

}
