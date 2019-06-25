package cn.futuremove.tsp.vehicle.control.business;

public class SetRequest {



    public final String uri;
    public final String body;

    public SetRequest(String uri, String body) {
        this.uri = uri;
        this.body = body;
    }
}
