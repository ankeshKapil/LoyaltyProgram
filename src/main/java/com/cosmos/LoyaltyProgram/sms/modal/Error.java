
package com.cosmos.LoyaltyProgram.sms.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "message"
})
public class Error {

    @JsonProperty("code")
    private Long code;
    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    public Long getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Long code) {
        this.code = code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Error [code=").append(code).append(", message=").append(message).append("]");
		return builder.toString();
	}
    
    

}
