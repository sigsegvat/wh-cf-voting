package at.willhaben.aws.cfvoting;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;


import java.io.UnsupportedEncodingException;

/**
 * AWS Lambda Class for casting one vote
 */
public class CastVote {

    public static void main(String[] args) {

    }

    public CastVote() {

    }


    public String myHandler(Vote vote, Context context) throws UnsupportedEncodingException {

        LambdaLogger logger = context.getLogger();
        logger.log("received : " + vote);
        return String.valueOf(vote.getVote());
    }

}