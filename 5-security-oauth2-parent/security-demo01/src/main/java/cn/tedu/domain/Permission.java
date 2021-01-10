package cn.tedu.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Permission {
    private Long id;
    private Long userId;
    private String name;
    private String authority;
    private Date created;
    private Date updated;
}
