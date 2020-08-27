package simpleNote.A3JavaPlus.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * @auther: zhouruigang
 * @date: 2018/11/23 13:59
 */
public class AppConsumer {
    public static final String url = "tcp://localhost:61616";
    public static final String topicName = "topic-test ";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话 第一个参数：是否在事务中去处理， 第二个参数.应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标
        Destination destination = session.createTopic(topicName);

        //6创建一个消费者
        MessageConsumer consumber = session.createConsumer(destination);

        //7创建一个监听器
        consumber.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

        //8关闭连接  消息是异步的 ，在程序退出是关闭
        //connection.close();
    }

}
