package org.example.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserVO {
    private String regNo;
    private String name;
    private int age;
}
