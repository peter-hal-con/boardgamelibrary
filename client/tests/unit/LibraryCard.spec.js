import LibraryCard from '../../src/components/LibraryCard.vue'

describe('LibraryCard', () => {
    it("renders a person", () => {
        const value = JSON.parse(LibraryCard.computed.value.call({}))
        expect(value.t).toBe("P")
    })

    it("renders a supplied uuid", () => {
        const value = JSON.parse(LibraryCard.computed.value.call({"uuid": "c81ac265-35e3-4734-a0fc-7da24e00967b"}))
        expect(value.u).toBe("c81ac265-35e3-4734-a0fc-7da24e00967b")
    })

    it("generates a uuid when none is supplied", () => {
        const value = JSON.parse(LibraryCard.computed.value.call({}))
        expect(value.u).toMatch(/^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/)
    })
})
