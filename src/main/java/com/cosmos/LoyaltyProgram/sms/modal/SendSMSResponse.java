
package com.cosmos.LoyaltyProgram.sms.modal;

import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "balance",
    "batch_id",
    "cost",
    "num_messages",
    "message",
    "messages",
    "errors",
    "warnings",
    "status"
})
public class SendSMSResponse {

    @JsonProperty("balance")
    private Long balance;
    @JsonProperty("batch_id")
    private Long batchId;
    @JsonProperty("cost")
    private Long cost;
    @JsonProperty("num_messages")
    private Long numMessages;
    @JsonProperty("message")
    @Valid
    private Message message;
    @JsonProperty("messages")
    @Valid
    private List<Messages> messages = null;
    @JsonProperty("errors")
    @Valid
    private List<Error> errors = null;
    @JsonProperty("warnings")
    @Valid
    private List<Warning> warnings = null;
    @JsonProperty("status")
    private String status;

    @JsonProperty("balance")
    public Long getBalance() {
        return balance;
    }

    @JsonProperty("balance")
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @JsonProperty("batch_id")
    public Long getBatchId() {
        return batchId;
    }

    @JsonProperty("batch_id")
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @JsonProperty("cost")
    public Long getCost() {
        return cost;
    }

    @JsonProperty("cost")
    public void setCost(Long cost) {
        this.cost = cost;
    }

    @JsonProperty("num_messages")
    public Long getNumMessages() {
        return numMessages;
    }

    @JsonProperty("num_messages")
    public void setNumMessages(Long numMessages) {
        this.numMessages = numMessages;
    }

    @JsonProperty("message")
    public Message getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(Message message) {
        this.message = message;
    }

    @JsonProperty("messages")
    public List<Messages> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    @JsonProperty("errors")
    public List<Error> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @JsonProperty("warnings")
    public List<Warning> getWarnings() {
        return warnings;
    }

    @JsonProperty("warnings")
    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SendSMSResponse [balance=").append(balance).append(", batchId=").append(batchId)
				.append(", cost=").append(cost).append(", numMessages=").append(numMessages).append(", message=")
				.append(message).append(", messages=").append(messages).append(", errors=").append(errors)
				.append(", warnings=").append(warnings).append(", status=").append(status).append("]");
		return builder.toString();
	}
    
    

}
