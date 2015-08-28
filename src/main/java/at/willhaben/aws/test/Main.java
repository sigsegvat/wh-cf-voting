package at.willhaben.aws.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.StringInputStream;

import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) {

    }

    public Main() {

    }


    public String myHandler(int myCount, Context context) throws UnsupportedEncodingException {
        AmazonS3Client amazonS3Client = new AmazonS3Client();
        amazonS3Client.putObject("wh-template-test",myCount+".txt",new StringInputStream("asdf"), new ObjectMetadata());



        LambdaLogger logger = context.getLogger();
        logger.log("received : " + myCount);
        return String.valueOf(myCount);
    }

}