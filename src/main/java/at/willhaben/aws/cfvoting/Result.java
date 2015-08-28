package at.willhaben.aws.cfvoting;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * AWS Lambda Class for getting voting options for one voting token
 */
public class Result {

    public static void main(String[] args) {
        Result result = new Result();
        final Map<String, Object> map = result.getResult("Crazy Friday 28-08-2015");
        System.out.print(map);
    }

    public Result() {

    }


    public Map<String, Object> myHandler(String projectName, Context context) throws UnsupportedEncodingException {

        LambdaLogger logger = context.getLogger();
        logger.log("get options for " + projectName);
        return getResult(projectName);
    }                                                                      k

    private Map<String, Object> getResult(String project) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(Regions.EU_WEST_1));
        DynamoDB dynamoDB = new DynamoDB(client);


        Table table = dynamoDB.getTable("wh-voting-result");

        Item item = table.getItem("project", project);

        if (item == null) throw new IllegalArgumentException();

        return item.asMap();
    }

}