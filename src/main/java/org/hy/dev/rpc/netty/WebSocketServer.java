package org.hy.dev.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer {


	public static final int DEFAULT_WEBSOCKET_PORT = 9000;
	
	private final static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	private int port = DEFAULT_WEBSOCKET_PORT;
	
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;	
	private ServerBootstrap b;

	private WebSocketServerInitializer websocketServerInitializer = new WebSocketServerInitializer();	
	
	public void start() throws Exception{
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
        b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(websocketServerInitializer);
        ChannelFuture f = b.bind(port).sync();
        logger.info("Web Socket Server started at port " + port);
        f.channel().closeFuture().sync();

	}
	
	public void stop() throws Exception{		
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();		
	}	
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws Exception {
		
		new WebSocketServer().start();
	}

}
