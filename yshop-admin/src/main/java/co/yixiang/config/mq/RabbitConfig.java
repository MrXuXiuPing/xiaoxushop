//package co.yixiang.config.mq;
//
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.xmlbeans.impl.regex.REUtil;
//import org.omg.CORBA.Environment;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///***
// * 配置Rabbit相关配置
// */
//@Configuration
//@Slf4j
//@ConfigurationProperties(prefix = "spring.rabbitmq")
//public class RabbitConfig {
//
//    private String host;
//
//    private int port;
//
//    private String username;
//
//    private String password;
//
//    private static String EXCHANGE_A = "EXCHANGE_A";
//    public static final String QUEUE_A = "QUEUE_A";
//    public static final String ROUTINGKEY_A = "ROUTINGKEY_A";
//    public static final String FANOUT_EXCHANGE = "FANOUT_EXCHANGE";
//
////    @Autowired
////    private Environment environment;
//
//    @Autowired
//    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
//        cachingConnectionFactory.setUsername(username);
//        cachingConnectionFactory.setPassword(password);
//        cachingConnectionFactory.setVirtualHost("/");
//        cachingConnectionFactory.setPublisherConfirms(true);
//        cachingConnectionFactory.setPublisherReturns(true);
//        return cachingConnectionFactory;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                log.info("ConfirmCallback:     " + "相关数据：{}", correlationData);
//                log.info("ConfirmCallback:     " + "确认情况：{}", ack);
//                log.info("ConfirmCallback:     " + "原因：{}", cause);
//                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
//            }
//        });
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("ReturnCallback:     " + "消息：{}", message);
//                log.info("ReturnCallback:     " + "回应码：{}", replyCode);
//                log.info("ReturnCallback:     " + "回应信息：{}", replyText);
//                log.info("ReturnCallback:     " + "交换机：{}", exchange);
//                log.info("ReturnCallback:     " + "路由键：{}", routingKey);
//                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
//            }
//        });
//        return rabbitTemplate;
//    }
//
//    /***
//     * 交换机
//     * @return
//     */
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(EXCHANGE_A);
//    }
//
//    /***
//     * 获取队列
//     * @return
//     */
//    @Bean
//    public Queue queue() {
//        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
//        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
//        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
//        // return new Queue("QUEUE_A",true,true,false);
//        //一般设置一下队列的持久化就好,其余两个就是默认false
//        return new Queue(QUEUE_A, true);//队列持久
//    }
//
////    @Bean
////    public FanoutExchange fanoutExchange() {
////        return new FanoutExchange(RabbitConfig.FANOUT_EXCHANGE);
////    }
//
//    /***
//     * //绑定  将队列和交换机绑定, 并设置用于匹配键：ROUTINGKEY_A
//     * @return
//     */
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitConfig.ROUTINGKEY_A);
//    }
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
//        return new SimpleRabbitListenerContainerFactory();
//    }
//
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        //加载处理消息A的队列
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        /***
//         * 设置接收多个队列里面的消息，这里设置接收队列A
//         * 假如想一个消费者处理多个队列里面的信息可以如下设置：
//         * container.setQueues(queueA(),queueB(),queueC());
//         */
//        container.setQueues(queue());
//        container.setExposeListenerChannel(true);
//        //设置最大的并发的消费者数量
//        container.setMaxConcurrentConsumers(10);
//        //最小的并发消费者的数量
//        container.setConcurrentConsumers(1);
//        //设置确认模式手工确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                /**
//                 通过basic.qos方法设置prefetch_count=1，
//                 这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//                 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它
//                 */
//                channel.basicQos(1);
//                byte[] body = message.getBody();
//                log.info("接收处理队列A当中的消息: {}", new String(body));
//                /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//                 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，
//                 然后RabbitMQ才会将消息删除。
//                 */
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
//        return container;
//    }
//
//    /***
//     * 配置单一消费者
//     * @return
//     */
//    @Bean(name = "singleListenerContainer")
//    public SimpleRabbitListenerContainerFactory listenerContainer() {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory());
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setConcurrentConsumers(1);
//        factory.setMaxConcurrentConsumers(1);
//        factory.setPrefetchCount(1);
//        factory.setTxSize(1);
//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        return factory;
//    }
//
////    @Bean
////    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
////        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
////        factoryConfigurer.configure(factory, connectionFactory());
////        factory.setMessageConverter(new Jackson2JsonMessageConverter());
////        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
////        factory.setConcurrentConsumers(environment);
////        factory.setMaxConcurrentConsumers(environment);
////        factory.setPrefetchCount(environment);
////        return factory;
////    }
//
//}
