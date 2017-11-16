package com.redislabs.ingest.pubsub;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import redis.clients.jedis.Jedis;

/*
 * HashTagCollector is a custom class that listens to English Tweets,
 * extracts all the hash tags, and indexes them in a Redis Sorted Set 
 */
public class HashTagCollector extends Subscriber{
	
	// Regular expression to extract a hashtag
	Pattern HASHPATTERN = Pattern.compile("#(\\w+)");
	
	// Redis connection to access the Sorted Set
	private RedisConnection conn = null;
	private Jedis jedis = null;
	static int count = 0;
	private int collectorNumber = 0;

	/*
	 * @param subscriberName: name of this object
	 * @param channel: name of the channel to listen to
	 */
	public HashTagCollector(String subscriberName, String channel) throws Exception{
		super(subscriberName, channel);
		
		conn = RedisConnection.getRedisConnection();
		jedis = conn.getJedis();
		collectorNumber = ++count;
		
	}
	
	/*
	 * Custom filter: extract hashtags from each tweet, update the Sorted Set
	 * (non-Javadoc)
	 * @see com.redislabs.ingest.pubsub.Subscriber#onMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void onMessage(String subscriberChannel, String message){	
		JsonParser jsonParser = new JsonParser();
		final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss:S");
		Date date = new Date();
		
//		JsonElement jsonElement = jsonParser.parse(message);
//		JsonObject jsonObject = jsonElement.getAsJsonObject();
//		
//		if(jsonObject.get("lang") != null && jsonObject.get("lang").getAsString().equals("en")){
//			Matcher mat = HASHPATTERN.matcher(jsonObject.get("text").getAsString());
//			while(mat.find()){
//				System.out.println(collectorNumber+":"+mat.group(1)+":"+SDF.format(date));
//				// Update the Sorted Set with ZINCRBY command
//				//jedis.zincrby("hashtagset", 1, mat.group(1));
//			}
//		}
		System.out.println(message+":"+SDF.format(date));
	}
	
}