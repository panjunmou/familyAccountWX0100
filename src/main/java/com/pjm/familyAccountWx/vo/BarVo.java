package com.pjm.familyAccountWx.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by PanJM_Levono on 2016/12/29.
 */
public class BarVo {
    private List<String> legendDatas;

    private List<ReportDataVo> seriesDatas;

    private BigDecimal totalIn;

    private BigDecimal totalOut;

    public List<String> getLegendDatas() {
        return legendDatas;
    }

    public void setLegendDatas(List<String> legendDatas) {
        this.legendDatas = legendDatas;
    }

    public List<ReportDataVo> getSeriesDatas() {
        return seriesDatas;
    }

    public void setSeriesDatas(List<ReportDataVo> seriesDatas) {
        this.seriesDatas = seriesDatas;
    }

    public BigDecimal getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(BigDecimal totalIn) {
        this.totalIn = totalIn;
    }

    public BigDecimal getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(BigDecimal totalOut) {
        this.totalOut = totalOut;
    }
}
