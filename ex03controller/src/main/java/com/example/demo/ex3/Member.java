package com.example.demo.ex3;

import java.util.Date;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {

    @NotNull(message = "Name cannot be null")
    private String name;

    @AssertTrue(message = "Working must be true")
    private boolean working;

    @Size(min = 10, max = 200, message 
      = "About Me must be between 10 and 200 characters")
    private String aboutMe;

    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "0\\d{1,4}-\\\\d{1,4}-\\\\d{4}" , message = "xxx-xxx-xxxx 형식")
    private String phone;
    private Date startdate;
    private Date enddate;
    
    @AssertTrue
    public Boolean isEndDateAfterStartDate() {
    	return this.enddate.after(startdate);
    }
}
