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

/**
 * A class to send confirmation notification to customers via SMS
 * @author Chen Xingyu
 */
public class Notification {
	/**
	 * a method to send SMS message to customers with given phone number
	 * @param message the message to be sent
	 * @param phoneNumber the phone number for the message to be sent to
	 */
	public static void sendSMS(String message, String phoneNumber) 
	{
		BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials("your_access_key_ID", "your_access_key");
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
