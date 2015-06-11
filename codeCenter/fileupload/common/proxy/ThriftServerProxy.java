package com.moxian.common.proxy;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.moxian.common.BeanConfig.ThriftServerConfig;
import com.moxian.common.proxy.impl.FileServerThriftImpl;
import com.moxian.common.proxy.service.FileServerThrift;


@Slf4j
public class ThriftServerProxy {
	   @Inject
	   private FileServerThriftImpl fileServerThriftImpl;
	   
	   @Inject
	   private ThriftServerConfig thriftServerConfig;
	 
	   private TServer server; 
	   
	   public ThriftServerProxy() {
	 
	   }
	 
	   public void start() {
	       new Thread() {
	           public void run() {
	               try {
	                   // 设置服务端口为 7912
	                   TServerSocket serverTransport = new TServerSocket(thriftServerConfig.getPort());
	                   // 设置协议工厂为 TBinaryProtocol.Factory
	                   Factory proFactory = new TBinaryProtocol.Factory();
	                   // 关联处理器与 Sms 服务的实现
	                   TProcessor processor = new FileServerThrift.Processor<FileServerThriftImpl>(
	                		   fileServerThriftImpl);
	                   Args args = new Args(serverTransport);
	                   args.processor(processor);
	                   args.protocolFactory(proFactory);
	                   server = new TThreadPoolServer(args);
	                   log.info("Start server on port 79232..."+thriftServerConfig.getPort());
	                   server.serve();
	               } catch (TTransportException e) {
	                   e.printStackTrace();
	               }
	           }
	       }.start();
	   }
	   
	   public void shutdown(){
	       log.info("shutdown server on port 7923...");
	       if(server != null){
	           server.stop();
	       }
	   }

}
