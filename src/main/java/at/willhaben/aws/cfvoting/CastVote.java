package at.willhaben.aws.cfvoting;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * AWS Lambda Class for casting one vote
 */
public class CastVote {

    public static void main(String[] args) {
        UpdateItemOutcome asdf = new CastVote().vote(new Vote("12345", "22asdf"));
        int i = 0;
    }

    public CastVote() {

    }


    public String myHandler(Vote vote, Context context) throws UnsupportedEncodingException {

        LambdaLogger logger = context.getLogger();
        logger.log("received : " + vote.getTokenId());

        UpdateItemOutcome updated = vote(vote);

        logger.log("voted");

        return vote.getVote();
    }

    private UpdateItemOutcome vote(Vote vote) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(Regions.EU_WEST_1));
        DynamoDB dynamoDB = new DynamoDB(client);


        Table table = dynamoDB.getTable("wh-voting");

        Item item = table.getItem("token", vote.getTokenId());

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("token", vote.getTokenId())
                .withUpdateExpression("set #vote = :vote")
                .withNameMap(new NameMap().with("#vote", "vote"))
                .withValueMap(new ValueMap().withString(":vote", vote.getVote()))
                .withReturnValues(ReturnValue.UPDATED_NEW);



        String project = (String) item.asMap().get("project");
        votingResult(project, vote.getVote(), dynamoDB, client);

        return table.updateItem(updateItemSpec);
    }

    private void votingResult(String project, String vote, DynamoDB dynamoDB, AmazonDynamoDBClient client) {
        Table table = dynamoDB.getTable("wh-voting-result");

        Item item = table.getItem("project", project);
        if (item == null) {
            table.putItem(new Item().
                    withPrimaryKey("project", project).
                    withNumber(vote, 1));
        } else {
            Map<String, AttributeValue> keyMap = new HashMap<>();
            keyMap.put("project", new AttributeValue(project));

            Map<String, AttributeValueUpdate> map = new HashMap<>();
            AttributeValueUpdate update = new AttributeValueUpdate().withAction(AttributeAction.ADD);
            map.put(vote, update);
            UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                    .withTableName("wh-voting-result")
                    .withKey(keyMap)
                    .addAttributeUpdatesEntry(
                            "vote", new AttributeValueUpdate()
                                    .withValue(new AttributeValue().withN("1"))
                                    .withAction(AttributeAction.ADD));
            client.updateItem(updateItemRequest);
        }
    }

}