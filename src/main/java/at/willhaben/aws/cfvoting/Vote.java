package at.willhaben.aws.cfvoting;


public class Vote {

    private String tokenId, vote;

    public Vote() {
    }

    public Vote(String tokenId, String vote) {
        this.tokenId = tokenId;
        this.vote = vote;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
