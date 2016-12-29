package com.pjm.familyAccountWx.vo;

import java.util.List;

/**
 * Created by PanJM_Levono on 2016/12/29.
 */
public class BarVo {
    private List<String> legendDatas;

    private List<ReportDataVo> seriesDatas;

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
}
