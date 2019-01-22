<template>
    <div>
      <basic-information></basic-information>
      <TrackRecord></TrackRecord>
    </div>
</template>

<script>
    import BasicInformation from "./BasicInformation.vue";
    import TrackRecord from "./TrackRecord.vue";

    export default {
        components:{
          BasicInformation,
          TrackRecord
        },
      mounted() {
          alert("获取到发送过来的数据是："+this.$route.params)
      }
    }
</script>

<style scoped>

</style>
