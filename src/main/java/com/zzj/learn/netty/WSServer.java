package com.zzj.learn.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WSServer {

    private static class SingletionWSServer{
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance(){
        return SingletionWSServer.instance;
    }

    private EventLoopGroup mainGroup;
    private  EventLoopGroup subGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture future;


    public WSServer(){

         mainGroup = new NioEventLoopGroup();
         subGroup = new NioEventLoopGroup();
         serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainGroup,subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }

    public void start(){
        this.future = serverBootstrap.bind(8088);
        System.out.println("netty---webSocket server 启动完毕");
    }

//    public static void main(String[] args) throws Exception{
//
//        EventLooproup mainGroup = new NioEventLoopGroup();
//        EventLoopGroup subGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.group(mainGroup,subGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(null);
//            ChannelFuture future = serverBootstrap.bind(8088).sync();
//            future.channel().closeFuture().sync();
//        }finally {
//
//            mainGroup.shutdownGracefully();
//            subGroup.shutdownGracefully();
//
//        }
//
//    }
}
