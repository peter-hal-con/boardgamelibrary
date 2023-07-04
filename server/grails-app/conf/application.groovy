import org.apache.commons.lang3.RandomStringUtils


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.halcon.gaming.boardgamelibrary.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.halcon.gaming.boardgamelibrary.UserAuthority'
grails.plugin.springsecurity.authority.className = 'com.halcon.gaming.boardgamelibrary.Authority'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/index.html', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']],
        [pattern: '/clientConfiguration/**', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.rest.token.storage.jwt.secret = RandomStringUtils.random(32, true, true)

environments {
    test {
        grails.plugin.springsecurity.controllerAnnotations.staticRules += [
                [pattern: '/testOnly/**', access: ['permitAll']]
        ]
    }
}

