<template>
  <v-autocomplete
      v-model="selectedItemBggId"
      :items='items'
      :search-input.sync='search'
      @change="$emit('itemSelected', rawItemWithBggId(selectedItemBggId))"
  />
</template>

<script>
import {bggId, createItemWithPrimaryName, displayName, searchBgg} from '@/util/bgg-util'

function debounce(fn, delay) {
  var timeoutID = null;
  return function () {
    clearTimeout(timeoutID);
    var args = arguments;
    var that = this
    timeoutID = setTimeout(function () {
      fn.apply(that, args)
    }, delay)
  };
}

export default {
  name: 'BggAutocomplete',

  emits: ['itemSelected'],

  data() {
    return {
      search: '',
      items: [],
      selectedItemBggId: null
    }
  },

  methods: {
    rawItemWithBggId(targetBggId) {
      for (const item of this.items) {
        if (bggId(item.raw) == targetBggId) {
          return item.raw
        }
      }
    }
  },

  watch: {
    search: debounce(async function (localSearch) {
      if (this.search && this.search !== '') {
        if (!this.selectedItemBggId) {
          const localRawItems = await searchBgg(localSearch).then(rawItems => rawItems.concat([createItemWithPrimaryName(localSearch)]))
          if (this.search === localSearch) {
            this.items = localRawItems.map(rawItem => {
              return {text: displayName(rawItem), value: bggId(rawItem), raw: rawItem};
            })
          }
        }
      } else {
        this.items = []
      }
    }, 500)
  }
}
</script>
