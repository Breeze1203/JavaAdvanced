package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.logging.Logger;

/**
 * 周六闲得无聊..
 *
 * @author Levin
 * @create 2017/9/16 0016
 */
public class WebSocketServer {

    private static final Logger LOG = Logger.getLogger(WebSocketServer.class.getName());

    public static void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
                            pipeline.addLast("handler", new WebSocketServerHandler()); // WebSocket服务端Handler
                        }
                    });
            Channel channel = bootstrap.bind(port).sync().channel();
            LOG.info("WebSocket 已经启动，端口：" + port + ".");
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        WebSocketServer.run(Init.PORT);
    }

}