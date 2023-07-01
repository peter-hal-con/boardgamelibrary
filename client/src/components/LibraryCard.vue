<template>
  <div style="width:3.25in; height:1.75in; padding:0.125in; float:left; overflow:hidden;">
    <h1 style="margin:0; font-size:1.25em;">Hal-Con Boardgame Library Card</h1>
    <qrcode-vue :value="value" level="H" render-as="svg" size="110" style="padding:14px; height:138px; width:138px"/>
  </div>
</template>

<script>
import QrcodeVue from 'qrcode.vue'
import {v4 as uuidv4} from 'uuid'

export default {
  name: 'LibraryCard',

  components: {
    QrcodeVue,
  },

  props: {
    uuid: String
  },

  computed: {
    value: function () {
      return '{"t":"P","u":"' + (this.uuid ? this.uuid : uuidv4()) + '"}'
    }
  }
}
</script>
