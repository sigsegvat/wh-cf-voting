package at.willhaben.aws.cfvoting;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.StringInputStream;

import java.io.UnsupportedEncodingException;

/**
 * AWS Lambda Class for computing voting result
        */
public class AggregateVotes {

    public static void main(String[] args) {

    }

    public AggregateVotes() {

    }


    public String myHandler(int myCount, Context context) throws UnsupportedEncodingException {


        LambdaLogger logger = context.getLogger();
        logger.log("received : " + myCount);
        return String.valueOf(myCount);
    }

}