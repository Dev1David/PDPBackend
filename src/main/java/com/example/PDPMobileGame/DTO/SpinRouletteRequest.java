package com.example.PDPMobileGame.DTO;

public class SpinRouletteRequest {
    private Integer percentage;

    private Integer betBalance;

    public SpinRouletteRequest() {}

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getBetBalance() {
        return betBalance;
    }

    public void setBetBalance(Integer betBalance) {
        this.betBalance = betBalance;
    }
}
