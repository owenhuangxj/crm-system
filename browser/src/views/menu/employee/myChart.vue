<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span>李小姐的业f务情况</span>
        </div>
        <div class="yourClassName" id="yourID" style="height:200px;width:250px" ref="myEchart"></div>
    </el-card>
</template>
<script>
  export default {
        data() {
            return {
                userId: 1,
                chart: null,
                chartData:'',
            }
        },
        created() {
            this.getData();
        },
        mounted() {

            this.initChart();
        },
        beforeDestroy() {
            if (!this.chart) {
                return
            }
            this.chart.dispose();
            this.chart = null;
        },
        methods: {
            //从后台获取数据
            getData() {
                this.url = '/selectWorkerOfBusiness/' + 2;
                axios.get(this.url).then((res) => {
                    this.chartData=res.data
                    alert("获得的数据：" + JSON.stringify(this.chartData));
                    this.setChart();
                    // this.tableData = res.data;
                })

            },

            //赋值给柱形图
            setChart(){
                alert("违背执行了"+JSON.stringify(this.chartData));
                this.chart.setOption({
                    xAxis: {
                        data: this.chartData.category
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '已完成',
                        data: this.chartData.AlreadData
                    },
                        {
                            // 根据名字对应到相应的系列
                            name: '未完成',
                            data: this.chartData.NoData
                        }
                    ]
                })
            },

            initChart() {

                alert("我初始化了"+JSON.stringify(this.chartData.category));
                this.chart = echarts.init(this.$refs.myEchart);
                // 把配置和数据放这里
                this.chart.setOption({
                    title: {},
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['本月', '本周', '完成率']
                    },
                    toolbox: {
                        show: true,
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            data: []
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '已完成',
                            type: 'bar',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            data: []
                        },
                        {
                            name: '未完成',
                            type: 'bar',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            data: []
                        }
                        ,
                        {
                            name: '完成率',
                            type: 'bar',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top',
                                    formatter: '{b}\n{c}%'
                                }
                            },
                            data: [5, 6]
                        }
                    ]
                });

            }
        }
    }
</script>
<style scoped>
    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 250px;
        height: 250px;
    }
</style>
