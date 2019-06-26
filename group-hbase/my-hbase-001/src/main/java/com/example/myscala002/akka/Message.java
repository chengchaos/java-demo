package com.example.myscala002.akka;

import akka.actor.ActorPath;

import java.util.StringJoiner;

/**
 * <p>
 * <strong>
 * 1.首先我们来创建一些消息：
 * </strong><br /><br />
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/1 0001 下午 5:13 <br />
 * @since 1.1.0
 */
public class Message {

    private String content;

    public String getContent() {
        return content;
    }

    public Message(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
                .add("content='" + content + "'")
                .toString();
    }

    public static class Business extends Message {

        public Business(String content) {
            super(content);
        }
    }


    public static class Meeting extends Message {

        public Meeting(String content) {
            super(content);
        }
    }


    public static class Confirm extends Message {

        private ActorPath actorPath;

        public Confirm(String content, ActorPath actorPath) {
            super(content);
            this.actorPath = actorPath;
        }



        public ActorPath getActorPath() {
            return actorPath;
        }


    }


    public static class DoAction extends Message {

        public DoAction(String content) {
            super(content);
        }
    }


    public static class Done extends Message {

        public Done(String content) {
            super(content);
        }
    }
}

