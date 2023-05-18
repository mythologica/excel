package org.example.aws;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class SesJsonParser implements Serializable {

    /**
     * 이벤트 유형을 설명하는 문자열입니다. 가능한 값: Bounce, Complaint, Delivery, Send, Reject, Open, Click, Rendering Failure, DeliveryDelay 또는 Subscription
     * 이벤트 게시를 설정하지 않은 경우 이 필드의 이름은 notificationType입니다.
     */
    public static enum SesStatus {

        BOUNCE("Bounce","bounce", true),//반송 관련 정보가 포함되어 있습니다.
        COMPLAINT("Complaint","complaint", true),//수신 거부 관련 정보가 포함되어 있습니다.
        DELIVERY("Delivery","delivery", true),//전송 관련 정보가 포함되어 있습니다.
        SEND("Send","send", false),//Send인 경우에만 존재합니다.
        REJECT("Reject","reject", true),//거부 관련 정보가 포함되어 있습니다.
        OPEN("Open","open", true),//열기 이벤트 관련 정보가 포함되어 있습니다.
        CLICK("Click","click", true), //클릭 이벤트 관련 정보가 포함되어 있습니다.
        RENDERING_FAILURE("Rendering Failure","failure", true), //렌더링 오류 이벤트 관련 정보가 포함되어 있습니다.
        DELIVERYDELAY("DeliveryDelay","deliveryDelay", true), //이메일 전송 지연에 대한 정보가 포함되어 있습니다.
        SUBSCRIPTION("Subscription","subscription", true), //구독 기본 설정에 대한 정보가 포함되어 있습니다.
        ERROR("Error","error", false); //위 어떤 경우에도 해당 되지 않은 경우

        private final String eventType;
        private final String columnKey;
        private final boolean hasBody;

        SesStatus(String eventType,String columnKey, boolean hasBody) {
            this.eventType = eventType;
            this.columnKey = columnKey;
            this.hasBody = hasBody;
        }
        public String getEventType() { return eventType; }
        public String getColumnKey() { return columnKey; }
        public boolean isHasBody() { return hasBody; }

        public boolean equalsEventType( String eventType ) {
            return this.getEventType().equalsIgnoreCase(eventType);
        }
    }

    private DateTimeFormatter formatter;
    private boolean isSuccess = true;
    private String messageId ="0000000000000000-00000000-0000-0000-0000-000000000000-000000";
    private String eventType;
    private String toEmail;
    private String timestamp;
    private String option;
    private String jsonString;

    private String serverType;
    private String itemKey;
    private SesStatus sesStatus;

    public SesJsonParser(String jsonString) {
        if( jsonString != null ) {
            this.jsonString = jsonString;
            this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("Asia/Seoul"));
            this.parse();
        }
    }

    private String getString(JsonObject jsonObject,String key) {
        return getString(jsonObject,key,"");
    }

    private String getString(JsonObject jsonObject,String key, String defaultString) {
        if( jsonObject != null && jsonObject.has(key) ){
            return jsonObject.get(key).getAsString();
        }
        return defaultString;
    }

    private JsonArray getArray(JsonObject jsonObject,String key) {
        return getArray(jsonObject,key, new JsonArray());
    }

    private JsonArray getArray(JsonObject jsonObject,String key, JsonArray defaultString) {
        if( jsonObject != null && jsonObject.has(key) ){
            return jsonObject.get(key).getAsJsonArray();
        }
        return defaultString;
    }

    private SesJsonParser parse() {
//        String message = JsonParser.parseString(this.jsonString).getAsJsonObject().get("Message").getAsString();
        String message = this.jsonString;

        JsonObject messageObject = JsonParser.parseString(message).getAsJsonObject();
        JsonObject mailObject = messageObject.get("mail").getAsJsonObject();

        this.messageId = getString(mailObject,"messageId");
        this.eventType = getString(messageObject, "eventType");

        JsonArray headers = getArray(mailObject, "headers");
        int headerCount = headers.size();
        for(int i=0;i<headerCount;i++) {
            JsonObject header = headers.get(i).getAsJsonObject();
            if("To".equalsIgnoreCase( getString(header,"name") ) ) {
                this.toEmail = getString(header,"value");
            } else if("OPTION".equalsIgnoreCase( getString(header,"name") ) ) {
                this.option = getString(header,"value");
                //TODO: option 값을 쪼게서 서버#메일ID 사용하였으면
                this.serverType = this.option;
            }
        }

        if( SesStatus.BOUNCE.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.BOUNCE;
            parseBounce(messageObject);

        } else if( SesStatus.COMPLAINT.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.COMPLAINT;
            parseComplaint(messageObject);

        } else if( SesStatus.DELIVERY.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.DELIVERY;
            parseDelivery(messageObject);

        } else if( SesStatus.SEND.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.SEND;
            parseSend(messageObject);

        } else if( SesJsonParser.SesStatus.REJECT.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.REJECT;
            parseReject(messageObject);

        } else if( SesJsonParser.SesStatus.OPEN.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.OPEN;
            parseOpen(messageObject);

        } else if( SesJsonParser.SesStatus.CLICK.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.CLICK;
            parseClick(messageObject);

        } else if( SesJsonParser.SesStatus.RENDERING_FAILURE.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.RENDERING_FAILURE;
            parseRenderingFailure(messageObject);

        } else if( SesStatus.DELIVERYDELAY.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.DELIVERYDELAY;
            parseDeliveryDelay(messageObject);

        } else if( SesStatus.SUBSCRIPTION.equalsEventType(eventType) ) {
            this.sesStatus = SesStatus.SUBSCRIPTION;
            parseSubscription(messageObject);

        } else {
            this.isSuccess = false;
            this.sesStatus = SesStatus.ERROR;
        }
        return this;
    }

    private void parseBounce(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }

    private void parseComplaint(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseDelivery(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseSend(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get("mail").getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseReject(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get("mail").getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseOpen(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }

    private void parseClick(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseRenderingFailure(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get("mail").getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseDeliveryDelay(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
    private void parseSubscription(JsonObject messageObject) {
        JsonObject eventObject = messageObject.get(sesStatus.getColumnKey()).getAsJsonObject();
        this.timestamp = formatter.format(Instant.parse(getString(eventObject,"timestamp")));
    }
}
