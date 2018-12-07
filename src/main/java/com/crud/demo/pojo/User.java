package com.crud.demo.pojo;

import com.crud.demo.utils.Insert;
import com.crud.demo.utils.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Author: zxx
 * @Date: 2018/11/28 20:33
 * @Version 1.0
 */
@Component
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -7383047314103937310L;
    @Min(value=1,message = "{idFlase}",groups = {Insert.class})
    private int id;
    @NotEmpty(message = "{nameHint}")
    private String name;
    @Min(value=10,message = "{ageLow}")
    @Max(value = 99,message = "{ageHigh}")
    private int age;
    private String mobile;
    @Email(message = "{emailFalse}")
    private String email;
    private String img;

    public User() {
    }

    public User(int id, String name, int age, String mobile, String email, String img) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.img = img;
    }
}
