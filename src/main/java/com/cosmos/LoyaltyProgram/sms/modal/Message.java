
package com.cosmos.LoyaltyProgram.sms.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "num_parts",
    "sender",
    "content"
})
public class Message {

    @JsonProperty("num_parts")
    private Long numParts;
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("content")
    private String content;

    @JsonProperty("num_parts")
    public Long getNumParts() {
        return numParts;
    }

    @JsonProperty("num_parts")
    public void setNumParts(Long numParts) {
        this.numParts = numParts;
    }

    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [numParts=").append(numParts).append(", sender=").append(sender).append(", content=")
				.append(content).append("]");
		return builder.toString();
	}

}
