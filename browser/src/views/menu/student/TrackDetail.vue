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
    }
</script>

<style scoped>

</style>
