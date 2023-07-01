import {mount} from '@vue/test-utils'
import ItemCard from '../../src/components/ItemCard.vue'

describe('ItemCard', () => {
    it("renders an item", () => {
        const value = JSON.parse(ItemCard.computed.value.call({
            "uuid": "a157940d-50aa-4d48-adcf-0b8abe965951",
            "title": "Some Title",
            "owner": "Some Owner"
        }))
        expect(value.t).toBe("I")
    })

    it("renders a supplied uuid", () => {
        const value = JSON.parse(ItemCard.computed.value.call({
            "uuid": "a157940d-50aa-4d48-adcf-0b8abe965951",
            "title": "Some Title",
            "owner": "Some Owner"
        }))
        expect(value.u).toBe("a157940d-50aa-4d48-adcf-0b8abe965951")
    })
})

describe('ItemCard wrapper', () => {
    const wrapper = mount(ItemCard, {
        propsData: {
            "uuid": "a157940d-50aa-4d48-adcf-0b8abe965951",
            "title": "Some Title",
            "owner": "Some Owner"
        }
    })

    it("renders a supplied title", () => {
        expect(wrapper.html()).toContain('Some Title')
    })

    it("renders a supplied owner", () => {
        expect(wrapper.html()).toContain('Some Owner')
    })
})
