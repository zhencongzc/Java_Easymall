package cn.tedu.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private Date created;
    private Date updated;
}
