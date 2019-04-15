package CommandPattern.userStories;

import CommandPattern.Command;

import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

public class UserApp {
    private static final String RPC_QUEUE_NAME = "users";
    private static int numberOfThreads = 2;
    private static int _prefetchCount = 50;

    public static int get_prefetchCount() {
        return _prefetchCount;
    }

    public static void set_prefetchCount(int _prefetchCount) {
        UserApp._prefetchCount = _prefetchCount;
    }

    public static int getNumberOfThreads() {
        return numberOfThreads;
    }

    public static void setNumberOfThreads(int numberOfThreads) {
        UserApp.numberOfThreads = numberOfThreads;
    }

    public static void main(String args[]) {
        AppThreadPool appPool = new AppThreadPool(numberOfThreads);
        ExecutorService executor = appPool.getInstance();
        ConnectionFactory factory = new ConnectionFactory();
        //TODO: Get ip:port from config file
        factory.setHost("localhost");
        Connection connection = null;
        CommandMap.instantiate();
        try {
            //set up connection
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            //declare queue
            AMQP.Queue.DeclareOk count=channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null);
            //attempt to split work between workers evenly
            channel.basicQos(get_prefetchCount());
            System.out.println(" [x] Awaiting RPC requests");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //set properties according to the message received
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();
                    String message = new String(body, "UTF-8");
                    Runnable task = new Runnable() {
                        public void run() {
                            try {
                                System.out.println("Responding to"+properties.getCorrelationId());
                                System.out.println(message);
                                createResponse(message, channel, properties, replyProps, envelope);
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                    };
                    executor.submit(task);
                }
            };
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    public  static void createResponse(String message, Channel channel,
                                       AMQP.BasicProperties properties,
                                       AMQP.BasicProperties replyProps,
                                       Envelope envelope)  throws IOException{
        JSONObject response = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject(message);
            String method = jsonObject.getString("method");
            Command x  = (Command) CommandMap.queryClass(method).newInstance();
            response = x.execute(jsonObject);
            Command command = null;
        } catch (Exception e) {
            e.printStackTrace();
            String reply = e.toString();
            response = new JSONObject(reply);
            System.out.println(" [.] " + e.toString());
        } finally {
            channel.basicPublish("", properties.getReplyTo(), replyProps, response.toString().getBytes("UTF-8"));
            System.out.println("Responded to:"+properties.getCorrelationId());
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }
}