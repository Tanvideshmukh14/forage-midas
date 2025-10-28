package com.jpmc.midascore.foundation;

public class Transaction {

    private Long senderId;
    private Long receiverId;
    private Float amount;
    private Float incentive;


        private String id;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }



    public Transaction() {}

    public Transaction(Long senderId, Long receiverId, Float amount, Float incentive) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.incentive = incentive;
    }

    public Long getSenderId() { return senderId; }
    public Long getReceiverId() { return receiverId; }
    public Float getAmount() { return amount; }
    public Float getIncentive() { return incentive; }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setIncentive(Float incentive) {
        this.incentive = incentive;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", amount=" + amount +
                ", incentive=" + incentive +
                '}';
    }

}
