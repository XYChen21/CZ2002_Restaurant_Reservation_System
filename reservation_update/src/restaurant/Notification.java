package restaurant;

import java.util.*;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class Notification {
//	AWS_ACCESS_KEY_ID = "AKIATYW5Q773CIBXA2X5";
//	AWS_SECRET_KEY = "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";
	public static void sendSMS(String message, String phoneNumber) 
	{
		AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
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
