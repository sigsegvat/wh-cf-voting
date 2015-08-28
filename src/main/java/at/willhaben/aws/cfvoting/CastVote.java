package at.willhaben.aws.cfvoting;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;


import java.io.UnsupportedEncodingException;

/**
 * AWS Lambda Class for casting one vote
 */
public class CastVote {

    public static void main(String[] args) {
        new CastVote().vote(new Vote("12345", "test"));
    }

    public CastVote() {

    }


    public String myHandler(Vote vote, Context context) throws UnsupportedEncodingException {

        LambdaLogger logger = context.getLogger();
        logger.log("received : " + vote);

        vote(vote);

        return String.valueOf(vote.getVote());
    }

    private void vote(Vote vote) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(Regions.EU_WEST_1));
        DynamoDB dynamoDB = new DynamoDB(client);


        Table table = dynamoDB.getTable("wh-voting");

        Item item = table.getItem("token", vote.getTokenId());

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("token", vote.getTokenId())
                .withUpdateExpression("set #vote = :vote")
                .withNameMap(new NameMap().with("#vote", "vote"))
                .withValueMap(new ValueMap().withString(":vote", vote.getVote()));

        table.updateItem(updateItemSpec);

    }

}