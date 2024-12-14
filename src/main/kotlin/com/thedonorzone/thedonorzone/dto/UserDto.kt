import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserDto(
    @Email(message = "Invalid email format")
    val email: String,

    @NotBlank(message = "Username cannot be blank")
    val username: String,

    @NotBlank(message = "Password cannot be blank")
    val password: String
)
