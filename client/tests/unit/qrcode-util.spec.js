import {validateType, validateUUID} from '@/util/qrcode-util'

describe('validateType', () => {
    it("accepts a person", () => {
        expect(validateType('P')).toBe(true)
    })

    it("accepts an item", () => {
        expect(validateType('P')).toBe(true)
    })

    it("rejects other types", () => {
        expect(validateType('Z')).toBe(false)
    })
})

describe('validateUUID', () => {
    it("accepts a valid UUID", () => {
        expect(validateUUID('884b224b-6aa5-4949-855d-95e72d1e3a1c')).toBe(true)
    })

    it("rejects an invalid UUID", () => {
        expect(validateType('884b224b-6aa5-f949-855d-95e72d1e3a1c')).toBe(false)
    })
})
