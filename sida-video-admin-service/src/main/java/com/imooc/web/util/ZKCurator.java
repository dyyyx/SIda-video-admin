package com.imooc.web.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKCurator {

	private CuratorFramework client = null;
	final static Logger log = LoggerFactory.getLogger(ZKCurator.class);

	public ZKCurator(CuratorFramework client) {
		this.client = client;
	}

	public void init() {
		client = client.usingNamespace("admin");

		try {
			if (client.checkExists().forPath("/bgm") == null) {
				/**
				 * 对于zk来讲，有两种类型的节点: 持久节点: 当你创建一个节点的时候，这个节点就永远存在，除非你手动删除 临时节点:
				 * 你创建一个节点之后，会话断开，会自动删除，当然也可以手动删除
				 */
				client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE)
						.forPath("/bgm");
				log.info("zookeeper初始化成功....");

				log.info("zookeeper服务器状态:{}", client.isStarted());
			}
		} catch (Exception e) {
			log.error("zookeeper客户端连接初始化失败...");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 增加或删除bgm，向zk-server创建子节点，让小程序后端进行监听
	 * @param bgmId
	 * @param operaObj
	 */
	public void sendBgmOperator(String bgmId,String operaObj){
		try {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE)
					.forPath("/bgm/" + bgmId, operaObj.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
