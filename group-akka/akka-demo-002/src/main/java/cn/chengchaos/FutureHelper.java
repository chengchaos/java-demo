package cn.chengchaos;

import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;

public class FutureHelper {

    public static CompletionStage<Object> toJava(Future<Object> scalaFuture) {

        return scala.compat.java8.FutureConverters.toJava(scalaFuture);
    }
}
