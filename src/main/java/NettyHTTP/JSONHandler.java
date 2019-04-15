package NettyHTTP;

import com.rabbitmq.client.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import org.json.JSONObject;

import static io.netty.buffer.Unpooled.copiedBuffer;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class JSONHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        //System.out.println("JSON HANDLER");
        ByteBuf buffer = (ByteBuf) o;
        JSONObject jsonObject = new JSONObject(buffer.toString(CharsetUtil.UTF_8));
        System.out.println(jsonObject.getString("service"));
       // final String responseMessage = jsonObject.toString();
        String responseMessage  = contactMQ(jsonObject);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                copiedBuffer(responseMessage.getBytes())
        );
        channelHandlerContext.writeAndFlush(response);

    }

    protected String contactMQ(JSONObject object)
    {
        try {
            String message = object.toString();
            String requestQueueName = object.getString("service");

            //create connection to MQ, TODO: make mq ip:port variables from config file
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //send to MQ, TODO: convert queue to exchange?
            String corrId = UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(corrId)
                    .replyTo(replyQueueName)
                    .build();
            channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

            //listen to response, then check against corrID
            BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody(), "UTF-8"));
                }
            };

            String ctag = channel.basicConsume(replyQueueName, true, deliverCallback, consumerTag -> {
            });

            String result = response.take();
            channel.basicCancel(ctag);
            return result;


        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
