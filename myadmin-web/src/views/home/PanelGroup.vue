<template>
  <!-- :gutter="20"  间隔距离 -->
  <el-row :gutter="20" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              会员总数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.userCount" :duration="2600" class="card-panel-num" />
            人
          </span>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="order" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              订单总数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.orderCount" :duration="3000" class="card-panel-num" />
            单
          </span>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">

            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              总金额数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.priceCount" :duration="3200" class="card-panel-num" />
          元
          </span>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="shopping" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              商品总数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.goodsCount" :duration="3600" class="card-panel-num" />
          个
          </span>
        </div>
      </div>
    </el-col>
    <!-- 第二层 -->
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people" style=" color:rgb(179, 127, 235);">
          <svg-icon icon-class="today" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">

            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              今日订单数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.todayCount" :duration="2600" class="card-panel-num" />
          单
          </span>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="ic-yesterday" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              昨日订单数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.proCount" :duration="3000" class="card-panel-num" />
          单
          </span>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="seven" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              近七天订单数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.lastWeekCount" :duration="3200" class="card-panel-num" />
          单
          </span>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="monthlyview" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            <span class="el-tag el-tag--success el-tag--small el-tag--light">
              本月订单数
            </span>
            <span class="el-tag el-tag--warning el-tag--small el-tag--light">
              全平台
            </span>
          </div>
          <span class="el-tag el-tag--small ">
            <count-to :start-val="0" :end-val="count.monthCount" :duration="3600" class="card-panel-num" />
          单
          </span>
        </div>
      </div>
    </el-col>
    <div class="el-card__header">
      <div>
        <div class="acea-row row-middle">
          <span class="el-avatar  el-avatar--icon el-avatar--circle"
          style="color: rgb(24, 144, 255); background: rgb(230, 247, 255); font-size: 13px;">
            <i class="el-icon-menu"></i>
          </span>
          <span class="ivu-pl-8">快捷面板</span>
        </div>
      </div>
    </div>
    <!-- 第三层 -->
    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col ">
      <div class="card-panel ">
        <div class="el-tag-info">
          <i style="color: rgb(105, 192, 255); font-size:40px" class="el-icon-goods">
          </i>
          <p>商品管理</p>
        </div>
      </div>
    </el-col>

    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color: rgb(149, 222, 100); font-size:40px" class=" el-icon-user">
          </i>
          <p>会员管理</p>
        </div>
      </div>
    </el-col>
    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color: rgb(255, 156, 110); font-size:40px" class="el-icon-s-order">
          </i>
          <p>订单管理</p>
        </div>
      </div>
    </el-col>
    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color: rgb(179, 127, 235); font-size:40px" class="el-icon-s-ticket ">
          </i>
          <p>电子劵管理</p>
        </div>
      </div>
    </el-col>
    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color: rgb(255, 214, 102); font-size:40px" class="el-icon-postcard ">
          </i>
          <p>营销管理</p>
        </div>
      </div>
    </el-col>

    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color:  rgb(92, 219, 211); font-size:40px" class="el-icon-notebook-1 ">
          </i>
          <p>分销管理</p>
        </div>
      </div>
    </el-col>
    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color: rgb(255, 133, 192); font-size:40px" class="el-icon-s-finance">
          </i>
          <p>财务管理</p>
        </div>
      </div>
    </el-col>
    <el-col :span="3" :xs="12" :sm="12" :lg="3" class="card-panel-col">
      <div class="card-panel">
        <div class="el-tag-info">
          <i style="color: rgb(255, 192, 105); font-size:40px" class="el-icon-office-building">
          </i>
          <p>门店管理</p>
        </div>
      </div>
    </el-col>


  </el-row>
</template>
<script>
  import CountTo from 'vue-count-to'
  import {
    gett
  } from '@/api/visits'
  export default {
    components: {
      CountTo
    },
    data() {
      return {
        count: {
          todayPrice: 0,
          todayCount: 0,
          proPrice: 0,
          proCount: 0,
          monthPrice: 0,
          monthCount: 0,
          lastWeekCount: 0,
          lastWeekPrice: 0,
          userCount: 0,
          orderCount: 0,
          priceCount: 0,
          goodsCount: 0
        }
      }
    },
    mounted() {
      gett().then(res => {
        this.count.todayPrice = res.todayPrice
        this.count.todayCount = res.todayCount
        this.count.proCount = res.proCount
        this.count.proPrice = res.proPrice

        this.count.monthPrice = res.monthPrice
        this.count.monthCount = res.monthCount
        this.count.lastWeekCount = res.lastWeekCount
        this.count.lastWeekPrice = res.lastWeekPrice

        this.count.userCount = res.userCount
        this.count.orderCount = res.orderCount
        this.count.priceCount = res.priceCount
        this.count.goodsCount = res.goodsCount
      })
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  // element.style {
  //     padding-left: 10px;
  //     padding-right: 10px;
  // }
  .panel-group {
    margin-top: 8px;

    .card-panel-col {
      margin-bottom: 22px;
    }

    .card-panel {
      border-radius: 8px;
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      .el-tag-info {
        margin-top: 20px;
        margin-left: 75px;
        position: relative;
        overflow: hidden;
        // margin-right: 60px;
      }

      .icon-people {
        // color: #40c9c6;
        color: rgb(149, 222, 100);
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-money {
        // color: #f4516c;
        color: rgb(179, 127, 235);
      }

      .icon-money {
        // color: #f4516c;
        color: rgb(179, 127, 235);
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 18px;
        }
      }
    }
  }

  @media (max-width:550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
  .el-card__header {
    // padding: 18px 22px 22px 22px;
    // background-color: rgb(240, 242, 245);
    // background: #fff;
    // border-radius:8px;
      // padding: 18px 20px ;
      border-bottom: 2px solid #e6ebf5;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
  }
</style>
