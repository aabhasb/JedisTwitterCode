package com.redislabs.ingest.pubsub;

import java.text.SimpleDateFormat;
import java.util.Date;

import redis.clients.jedis.Jedis;

/*
 * Publisher class: maintains a Redis connection, registers a channel, 
 * provides the method to publish a message to the registered channel
 *   
 */
public class Publisher {

	// Redis connection
	RedisConnection conn = null;

	// Jedis object
	Jedis jedis = null;

	private String channel = "defaultchannel";

	/*
	 * @param channelName: Name of the channel the messages are published to.
	 */
	public Publisher(String channelName) throws Exception {
		channel = channelName;
		conn = RedisConnection.getRedisConnection();
		jedis = conn.getJedis();

	}

	/*
	 * @param msg: The message that's published to the channel
	 */
	public void publish(String msg) throws Exception {
		// jedis.publish(channel, msg);

		final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss:S");
		Date date = new Date();
		jedis.publish(channel, "Message@" + SDF.format(date));
	}
	
	/*
	 * Default message published
	 */
	public void publish() throws Exception {
		// jedis.publish(channel, msg);

		final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss:S");
		Date date = new Date();
		jedis.publish(channel, "Message@" + SDF.format(date));
	}
}