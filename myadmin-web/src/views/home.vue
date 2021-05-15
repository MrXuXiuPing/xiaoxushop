<template>
  <div class="dashboard-container">

    <div class="dashboard-editor-container">
      <!--<yshop-info />-->
      <panel-group />
      <!-- <panel-group-t /> -->
      <div class="chart-wrapper">
        <el-row :gutter="32">
          <div class="el-card__header">
            <div>
              <div class="acea-row row-middle">
                <span class="el-avatar  el-avatar--icon el-avatar--circle"
                style="color: rgb(24, 144, 255); background: rgb(230, 247, 255); font-size: 13px;">
                  <i class="el-icon-s-operation"></i>
                </span>
                <span class="ivu-pl-8">数据统计</span>
              </div>
            </div>
          </div>
          <el-col :xs="16" :sm="16" :lg="8">
            <p>商品分析</p>
            <order-count></order-count>
          </el-col>
          <el-col :xs="16" :sm="16" :lg="8">
            <p>本月成交额</p>
            <bar-chart />
          </el-col>
          <el-col :xs="16" :sm="16" :lg="8">
            <p>本月订单数</p>
            <pie-chart />
          </el-col>
        </el-row>

      </div>
    </div>
  </div>
</template>

<script>
  import {
    mapGetters
  } from 'vuex'
  import PanelGroup from './home/PanelGroup'
  import PanelGroupT from './home/PanelGroupT'
  import PieChart from './home/BarChartT'
  import BarChart from './home/BarChart'
  import {
    count
  } from '@/api/visits'
  import YshopInfo from './home/YshopInfo'
  import OrderCount from './home/OrderCount'

  /**
   * 记录访问，只有页面刷新或者第一次加载才会记录
   */
  count().then(res => {})

  export default {
    name: 'HomeIndex',
    components: {
      PanelGroup,
      PanelGroupT,
      PieChart,
      BarChart,
      YshopInfo,
      OrderCount
    },
    computed: {
      ...mapGetters([
        'roles'
      ])
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard-editor-container {
    padding: 18px 22px 22px 22px;
    background-color: rgb(240, 242, 245);

    .chart-wrapper {
	  border-radius:8px;
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }
.el-card__header {
    padding: 0px 2px 15px 10px/* 18px 20px */;
    border-bottom: 2px solid #e6ebf5;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;


}
  .title1 {
    font-weight: bold;
    color: #432f2f;
    line-height: 0px;
    margin-block-start: 0 !important;
  }
</style>
