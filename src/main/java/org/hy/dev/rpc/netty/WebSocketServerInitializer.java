package org.hy.dev.rpc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	private final static Logger logger = LoggerFactory.getLogger(WebSocketServerInitializer.class);
	
	@Resource
	private WebSocketServerHandler webSocketServerHandler = new WebSocketServerHandler();
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	if(logger.isDebugEnabled()) logger.debug("init socketChannel:{}",ch);
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("handler", webSocketServerHandler);
        if(logger.isDebugEnabled()) logger.debug("init socketChannel successfully, handler:{}",webSocketServerHandler);
    }
}
