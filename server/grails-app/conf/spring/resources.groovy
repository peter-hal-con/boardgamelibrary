import com.halcon.gaming.boardgamelibrary.UserPasswordEncoderListener

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    authenticationEntryPoint(org.springframework.security.web.authentication.Http403ForbiddenEntryPoint)
}
