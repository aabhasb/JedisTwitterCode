package com.redislabs.ingest.pubsub;

import java.util.Arrays;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

/*
 * This is the main class - the starting point of the ingest process. 
 * Listens to the Twitter feed via PubNub, starts all the publishers
 * and subscribers (filters).
 * 
 * Flow of data:                                                                
 *                      
 * IngestPubSub -> (channel: AllData)
 *                      --> englishFilter ->  (channel: EnglishTweets)
 *                                                        -> hashTagCollector 
 *                      --> influencerFilter -> (channel: InfluencerTweets)
 *                                                        -> influencerCollector
 * 
 */
public class IngestPubSub
{
	final static String SUB_KEY_TWITTER = "sub-c-78806dd4-42a6-11e4-aed8-02ee2ddab7fe";
	final static String CHANNEL_TWITTER = "pubnub-twitter";
	
	Publisher publisher = null;
	Subscriber englishFilter = null;
	Subscriber influencerFilter = null;
	static Subscriber[] hashtagCollector;// = new Subscriber[10];
	Subscriber influencerCollector = null;
	static int messageCount = 0;
	
	/*
	 * Start the main program; start all the components 
	 */
	public static void main(String[] args) throws Exception
	{
		
		try{
			ConnectionUtils.setHosts(args[0]);
			int numSubscribers = Integer.parseInt(args[1]);
			hashtagCollector = new Subscriber[numSubscribers];
			IngestPubSub ing = new IngestPubSub();
			ing.start(numSubscribers);	
		}catch(Exception pe){
			pe.printStackTrace();
		}
		
	}
	
	/*
	 * Start all the services, register the callback as a listener  
	 */
	public void start(int numSubScribers) throws Exception{
		PNConfiguration pnConfig = new PNConfiguration();
		pnConfig.setSubscribeKey(SUB_KEY_TWITTER);
		pnConfig.setSecure(false);
		
		PubNub pubnub = new PubNub(pnConfig);
		
		pubnub.subscribe().channels(Arrays.asList(CHANNEL_TWITTER)).execute();
		
		// All incoming data are published to this channel
		//publisher = new Publisher("alldata");
		
		// EnglishTweetFilter subscribes to AllData and publishes to EnglishTweets 
		// englishFilter = new EnglishTweetFilter("English Filter","alldata", "englishtweets");
		
		// InfluencerTweetFilter subscribes to AllData and publishes to InfluencerTweets
		//influencerFilter = new InfluencerTweetFilter("Influencer Filter", "alldata", "influencertweets");
		
		// HashTagCollector subscribes to EnglishTweets
		for (int i=0; i<numSubScribers; i++) {
			hashtagCollector[i] = new HashTagCollector("Hashtag Collector", "alldata");
		}

		// InfluencerCollector subscribes to InfluencerTweets
		//influencerCollector = new InfluencerCollector("Influencer Collector", "influencertweets");
		
		// PubNub event callback
		/*SubscribeCallback subscribeCallback = new SubscribeCallback() {
		    @Override
		    public void status(PubNub pubnub, PNStatus status) {
		        if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
		            // internet got lost, do some magic and call reconnect when ready
		            pubnub.reconnect();
		        } else if (status.getCategory() == PNStatusCategory.PNTimeoutCategory) {
		            // do some magic and call reconnect when ready
		            pubnub.reconnect();
		        } else {
		            //System.out.println(status.toString());
		        }
		    }
		 
		    // Receive the message and publish to AllData channel
		    @Override
		    public void message(PubNub pubnub, PNMessageResult message) {
		    	try{
		    		//System.out.println(message.getMessage());
		    		if (messageCount++<1) {
		    		
		    			publisher.publish(message.getMessage().toString());	
		    		}
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
		    	
		    	
		    }
		 
		    @Override
		    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
		    }
		};
		 
		// Add callback as a listener (PubNub code) 
		pubnub.addListener(subscribeCallback);*/
		
	}
}