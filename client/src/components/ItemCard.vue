<template>
  <div style="width:2.00in; height:3.25in; padding:0.125in; float:left; overflow:hidden;">
    <qrcode-vue :value="value" level="H" render-as="svg" size="135" style="padding:14px; height:163px; width:163px"/>
    <p>{{ title }}</p>
    <p>{{ owner }}</p>
  </div>
</template>

<script>
import QrcodeVue from 'qrcode.vue'

export default {
  name: 'ItemCard',

  components: {
    QrcodeVue,
  },

  props: {
    uuid: {
      type: String,
      required: true
    },
    title: {
      type: String,
      required: true
    },
    owner: {
      type: String,
      required: true
    }
  },

  computed: {
    value: function () {
      return '{"t":"I","u":"' + this.uuid + '"}'
    }
  }
}
</script>
