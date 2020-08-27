package simpleNote.A3JavaPlus.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;

/**
 * 消息的消费者（接受者）　（简单测试）
 * 　Ｐ-Ｐ　点对点模式发送和接受消息
 * 接收到消息后　消息队列　减少
 *
 *
 * @auther: zhouruigang
 * @date: 2018/11/23 15:52
 */
public class JMSConsumer {
    private static final String DEFAULT_USER = null;
    private static final String DEFAULT_PASSWORD = null;

    private static Logger log = Logger.getLogger(JMSProducer.class);

    public static void main(String[] args) {
        /***
         * 消息的接受　
         */
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_USER,DEFAULT_PASSWORD, "failover:(tcp://localhost:61616)?Randomize=false");
        try {
            //第二步：通过ConnectionFactory工厂对象我们创建一个Connection连接，并且调用Connection的start方法开启连接，Connection默认是关闭的。
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //第三步：通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数配置1为是否启用是事务，参数配置2为签收模式，一般我们设置自动签收。
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            //第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，在PTP模式中，Destination被称作Queue即队列；在Pub/Sub模式，Destination被称作Topic即主题。在程序中可以使用多个Queue和Topic。
            Destination destination = session.createQueue("test-queue");
            //第五步：通过Session创建MessageConsumer
            MessageConsumer consumer = session.createConsumer(destination);

            while (true) {
                TextMessage msg = (TextMessage) consumer.receive();
                if (msg == null) {
                    break;
                }
                log.info("收到的内容：" + msg.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
