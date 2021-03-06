package com.halcon.gaming.boardgamelibrary

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class TestOnlyControllerSpec extends Specification implements ControllerUnitTest<TestOnlyController>, DataTest {

    Class<?>[] getDomainClassesToMock(){
        return [User, Authority, UserAuthority, Title, Copy] as Class[]
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test index"() {
        when:
        controller.index()

        then:
        status == 200
    }

    void "test createUser"() {
        given:
            request.JSON = [username:'test@example.com', password:'password3']

        when:
            controller.createUser()

        then:
            status == 200
            def createdUser = User.findById(response.json.id)
            createdUser.username == 'test@example.com'
    }

    void "test createUser with authority"() {
        given:
            Authority adminAuthority = [authority:"ROLE_ADMIN"] as Authority
            adminAuthority.save(flush:true)
            request.JSON = [username:'test@example.com', password:'password3', authorities:['ROLE_ADMIN']]

        when:
            controller.createUser()

        then:
            status == 200
            def createdUser = User.findById(response.json.id)
            createdUser.authorities.size() == 1
            createdUser.authorities.contains(adminAuthority)
    }

    void "test reset on user"() {
        given:
            User user = [username:"test@example.com", password:"password3"] as User
            user.save(flush:true)

        when:
            controller.reset()

        then:
            status == 200
            User.findAll().empty
    }

    void "test reset on user with role"() {
        given:
            Authority adminAuthority = [authority:"ROLE_ADMIN"] as Authority
            adminAuthority.save(flush:true)
            User user = [username:"test@example.com", password:"password3"] as User
            user.save(flush:true)
            UserAuthority.create(user, adminAuthority, true)

        when:
            controller.reset()

        then:
            status == 200
            User.findAll().empty
            UserAuthority.findAll().empty
    }

    void "test reset on title"() {
        given:
            Title title = [title:"Crossbows and Catapults", bggId:2129] as Title
            title.save(flush:true)

        when:
            controller.reset()

        then:
            status == 200
            Title.findAll().empty
    }

    void "test reset on copy"() {
        given:
            Title title = [title:"Crossbows and Catapults", bggId:2129] as Title
            title.save(flush:true)

            User user = [username:"test@example.com", password:"password3"] as User
            user.save(flush:true)

            Copy copy = [title:title, owner:user] as Copy
            copy.save(flush:true)

        when:
            controller.reset()

        then:
            status == 200
            Copy.findAll().empty
    }
}
