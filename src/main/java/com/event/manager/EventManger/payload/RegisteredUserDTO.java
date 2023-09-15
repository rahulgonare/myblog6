package com.event.manager.EventManger.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUserDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotNull(message = "Grade is required")
    @Max(10)
    @Min(1)
    private int grade;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone must be a 10-digit number")
    private String phone;

    @NotBlank(message = "State is required")
    @Size(max = 255, message = "State must not exceed 255 characters")
    private String state;

    @NotBlank(message = "School Name is required")
    @Size(max = 255, message = "School Name must not exceed 255 characters")
    private String schoolName;

    @NotBlank(message = "City is required")
    @Size(max = 255, message = "City must not exceed 255 characters")
    private String city;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

}
