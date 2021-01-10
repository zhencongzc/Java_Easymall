package cn.tedu.rabbitmq;

public class DeclareUtils {
    public static final String DIRECT_TYPE = "direct";
    public static final String FANOUT_TYPE = "fanout";
    public static final String TOPIC_TYPE = "topic";
    public static final String DIRECT_EXCHANGE = DIRECT_TYPE + "ex";
    public static final String FANOUT_EXCHANGE = FANOUT_TYPE + "ex";
    public static final String TOPIC_EXCHANGE = TOPIC_TYPE + "ex";

    //队列
    public static final String DIRECT_QUEUE1 = DIRECT_TYPE + "queue1";
    public static final String DIRECT_QUEUE2 = DIRECT_TYPE + "queue2";
    public static final String FANOUT_QUEUE1 = FANOUT_TYPE + "queue1";
    public static final String FANOUT_QUEUE2 = FANOUT_TYPE + "queue2";
    public static final String TOPIC_QUEUE1 = TOPIC_TYPE + "queue1";
    public static final String TOPIC_QUEUE2 = TOPIC_TYPE + "queue2";
}
