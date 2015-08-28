package at.willhaben.aws.cfvoting;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.StringInputStream;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * AWS Lambda Class for getting voting options for one voting token
 */
public class GetOptions {

    public static void main(String[] args) {
        GetOptions getOptions = new GetOptions();

        String[] options = getOptions.getOptions("12345a");

        System.out.print(options);
    }

    public GetOptions() {

    }


    public String[] myHandler(String tokenId, Context context) throws UnsupportedEncodingException {

        LambdaLogger logger = context.getLogger();
        logger.log("get options for " + tokenId);
        return getOptions(tokenId);
    }

    private String[] getOptions(String tokenId) {


        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(Regions.EU_WEST_1));
        DynamoDB dynamoDB = new DynamoDB(client);


        Table table = dynamoDB.getTable("wh-voting");

        Item item = table.getItem("token", tokenId);

        if (item == null) throw new IllegalArgumentException();

        Object options = item.asMap().get("options");

        if (options instanceof List) {
            List list = (List) options;

            String[] objects = new String[list.size()];
            int i = 0;
            for (Object option : list.toArray()) {
                objects[i++] = (String) option;
            }
            return objects;
        } else {
            throw new IllegalArgumentException();
        }


    }

}