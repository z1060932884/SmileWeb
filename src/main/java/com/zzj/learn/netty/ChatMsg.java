package com.zzj.learn.netty;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ChatMsg implements Serializable {

    private String senderId;//发送者Id
    private String receiverId;//接收者Id
    private String  msg;//聊天内容
    private String  msgId;//消息Id
    private int type;//消息类型
    private int itemType;//消息item类型
    private int chatType;
    /**
     * 发送失败
     */
    private boolean sendFails;
    private String time;//时间
    /**
     * 是否发送成功
     */
    @JsonProperty(value = "isSend")
    private boolean isSend;
    /**
     * 是否读取
     */
    @JsonProperty(value = "isRead")
    private boolean isRead;

    private String userName;//用户名

    private String faceImage;//头像

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public boolean isSendFails() {
        return sendFails;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public void setSendFails(boolean sendFails) {
        this.sendFails = sendFails;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
