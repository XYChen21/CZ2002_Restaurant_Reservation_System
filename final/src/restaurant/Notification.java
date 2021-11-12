package restaurant;

import java.util.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class Notification {
//  account ID 259236364278
//	AWS_ACCESS_KEY_ID = "AKIATYW5Q773CIBXA2X5";
//	AWS_SECRET_KEY = "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";
	public static void sendSMS(String message, String phoneNumber) 
	{
		BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials("AKIATYW5Q773CIBXA2X5", "+F8r/IdVaiZ1fb+cVDu2HY6Uh2MGzVuovsGXGC7i");
		AmazonSNS snsClient = AmazonSNSClient
							.builder()
			                .withRegion(Regions.AP_SOUTHEAST_1)
			                .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
			                .build();
		Map<String, MessageAttributeValue> attr = new HashMap<String, MessageAttributeValue>(1);
		attr.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue().withStringValue("Restaurant").withDataType("String"));
		
		PublishRequest request = new PublishRequest()
				.withMessage(message)
				.withPhoneNumber(phoneNumber)
				.withMessageAttributes(attr);
		PublishResult result = snsClient.publish(request);
		System.out.println("Message sent successfully--" + result.getMessageId());
    }
}