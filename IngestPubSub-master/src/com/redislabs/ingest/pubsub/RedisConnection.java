package com.redislabs.ingest.pubsub;

import redis.clients.jedis.Jedis;

/*
 * RedisConnection class
 * 
 */
public class RedisConnection
{
	// The IP address/URL of the machine where Redis is running
	//private static final String URL = "10.131.0.1";
	
	// Redis port. This program assumes there's no authentication set on Redis.
	//private static final int PORT = 6379;
	
	private Jedis jedis = null;
	
	/*
	 * Factory method to return a RedisConnection object 
	 */
	public static synchronized RedisConnection getRedisConnection() throws Exception{
		RedisConnection conn = new RedisConnection();
		conn.connect();
		return conn;
	}
	
	/*
	 * Connects to the Redis instance
	 */
	private void connect() throws Exception{
		String hostPort = ConnectionUtils.getHost();
		String[] hostPortSplit = hostPort.split(":");
		String hostIp = hostPortSplit[0];
		int port = Integer.parseInt(hostPortSplit[1]);
		jedis = new Jedis(hostIp, port);		
		System.out.println("Connection Successful to host: " + hostIp + " port: " + port);
	}
	
	/*
	 * @return jedis object
	 */
	public Jedis getJedis(){
		return jedis;
	}
	
}