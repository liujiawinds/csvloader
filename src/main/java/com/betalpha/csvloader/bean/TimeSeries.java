package com.betalpha.csvloader.bean;

import java.util.Date;

public class TimeSeries {
    private String uuid;
    private Date tradingDate;
    private String stockCode;
    private Double itemValue;

    public TimeSeries(String uuid, Date tradingDate, String stockCode, Double itemValue) {
        this.uuid = uuid;
        this.tradingDate = tradingDate;
        this.stockCode = stockCode;
        this.itemValue = itemValue;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(Date tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Double getItemValue() {
        return itemValue;
    }

    public void setItemValue(Double itemValue) {
        this.itemValue = itemValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSeries that = (TimeSeries) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (tradingDate != null ? !tradingDate.equals(that.tradingDate) : that.tradingDate != null) return false;
        if (stockCode != null ? !stockCode.equals(that.stockCode) : that.stockCode != null) return false;
        return itemValue != null ? itemValue.equals(that.itemValue) : that.itemValue == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (tradingDate != null ? tradingDate.hashCode() : 0);
        result = 31 * result + (stockCode != null ? stockCode.hashCode() : 0);
        result = 31 * result + (itemValue != null ? itemValue.hashCode() : 0);
        return result;
    }
}
