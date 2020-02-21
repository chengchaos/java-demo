package luxe.chaos.demo.grpc;

import com.google.protobuf.Timestamp;

import java.util.Date;

public class GrpcDateUtil {

    public static Timestamp dateToTimestamp(Date input) {
        return Timestamp.newBuilder()
                .setSeconds(input.getTime())
                .build();
    }

    public static Date timestampToDate(Timestamp input) {
        return new Date(input.getSeconds());
    }
}
