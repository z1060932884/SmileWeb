package com.zzj.learn.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("s_user")
public class User {
    //MyBatis-Plus 声明主键
    //如果表中的主键使用了自增 需要在次注解中声明 type = IdType.AUTO（自增）[本人亲测]
    @TableId(value = "id")
    private String id;
    private String name;
    private Integer age;
    private String email;

}
