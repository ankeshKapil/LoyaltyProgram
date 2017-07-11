
package com.cosmos.LoyaltyProgram.sms.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "recipient"
})
public class Messages {

    @JsonProperty("id")
    private String id;
    @JsonProperty("recipient")
    private Long recipient;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("recipient")
    public Long getRecipient() {
        return recipient;
    }

    @JsonProperty("recipient")
    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Messages [id=").append(id).append(", recipient=").append(recipient).append("]");
		return builder.toString();
	}
    

}
