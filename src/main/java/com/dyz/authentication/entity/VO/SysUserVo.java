package com.dyz.authentication.entity.Vo;

/**
 * @ClassName SysUserVO
 * @Author
 * @Date 2020/9/4
 * @description
 */
public class SysUserVo {

    private String id;

    private String userName;

    private String passWord;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SysUserVO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
