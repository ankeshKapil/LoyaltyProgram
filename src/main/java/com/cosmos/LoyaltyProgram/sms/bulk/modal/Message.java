package com.cosmos.LoyaltyProgram.sms.bulk.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"number",
"text"
})
public class Message {

@JsonProperty("number")
private String number;
@JsonProperty("text")
private String text;

@JsonProperty("number")
public String getNumber() {
return number;
}

@JsonProperty("number")
public void setNumber(String number) {
this.number = number;
}

@JsonProperty("text")
public String getText() {
return text;
}

@JsonProperty("text")
public void setText(String text) {
this.text = text;
}

}